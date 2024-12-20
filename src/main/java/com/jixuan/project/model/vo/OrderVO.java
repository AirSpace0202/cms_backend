package com.jixuan.project.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 * @TableName order
 */
@Data
public class OrderVO implements Serializable {
    /**
     * 订单编号
     */
    private Long id;

    /**
     * 客户编号
     */
    private Long custId;

    /**
     * 商品名
     */
    private String product;

    /**
     * 订单金额
     */
    private BigDecimal totalNum;

    /**
     * 订单状态 0-未完成 1-已完成
     */
    private Integer status;

    /**
     * 支付方式 0-支付宝 1-微信
     */
    private Integer method;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}