package com.zhq.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhq.clients.*;
import com.zhq.param.ProductHotParam;
import com.zhq.param.ProductIdsParam;
import com.zhq.param.ProductSaveParam;
import com.zhq.param.ProductSearchParam;
import com.zhq.pojo.Category;
import com.zhq.pojo.Picture;
import com.zhq.pojo.Product;
import com.zhq.product.mapper.PictureMapper;
import com.zhq.product.mapper.ProductMapper;
import com.zhq.product.service.ProductService;
import com.zhq.to.OrderToProduct;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    //引入feign客户端 需要在启动类加配置注解
    @Autowired //类别服务功能
    private CategoryClient categoryClient;
    @Autowired //商品服务功能
    private ProductMapper productMapper;
    @Autowired //图片服务
    private PictureMapper pictureMapper;
    @Autowired //搜索服务功能
    private SearchClient searchClient;
    @Autowired
    private CartClient cartClient;
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private CollectClient collectClient;

    /**
     * 根据单类别名称 查询热门商品 最多7条数据
     * 1.根据类别名称调用 feign的客户端 访问类别服务的数据
     * 2.成功 继续根据类别id查询商品数据 （热门 销售量倒序 查询7）
     * 3.封装结果
     *
     * @param categoryName 类别名称
     * @return R
     */
    @Cacheable(value = "list.product", key = "#categoryName", cacheManager = "cacheManagerDay") //开启缓存
    @Override
    public R promo(String categoryName) {

        R r = categoryClient.byName(categoryName);

        if (r.getCode().equals(R.FAIL_CODE)) {
            return r;
        }
        // 类别服务 data = category --- feign {json} -------- product服务 {inkedHashMap} jackson

        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) r.getData(); //获取类别数据

        Integer categoryId = (Integer) map.get("category_id"); //继续根据类别id查询商品数据

        //封装查询参数
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        queryWrapper.orderByDesc("product_sales");
        IPage<Product> page = new Page<>(1, 7);

        //返回的是包装数据 内部有对应的商品集合，也有分页的参数 例如 总条数 总页数等等
        page = productMapper.selectPage(page, queryWrapper);

        List<Product> productList = page.getRecords(); //指定页数据

        return R.ok("数据查询成功!", productList);
    }

    /**
     * 多类别热门商品查询 根据类别名称集合 至多查询7条！
     * 1.调用类别服务
     * 2.类别集合id查询商品
     * 3.结果封装
     *
     * @param
     * @return R
     */
    @Cacheable(value = "list.product", key = "#productHotParam.categoryName") //开启缓存
    @Override
    public R hots(ProductHotParam productHotParam) {
        R r = categoryClient.host(productHotParam);
        if (r.getCode().equals(R.FAIL_CODE)) {
            return r;
        }
        List<Object> ids = (List<Object>) r.getData();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("category_id", ids);
        queryWrapper.orderByDesc("product_sales");
        IPage<Product> iPage = new Page<>(1, 7);
        iPage = productMapper.selectPage(iPage, queryWrapper);
        List<Product> records = iPage.getRecords();
        return R.ok("多热门类别商品查询成功!", records);
    }

    /**
     * 查询类别商品集合
     *
     * @return
     */
    @Override
    public R clist() {
        R list = categoryClient.list();
        return list;
    }

    /**
     * 查询类别商品，进行返回
     * 如果传入了id 根据id查
     * 没传入 直接查询全部
     *
     * @param productIdsParam
     * @return
     */
    @Cacheable(value = "list.product", key =
            "#productIdsParam.categoryID+'-'+#productIdsParam.currentPage+'-'+#productIdsParam.pageSize") //开启缓存
    @Override
    public R byCategory(ProductIdsParam productIdsParam) {
        List<Integer> categoryID = productIdsParam.getCategoryID();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if (!categoryID.isEmpty()) {
            queryWrapper.in("category_id", categoryID);
        }
        IPage<Product> iPage = new Page<>(productIdsParam.getCurrentPage(), productIdsParam.getPageSize());
        iPage = productMapper.selectPage(iPage, queryWrapper);

        return R.ok("查询成功!", iPage.getRecords(), iPage.getTotal());
    }

    /**
     * 根据商品id查询商品详情信息
     * 1.根据id查询
     *
     * @param productID
     * @return
     */
    @Cacheable(value = "product", key = "#productID") //开启缓存
    @Override
    public R detail(Integer productID) {
        Product product = productMapper.selectById(productID);
        return R.ok(product);
    }

    /**
     * 商品图片查询根据id
     *
     * @param productID
     * @return
     */
    @Cacheable(value = "picture", key = "#productID")
    @Override
    public R picture(Integer productID) {
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productID);
        List<Picture> pictures = pictureMapper.selectList(queryWrapper);
        R ok = R.ok(pictures);
        return ok;
    }

    /**
     * 搜索服务调用，获取全部商品数据
     * 进行同步
     *
     * @return
     */
    @Cacheable(value = "list.category", key = "#root.methodName", cacheManager = "cacheManagerDay")
    @Override
    public List<Product> allList() {

        List<Product> productslist = productMapper.selectList(null);
        return productslist;
    }

    /**
     * 搜索商品功能
     *
     * @return
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {
        R search = searchClient.search(productSearchParam);
        return search;
    }

    /**
     * 根据商品id集合查询商品信息
     *
     * @param productIds
     * @return
     */
    @Cacheable(value = "list.product", key = "#productIds")
    @Override
    public R ids(List<Integer> productIds) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper();
        queryWrapper.in("product_id", productIds);

        List<Product> list = productMapper.selectList(queryWrapper);
        return R.ok("类别信息查询成功!", list);
    }

    /**
     * 根据商品id查询商品集合
     *
     * @param productIds
     * @return
     */
    @Override
    public List<Product> cartList(List<Integer> productIds) {
        //校验id集合
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", productIds);
        //操作业务
        List<Product> products = productMapper.selectList(queryWrapper);
        return products;
    }

    /**
     * 修改库存和增加销售量
     *
     * @param orderToProducts
     */
    @Override
    public void subNumber(List<OrderToProduct> orderToProducts) {

        //将集合转成一个map   key:productId    value:orderToProduct
        Map<Integer, OrderToProduct> map = orderToProducts.stream().collect(Collectors.toMap(OrderToProduct::getProductId, v -> v));
        //获取商品的id集合
        Set<Integer> productIds = map.keySet();
        //查询集合对应的商品信息
        List<Product> productList = productMapper.selectBatchIds(productIds);
        //修改商品信息
        for (Product product : productList) {
            Integer num = map.get(product.getProductId()).getNum(); //取到OrderToProduct的num
            product.setProductNum(product.getProductNum() - num); //减去num就是减去的购买量
            product.setProductSales(product.getProductSales() + num); //加上num 就是加销量
        }
        //批量更新
        this.updateBatchById(productList);
    }

    /**
     * 类别对应的商品数量的查询
     *
     * @param categoryId
     * @return
     */
    @Override
    public Long adminCount(Integer categoryId) {


        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);

        Long count = productMapper.selectCount(queryWrapper);

        return count;
    }

    /**
     * 商品保存的业务
     * 1.商品数据保存
     * 2.商品的图片详情切割和保存
     * 3.搜索数据库的数据添加
     * 4.清空商品的缓存数据
     *
     * @param productSaveParam
     * @return
     */
    @CacheEvict(value = "list.product", allEntries = true)
    @Override
    public R adminSave(ProductSaveParam productSaveParam) {
//        1.商品数据保存
        Product product = new Product();
        BeanUtils.copyProperties(productSaveParam, product);
        int insert = productMapper.insert(product); //保存商品数据
        log.info("ProductServiceImpl,adminSave业务结束，结果：{}", insert);

//        2.商品的图片详情切割和保存
        String pictures = productSaveParam.getPictures();
        if (!StringUtils.isEmpty(pictures)) {
            //获取特殊字符串的时候 \\ []
            String[] urls = pictures.split("\\+");
            for (String url : urls) {
                Picture picture = new Picture();
                picture.setProductId(product.getProductId());
                picture.setProductPicture(url);
                pictureMapper.insert(picture); //插入商品的图片
            }
        }
//        3.搜索数据库的数据同步
        searchClient.saveOrUpdate(product);

        return R.ok("数据添加成功");
    }

    /**
     * 商品数据的更新
     * 1.更新商品数据
     * 2.同步搜索数据服务
     *
     * @param product
     * @return
     */

    @Override
    public R adminUpdate(Product product) {

        productMapper.updateById(product);

        //        3.搜索数据库的数据同步
        searchClient.saveOrUpdate(product);

        return R.ok("数据修改成功!");
    }

    /**
     * 商品删除
     * 1.检查购物车
     * 2.检查订单
     * 3.删除商品
     * 4.删除商品相关的图片
     * 5.删除商品相关的收藏
     * 6.进行es数据同步 删除搜索的数据
     * 7.清空缓存
     *
     * @param productId
     * @return
     */

    //删除多个 对应的清除缓存
    @Caching(
            evict = {
                    @CacheEvict(value = "list.product", allEntries = true),
                    @CacheEvict(value = "product", key = "#productId")
            }
    )
    @Override
    public R adminRemove(Integer productId) {
        //1.检查购物车
        R check = cartClient.check(productId);
        if ("004".equals(check.getCode())) {
            return check;
        }
        //2.检查订单
        R r = orderClient.check(productId);
        if ("004".equals(r.getCode())) {
            return r;
        }
        //3.删除商品和商品的图片
        productMapper.deleteById(productId);
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId); //看看是那个商品 通过id判断
        pictureMapper.delete(queryWrapper);

        //4.删除收藏
        collectClient.remove(productId);

        //5.es同步数据
        searchClient.remove(productId);
        return R.ok("商品删除成功!");
    }
}
