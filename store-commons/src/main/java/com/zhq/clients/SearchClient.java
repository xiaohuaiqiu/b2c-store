package com.zhq.clients;

import com.zhq.param.ProductSearchParam;
import com.zhq.pojo.Product;
import com.zhq.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "search-service")
public interface SearchClient {

    /**
     * 搜索服务 商品查询
     *
     * @param
     * @return
     */
    @PostMapping("/search/product")
    R search(@RequestBody ProductSearchParam productSearchParam);

    /**
     * 商品保存和更新
     *
     * @param
     * @return
     */
    @PostMapping("/search/save")
    R saveOrUpdate(@RequestBody Product Product);

    /**
     * 商品删除
     *
     * @param
     * @return
     */
    @PostMapping("/search/remove")
    R remove(@RequestBody Integer productId);


}

