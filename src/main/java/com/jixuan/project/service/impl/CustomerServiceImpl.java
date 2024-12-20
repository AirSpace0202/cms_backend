package com.jixuan.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jixuan.project.model.entity.Customer;
import com.jixuan.project.service.CustomerService;
import com.jixuan.project.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

/**
* @author Jaychou
* @description 针对表【customer(客户表)】的数据库操作Service实现
* @createDate 2024-12-12 12:00:24
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
    implements CustomerService{

}




