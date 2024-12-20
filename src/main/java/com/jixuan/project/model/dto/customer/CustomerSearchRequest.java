package com.jixuan.project.model.dto.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerSearchRequest implements Serializable {
    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 客户名称
     */
    private String custName;

    /**
     * 客户来源
     */
    private String custSource;

    /**
     * 客户行业
     */
    private String custIndustry;

    /**
     * 联系电话
     */
    private String phone;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}