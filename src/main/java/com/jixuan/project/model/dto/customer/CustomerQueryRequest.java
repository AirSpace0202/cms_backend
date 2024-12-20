package com.jixuan.project.model.dto.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jixuan.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerQueryRequest extends PageRequest implements Serializable {
    /**
     * 客户 id
     */
    private String id;

    /**
     * 客户名称
     */
    private String custName;


    /**
     * 客户信息来源
     */
    private String custSource;

    /**
     * 客户行业
     */
    private String custIndustry;

    /**
     * 性别（0-男, 1-女）
     */
    private Integer gender;

    /**
     * 客户电话
     */
    private String phone;

    /**
     * 客户级别
     */
    private Integer level;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
