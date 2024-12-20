package com.jixuan.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jixuan.project.model.entity.Order;
import com.jixuan.project.service.OrderService;
import com.jixuan.project.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author Jaychou
* @description 针对表【order(订单)】的数据库操作Service实现
* @createDate 2024-12-18 09:08:51
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




