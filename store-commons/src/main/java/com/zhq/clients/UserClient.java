package com.zhq.clients;

import com.zhq.param.CartListParam;
import com.zhq.param.PageParam;
import com.zhq.pojo.User;
import com.zhq.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户客户端
 */
@FeignClient("user-service")
public interface UserClient {
    /**
     * 后台管理展示用户信息
     *
     * @param pageParam
     * @return
     */
    @PostMapping("/user/admin/list")
    R adminListPage(@RequestBody PageParam pageParam);

    /**
     * 后台删除用户信息
     *
     * @param cartListParam
     * @return
     */
    @PostMapping("/user/admin/remove")
    R adminRemove(@RequestBody CartListParam cartListParam);

    /**
     * 后台修改用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("/user/admin/update")
    R adminUpdate(@RequestBody User user);

    /**
     * 后台添加用户信息
     *
     * @param user
     * @return
     */
    @PostMapping("/user/admin/save")
    R adminSave(@RequestBody User user);
}
