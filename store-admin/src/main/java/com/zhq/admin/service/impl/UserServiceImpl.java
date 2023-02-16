package com.zhq.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhq.admin.service.UserService;
import com.zhq.clients.UserClient;
import com.zhq.param.CartListParam;
import com.zhq.param.PageParam;
import com.zhq.pojo.User;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserClient userClient;

    /**
     * 用户的展示业务的方法
     *
     * @param pageParam
     * @return
     */
    @Cacheable(value = "list.user", key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    @Override
    public R userList(PageParam pageParam) {
        R r = userClient.adminListPage(pageParam);
        return r;
    }


    /**
     * 用户删除业务方法
     *
     * @param cartListParam
     * @return
     */
    @CacheEvict(value = "list.user", allEntries = true)//清空缓存
    @Override
    public R userRemove(CartListParam cartListParam) {
        R r = userClient.adminRemove(cartListParam);
        return r;
    }

    /**
     * 用户修改业务的方法
     *
     * @param user
     * @return
     */
    @CacheEvict(value = "list.user", allEntries = true)//清空缓存
    @Override
    public R userUpdate(User user) {
        R r = userClient.adminUpdate(user);
        return r;
    }

    /**
     * 用户添加业务的方法
     *
     * @param user
     * @return
     */
    @CacheEvict(value = "list.user", allEntries = true)//清空缓存
    @Override
    public R userSave(User user) {
        R r = userClient.adminSave(user);
        return r;
    }
}
