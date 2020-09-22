package com.jt.order.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.order.pojo.Order;
import com.jt.order.pojo.OrderItem;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper extends SysMapper<Order>{

	void updateStatus(Date timeOut);
  
}