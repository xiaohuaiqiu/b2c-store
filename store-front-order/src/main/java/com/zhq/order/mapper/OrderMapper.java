package com.zhq.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhq.pojo.Order;
import com.zhq.vo.AdminOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * order数据库调用
 */

public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 查询后台管理的方法
     * @param offset
     * @param pageSize
     * @return
     */
    List<AdminOrderVo> selectAdminOrder(@Param("offset") int offset, @Param("pageSize") int pageSize);
}
