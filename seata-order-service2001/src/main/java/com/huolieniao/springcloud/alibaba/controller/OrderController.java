package com.huolieniao.springcloud.alibaba.controller;

import com.huolieniao.springcloud.alibaba.domain.CommonResult;
import com.huolieniao.springcloud.alibaba.domain.Order;
import com.huolieniao.springcloud.alibaba.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;


    @GetMapping("/order/create")
    public CommonResult create(Order order)
    {
        orderService.create(order);
        return new CommonResult(200,"订单创建成功");
    }

    @GetMapping("count")
    public CommonResult count()
    {
        int count = orderService.count();
        return new CommonResult(200,"订单数量查询成功", count);
    }
}
