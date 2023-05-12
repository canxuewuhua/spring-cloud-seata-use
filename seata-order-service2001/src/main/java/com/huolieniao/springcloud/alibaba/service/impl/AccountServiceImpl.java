package com.huolieniao.springcloud.alibaba.service.impl;

import com.huolieniao.springcloud.alibaba.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Override
    public void decrease(Long userId, BigDecimal money) {

    }
}
