package com.zhq.user.servcie;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhq.param.PageParam;
import com.zhq.param.UserCheckParam;
import com.zhq.param.UserLoginParam;
import com.zhq.pojo.User;
import com.zhq.utils.R;

public interface UserService extends IService<User> {
    /**
     * 检查账号是否可用业务
     *
     * @param userCheckParam 账号参数 已经校验完毕
     * @return 检查结果 001 004
     */
    R check(UserCheckParam userCheckParam);

    /**
     * 注册业务
     * @param user 参数已经校验 密码加密
     * @return 结果 001 004
     */
    R register(User user);

    /**
     * 登录业务
     * @param userLoginParam 账号和密码 已经校验 密码加密
     * @return 结果 001 004
     */
    R login(UserLoginParam userLoginParam);

    /**
     * 后台管理调用,查询全部用户数据 带分页
     * @param param
     * @return
     */
    R listPage(PageParam param);


}
