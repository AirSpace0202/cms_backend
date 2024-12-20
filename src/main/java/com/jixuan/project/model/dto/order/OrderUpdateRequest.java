package com.jixuan.project.model.dto.order;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单添加请求
 */
@Data
public class OrderUpdateRequest implements Serializable {

    /**
     * 订单编号
     */
    private Long id;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}