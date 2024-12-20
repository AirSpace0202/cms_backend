package com.jixuan.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.jixuan.project.common.BaseResponse;
import com.jixuan.project.common.DeleteRequest;
import com.jixuan.project.common.ErrorCode;
import com.jixuan.project.common.ResultUtils;
import com.jixuan.project.exception.BusinessException;
import com.jixuan.project.model.dto.customer.CustomerAddRequest;
import com.jixuan.project.model.dto.customer.CustomerQueryRequest;
import com.jixuan.project.model.dto.customer.CustomerSearchRequest;
import com.jixuan.project.model.dto.customer.CustomerUpdateRequest;
import com.jixuan.project.model.entity.Customer;
import com.jixuan.project.model.vo.CustomerVO;
import com.jixuan.project.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户管理接口
 */

@RestController
@Slf4j
@RequestMapping("/customer")
public class CustomerController {

    @Resource
    CustomerService customerService;

    /**
     * 新增客户
     * @param customerAddRequest 添加请求
     * @param request 前端请求
     * @return 客户 id
     */
    @PostMapping("/add")
    public BaseResponse<Long> addCustomer(@RequestBody CustomerAddRequest customerAddRequest, HttpServletRequest request) {
        if (customerAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerAddRequest, customer);
        boolean result = customerService.save(customer);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(customer.getId());
    }

    /**
     * 删除客户
     * @param deleteRequest 删除请求
     * @param request 前端请求
     * @return 删除结果
     */
    @PostMapping("delete")
    public BaseResponse<Boolean> deleteCustomer(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = customerService.removeById(deleteRequest.getId());
        return ResultUtils.success(result);
    }

    /**
     * 更新客户
     * @param customerUpdateRequest 更新请求
     * @param request 前端请求
     * @return 更新结果
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody CustomerUpdateRequest customerUpdateRequest, HttpServletRequest request) {
        if (customerUpdateRequest == null || customerUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerUpdateRequest, customer);
        boolean result = customerService.updateById(customer);
        return ResultUtils.success(result);
    }

    /**
     * 分页查询客户列表
     * @param customerQueryRequest 查询请求
     * @param request 前端请求
     * @return 查询结果
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<CustomerVO>> listCustomerByPage(CustomerQueryRequest customerQueryRequest, HttpServletRequest request) {
        long current = 1;
        long size = 10;
        Customer customerQuery = new Customer();
        if (customerQueryRequest != null) {
            BeanUtils.copyProperties(customerQueryRequest, customerQuery);
            current = customerQueryRequest.getCurrent();
            size = customerQueryRequest.getPageSize();
        }
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>(customerQuery);
        Page<Customer> customerPage = customerService.page(new Page<>(current, size), queryWrapper);
        Page<CustomerVO> customerVOPage = new PageDTO<>(customerPage.getCurrent(), customerPage.getSize(), customerPage.getTotal());
        List<CustomerVO> customerVOList = customerPage.getRecords().stream().map(customer -> {
            CustomerVO customerVO = new CustomerVO();
            BeanUtils.copyProperties(customer, customerVO);
            return customerVO;
        }).collect(Collectors.toList());
        customerVOPage.setRecords(customerVOList);
        return ResultUtils.success(customerVOPage);
    }


    @PostMapping("/search")
    public BaseResponse<Page<CustomerVO>> searchCustomers(@RequestBody CustomerSearchRequest customerSearchRequest) {
        if (customerSearchRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long current = customerSearchRequest.getCurrent();
        long pageSize = customerSearchRequest.getPageSize();

        // 构建查询条件
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDelete", 0);

        // 添加搜索条件
        if (StringUtils.isNotBlank(customerSearchRequest.getCustName())) {
            queryWrapper.like("custName", customerSearchRequest.getCustName());
        }
        if (StringUtils.isNotBlank(customerSearchRequest.getCustSource())) {
            queryWrapper.like("custSource", customerSearchRequest.getCustSource());
        }
        if (StringUtils.isNotBlank(customerSearchRequest.getCustIndustry())) {
            queryWrapper.like("custIndustry", customerSearchRequest.getCustIndustry());
        }
        if (StringUtils.isNotBlank(customerSearchRequest.getPhone())) {
            queryWrapper.like("phone", customerSearchRequest.getPhone());
        }

        // 执行分页查询
        Page<Customer> customerPage = customerService.page(
                new Page<>(current, pageSize),
                queryWrapper
        );

        // 转换为VO对象
        Page<CustomerVO> customerVOPage = new PageDTO<>(
                customerPage.getCurrent(),
                customerPage.getSize(),
                customerPage.getTotal()
        );

        // 转换记录
        List<CustomerVO> customerVOList = customerPage.getRecords().stream()
                .map(customer -> {
                    CustomerVO customerVO = new CustomerVO();
                    BeanUtils.copyProperties(customer, customerVO);
                    return customerVO;
                })
                .collect(Collectors.toList());

        customerVOPage.setRecords(customerVOList);
        return ResultUtils.success(customerVOPage);
    }

}