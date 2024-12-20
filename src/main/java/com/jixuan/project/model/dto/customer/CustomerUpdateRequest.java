package com.jixuan.project.model.dto.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerUpdateRequest implements Serializable {

    /**
     * 客户 id
     */
    private Long id;

    /**
     * 客户名称
     */
    private String custName;

    /**
     * 负责人id
     */
    private Integer custUserId;

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
