package com.zhq.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhq.cart.mapper.CartMapper;
import com.zhq.cart.service.CartService;
import com.zhq.clients.ProductClient;
import com.zhq.param.CartSaveParam;
import com.zhq.param.ProductCollectParam;
import com.zhq.param.ProductIdParam;
import com.zhq.pojo.Cart;
import com.zhq.pojo.Product;
import com.zhq.utils.R;
import com.zhq.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductClient productClient;

    /**
     * 添加商品到购物车
     *
     * @param cartSaveParam
     * @return
     */
    @Override
    public R save(CartSaveParam cartSaveParam) {
        //检查商品数量
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cartSaveParam.getProductId());
        Product product = productClient.productDetail(productIdParam);

        if (product == null) {
            return R.fail("商品已经被删除，无法添加到购物车!");
        }
        //检查商品库存
        if (product.getProductNum() == 0) {
            R ok = R.ok("没有库存了,请过几天再来吧!");
            ok.setCode("003");

            return ok;
        }
        //检查是否添加过
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cartSaveParam.getUserId());
        queryWrapper.eq("product_id", cartSaveParam.getProductId());

        Cart cart = cartMapper.selectOne(queryWrapper);
        if (cart != null) {
            //证明添加过
            //数量+1
            cart.setNum(cart.getNum() + 1);
            cartMapper.updateById(cart);
            //返回002
            R ok = R.ok("购物车存在该商品，数量加1");
            ok.setCode("002");
            return ok;
        }
        //添加购物车
        cart = new Cart(); //创建一个新的商品 这是第一个商品
        cart.setNum(1);
        cart.setUserId(cartSaveParam.getUserId()); //并且记录下这个人的id
        cart.setProductId(cartSaveParam.getProductId()); //同理商品id也记下来
        int insert = cartMapper.insert(cart);//这是第一个商品 用insert添加到数据库
        //结果返回
        CartVo cartVo = new CartVo(product, cart); //最后不是返回001 而是返回一个封装好的对象
        return R.ok("购物车数据添加成功", cartVo);
    }

    /**
     * 根据用户id查询购物车商品
     * 1. 查询用户对应的购物车数据
     * 2. 查询购物车对应的商品数据
     * 3. 进行数据封装 vo
     * 4. 返回结果即可
     *
     * @param userId
     * @return
     */
    @Override
    public R list(Integer userId) {
        //1.用户id查询购物车数据
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Cart> carts = cartMapper.selectList(queryWrapper);
        //2.判断是否存在,不存在，返回一个空集合
        if (carts == null || carts.size() == 0) { //没有数据
            carts = new ArrayList<>(); //必须返回空数据
            return R.ok("购物车空空如也!", carts);
        }
        //3.存在,获取商品id集合，并且调用商品服务查询
        List<Integer> ids = new ArrayList<>();
        for (Cart cart : carts) {
            ids.add(cart.getProductId()); //把遍历的对象的id放入集合中
        }
        ProductCollectParam param = new ProductCollectParam();
        param.setProductIds(ids);
        List<Product> productList = productClient.cartList(param);

        //商品集合 - 商品集合map  商品的id = key   商品 = value
        //jdk8 新特性 steam流
        Map<Integer, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, v -> v));
        //4.进行vo封装
        List<CartVo> cartVoList = new ArrayList<>();
        for (Cart cart : carts) {
            CartVo cartVo = new CartVo(productMap.get(cart.getProductId()), cart);
            cartVoList.add(cartVo);
        }
        R r = R.ok("数据库数据查询成功", cartVoList);
        return r;
    }

    /**
     * 更新购物车数量
     * 1.查询商品数据
     * 2. 判断库存是否可用 修改商品数据
     * 3.返回
     *
     * @param cart
     * @return
     */
    @Override
    public R update(Cart cart) {
        //拿到对应id的商品
        ProductIdParam productIdParam = new ProductIdParam();
        productIdParam.setProductID(cart.getProductId());
        Product product = productClient.productDetail(productIdParam);

        //判断库存
        if (cart.getNum() > product.getProductNum()) {
            return R.fail("修改失败!");
        }

        //修改数据库 //先查
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("product_id", cart.getProductId());
        Cart newCart = cartMapper.selectOne(queryWrapper);
        newCart.setNum(cart.getNum());
        int rows = cartMapper.updateById(newCart);
        return R.ok("修改购物车数量成功!");
    }

    /**
     * 删除购物车数据
     *
     * @param cart
     * @return
     */
    @Override
    public R remove(Cart cart) {
        //拼接参数
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("product_id", cart.getProductId());

        int i = cartMapper.delete(queryWrapper);
        R ok = R.ok("删除商品成功!");
        return ok;
    }

    /**
     * 清空对应id的购物车项
     *
     * @param cartIds
     */
    @Override
    public void clearIdS(List<Integer> cartIds) {
        cartMapper.deleteBatchIds(cartIds); //根据id批量删除
    }

    /**
     * 查询购物车项
     *
     * @param productId
     * @return
     */
    @Override
    public R check(Integer productId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        Long count = cartMapper.selectCount(queryWrapper);
        if (count > 0) {
            return R.fail("不能删除,有" + count + "件商品在购物车中!");
        }
        return R.ok("购物车无商品引用!");
    }
}

