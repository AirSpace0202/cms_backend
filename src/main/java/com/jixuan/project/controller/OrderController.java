package com.jixuan.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.jixuan.project.common.BaseResponse;
import com.jixuan.project.common.DeleteRequest;
import com.jixuan.project.common.ErrorCode;
import com.jixuan.project.common.ResultUtils;
import com.jixuan.project.exception.BusinessException;
import com.jixuan.project.model.dto.order.OrderAddRequest;
import com.jixuan.project.model.dto.order.OrderQueryRequest;
import com.jixuan.project.model.dto.order.OrderSearchRequest;
import com.jixuan.project.model.dto.order.OrderUpdateRequest;
import com.jixuan.project.model.entity.Order;
import com.jixuan.project.model.vo.OrderVO;
import com.jixuan.project.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;

    /**
     * 添加订单
     * @param orderAddRequest 添加请求
     * @param request 前端请求
     * @return 订单编号
     */
    @PostMapping("/add")
    public BaseResponse<Long> addOrder(@RequestBody OrderAddRequest orderAddRequest, HttpServletRequest request) {
        if (orderAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderAddRequest, order);
        boolean result = orderService.save(order);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(order.getId());
    }

    /**
     * 删除订单
     * @param deleteRequest 删除请求
     * @param request 前端请求
     * @return 删除结果
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteOrder(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = orderService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    /**
     * 更新订单
     * @param orderUpdateRequest 更新请求
     * @param request 前端请求
     * @return 更新结果
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateOrder(@RequestBody OrderUpdateRequest orderUpdateRequest, HttpServletRequest request) {
        if (orderUpdateRequest == null || orderUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderUpdateRequest, order);
        boolean result = orderService.updateById(order);
        return ResultUtils.success(result);
    }

    /**
     * 分页查询订单
     * @param orderQueryRequest 查询请求
     * @param request 前端请求
     * @return 查询结果
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<OrderVO>> listCustomerByPage(OrderQueryRequest orderQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        Order orderQuery = new Order();
        if (orderQueryRequest != null) {
            BeanUtils.copyProperties(orderQueryRequest, orderQuery);
            current = orderQueryRequest.getCurrent();
            size = orderQueryRequest.getPageSize();
        }
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>(orderQuery);
        Page<Order> orderPage = orderService.page(new Page<>(current, size), queryWrapper);
        Page<OrderVO> orderVOPage = new PageDTO<>(orderPage.getCurrent(), orderPage.getSize(), orderPage.getTotal());
        List<OrderVO> orderVOList = orderPage.getRecords().stream().map(order -> {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            return orderVO;
        }).collect(Collectors.toList());
        orderVOPage.setRecords(orderVOList);
        return ResultUtils.success(orderVOPage);
    }

    /**
     * 订单查询
     * @param orderSearchRequest 订单查询请求
     * @return 查询结果
     */
    @PostMapping("/search")
    public BaseResponse<Page<OrderVO>> searchOrders(@RequestBody OrderSearchRequest orderSearchRequest) {
        if (orderSearchRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long current = orderSearchRequest.getCurrent();
        long pageSize = orderSearchRequest.getPageSize();

        // 构建查询条件
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDelete", 0);

        // 添加搜索条件
        if (orderSearchRequest.getCustId() != null) {
            queryWrapper.eq("custId", orderSearchRequest.getCustId());
        }
        if (StringUtils.isNotBlank(orderSearchRequest.getProduct())) {
            queryWrapper.like("product", orderSearchRequest.getProduct());
        }
        if (orderSearchRequest.getTotalNum() != null) {
            queryWrapper.eq("totalNum", orderSearchRequest.getTotalNum());
        }
        if (orderSearchRequest.getStatus() != null) {
            queryWrapper.eq("status", orderSearchRequest.getStatus());
        }
        if (orderSearchRequest.getMethod() != null) {
            queryWrapper.eq("method", orderSearchRequest.getMethod());
        }

        // 按创建时间降序排序
        queryWrapper.orderByDesc("createTime");

        // 执行分页查询
        Page<Order> orderPage = orderService.page(
                new Page<>(current, pageSize),
                queryWrapper
        );

        // 转换为VO对象
        Page<OrderVO> orderVOPage = new PageDTO<>(
                orderPage.getCurrent(),
                orderPage.getSize(),
                orderPage.getTotal()
        );

        // 转换记录
        List<OrderVO> orderVOList = orderPage.getRecords().stream()
                .map(order -> {
                    OrderVO orderVO = new OrderVO();
                    BeanUtils.copyProperties(order, orderVO);
                    return orderVO;
                })
                .collect(Collectors.toList());

        orderVOPage.setRecords(orderVOList);
        return ResultUtils.success(orderVOPage);
    }

}



