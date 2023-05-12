package com.huolieniao.springcloud.alibaba.domain;

import lombok.Data;
import java.math.BigDecimal;

@Data
//@TableName("t_order")
public class Order {


    private Long id;
    private Long userId;
    private Long productId;
    private Integer count;
    private BigDecimal money;
    private Integer status;
}
