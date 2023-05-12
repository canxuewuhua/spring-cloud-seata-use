package com.huolieniao.springcloud.alibaba.service;

import com.huolieniao.springcloud.alibaba.domain.Order;

public interface OrderService {

    void create(Order order);

    int count();
}
