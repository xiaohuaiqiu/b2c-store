package com.zhq.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhq.clients.ProductClient;
import com.zhq.order.mapper.OrderMapper;
import com.zhq.order.service.OrderService;
import com.zhq.param.OrderParam;
import com.zhq.param.PageParam;
import com.zhq.param.ProductCollectParam;
import com.zhq.pojo.Order;
import com.zhq.pojo.Product;
import com.zhq.to.OrderToProduct;
import com.zhq.utils.R;
import com.zhq.vo.AdminOrderVo;
import com.zhq.vo.CartVo;
import com.zhq.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate; //消息队列 必须引入此注解
    @Autowired
    private ProductClient productClient; //复用商品收藏的客户端 因为都是传入商品id
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 进行订单数据保存业务
     * 1.将购物车数据转化为订单数据
     * 2.进行订单数据批量插入
     * 3.商品库存修改消息
     * 4.发送购物车库存修改消息
     *
     * @param orderParam
     * @return
     */
    @Transactional //事务
    @Override
    public R save(OrderParam orderParam) {
        //准备数据
        List<Integer> cartIds = new ArrayList<>(); //要删除的购物车的商品id集合
        List<OrderToProduct> orderToProducts = new ArrayList<>(); //要发送给商品服务的集合
        List<Order> orders = new ArrayList<>(); //转化成order的集合

        //生成集合
        Integer userId = orderParam.getUserId();
        long orderId = System.currentTimeMillis(); //订单生成的编号 同时也是时间戳
        for (CartVo cartVo : orderParam.getProducts()) {

            cartIds.add(cartVo.getId()); //保存要删除的购物车项的id

            OrderToProduct orderToProduct = new OrderToProduct();
            orderToProduct.setProductId(cartVo.getProductID());
            orderToProduct.setNum(cartVo.getNum());
            orderToProducts.add(orderToProduct); //商品服务修改的数据
            //生成订单
            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderTime(orderId);
            order.setUserId(userId);
            order.setProductId(cartVo.getProductID());
            order.setProductNum(cartVo.getNum());
            order.setProductPrice(cartVo.getPrice());
            orders.add(order);
        }

        //订单数据批量保存
        saveBatch(orders); //这个是mybatisPlus提供的方法 用于批量保存

        //发送购物车消息
        rabbitTemplate.convertAndSend("topic.ex", "clear.cart", cartIds); //clear 删除
        //发送商品服务消息
        rabbitTemplate.convertAndSend("topic.ex", "sub.number", orderToProducts); //sub 服从

        return R.ok("购买成功，订单已经生成!"); //前端是根据响应码的 千万别写null
    }


    /**
     * 分组查询购物车订单数据
     * 1.查询用户对应的全部订单项
     * 2.利用stream进行订单分组 orderId
     * 3.查询订单的全部商品集合，并且steam组成集合
     * 4.封装返回OrderVo对象
     *
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
//        1. 查询用户对应的全部订单项
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Order> orderList = orderMapper.selectList(queryWrapper);
//        2. 利用stream进行订单分组 orderId
        Map<Long, List<Order>> orderMap = orderList.stream().collect(Collectors.groupingBy(Order::getOrderId));
//        3. 查询订单的全部商品集合，并且steam组成map
        List<Integer> productIds = orderList.stream().map(Order::getProductId).collect(Collectors.toList());

        ProductCollectParam param = new ProductCollectParam();
        param.setProductIds(productIds);
        List<Product> productList = productClient.cartList(param);
        //转化成对应的map集合
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
//        4. 封装返回OrderVo对象
        List<List<OrderVo>> result = new ArrayList<>();

        //遍历订单项集合
        for (List<Order> orders : orderMap.values()) {
            //封装每一个订单项
            List<OrderVo> orderVos = new ArrayList<>(); //装订单的集合
            for (Order order : orders) {
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(order, orderVo); //复制过去
                Product product = productMap.get(order.getProductId()); //补上少的那两个信息
                orderVo.setProductName(product.getProductName());
                orderVo.setProductPicture(product.getProductPicture());
                orderVos.add(orderVo);
            }
            result.add(orderVos);
        }
        R ok = R.ok("订单数据获取成功!", result);
        return ok;
    }

    /**
     * 检查订单中是否有商品引用
     *
     * @param productId
     * @return
     */
    @Override
    public R check(Integer productId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        Long count = orderMapper.selectCount(queryWrapper);
        if (count > 0) {
            return R.fail("订单中用户下单了" + count + "件这个商品!不能删除!");
        }
        return R.ok("可以放心删除!");
    }

    /**
     * 查询全部订单
     * 带分页
     *
     * @param pageParam
     * @return
     */
    @Override
    public R adminList(PageParam pageParam) {
        //分页参数计算完毕
        int offset = (pageParam.getCurrentPage() - 1) * pageParam.getPageSize();
        int pageSize = pageParam.getPageSize();

        List<AdminOrderVo> adminOrderVos = orderMapper.selectAdminOrder(offset, pageSize);

        return R.ok("订单数据查询成功!", adminOrderVos);
    }
}
