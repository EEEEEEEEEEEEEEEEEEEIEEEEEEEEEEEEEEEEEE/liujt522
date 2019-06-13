package com.itheima.controller;

import com.itheima.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext1.xml")
public class AccountController {
    @Test
    public void Test(){
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext1.xml");
        AccountService accountService = app.getBean(AccountService.class);
        accountService.transfer("zhangsan","lisi",500);

        int i= 10;
        i+=10;
    }

}
