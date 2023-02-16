package com.zhq.user.servcie.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhq.constants.UserConstants;
import com.zhq.param.PageParam;
import com.zhq.param.UserCheckParam;
import com.zhq.param.UserLoginParam;
import com.zhq.pojo.User;
import com.zhq.user.mapper.UserMapper;
import com.zhq.user.servcie.UserService;
import com.zhq.utils.MD5Util;
import com.zhq.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.LockInfo;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 检查账号是否可用业务
     *
     * @param userCheckParam 账号参数 已经校验完毕
     * @return 检查结果 001 004
     */
    @Override
    public R check(UserCheckParam userCheckParam) {
        //参数封装
        QueryWrapper queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("user_name", userCheckParam.getUserName());
        //数据库查询
        Long total = userMapper.selectCount(queryWrapper);
        //查询结果的处理
        if (total == 0) {
            //数据库不存在,可用
            log.info("UserServiceImpl,check业务结束，结果:{}", "账号可以使用!");
            return R.ok("账号可用");
        }
        log.info("UserServiceImpl,check业务结束，结果:{}", "账号不可使用!");
        return R.fail("账号已经存在,不可以注册");
    }

    /**
     * 注册业务
     * 1.检查账号是否存在
     * 2.密码加密处理
     * 3.插入数据库数据
     * 4.返回结果封装
     *
     * @param user 参数已经校验 密码加密
     * @return 结果 001 004
     */
    @Override
    public R register(User user) {
        //1.检查账号是否存在
        QueryWrapper queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("user_name", user.getUserName());
        //数据库查询
        Long aLong = userMapper.selectCount(queryWrapper);
        if (aLong > 0) {
            log.info("UserServiceImpl,register业务结束，结果:{}", "账号不可使用!");
            return R.fail("账号已存在!");
        }
        //2. 密码加密处理 要加盐处理
        String newPwd = MD5Util.encode(user.getPassword() + UserConstants.USER_SLAT);
        user.setPassword(newPwd);

        //3. 插入数据库数据
        int rows = userMapper.insert(user);
        //4. 返回结果封装
        if (rows == 0) {
            log.info("UserServiceImpl,register业务结束，结果:{}", "注册失败!");
            return R.fail("注册失败!");
        }
        log.info("UserServiceImpl,register业务结束，结果:{}", "注册成功!");
        return R.ok("注册成功!");
    }

    /**
     * 登录业务
     * 1.进行密码的加密加盐
     * 2.根据账号和密码进行数据库查询 返回一个完整的数据库对象
     * 3.判断返回结果
     *
     * @param userLoginParam 账号和密码 已经校验 密码加密
     * @return 结果 001 004
     */
    @Override
    public R login(UserLoginParam userLoginParam) {
        //1.MD5密码加盐加密处理
        String newPwd = MD5Util.encode(userLoginParam.getPassword() + UserConstants.USER_SLAT);
        //2.数据库查询
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userLoginParam.getUserName());
        queryWrapper.eq("password", newPwd);

        User user = userMapper.selectOne(queryWrapper);
        //3.判断返回结果
        if (user == null) {
            log.info("UserServiceImpl,login业务结束，结果:{}", "账号密码错误!");
            return R.fail("账号或者密码输入错误!");
        }
        log.info("UserServiceImpl,login业务结束，结果:{}", "登录成功!");
        user.setPassword(null); //对应上@JsonInclude(JsonInclude.Include.NON_ABSENT)这个注解，实现不返回password属性
        return R.ok("登录成功", user);
    }

    /**
     * 后台管理调用,查询全部用户数据 带分页
     *
     * @param param
     * @return
     */
    @Override
    public R listPage(PageParam param) {
        //封装分页类
        IPage<User> page = new Page<>(param.getCurrentPage(), param.getPageSize());

        page = userMapper.selectPage(page, null);

        List<User> records = page.getRecords(); //分页的数据
        long total = page.getTotal(); //分页总数量

        return R.ok("用户管理查询成功!", records, total);
    }
}
