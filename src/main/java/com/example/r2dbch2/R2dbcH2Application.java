package com.example.r2dbch2;

import com.example.r2dbch2.config.RouterConfig;
import com.example.r2dbch2.dao.StockDao;
import com.example.r2dbch2.dao.StockSubscriptionDao;
import com.example.r2dbch2.dao.UserDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootApplication
public class R2dbcH2Application {

    public static void main(String[] args) {
        // 摆脱注解声明的方式
        new SpringApplicationBuilder().sources(R2dbcH2Application.class)
                .initializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> {
                    ctx.registerBean("apiRouter", RouterFunction.class, RouterConfig::apiRouter);
                })
                .run(args);
    }

    //@Bean
    public CommandLineRunner demo1(StockSubscriptionDao stockSubscriptionDao) {
        return ((args) -> {
            stockSubscriptionDao.findAll().subscribe(System.out::println);
        });
    }

    //@Bean
    public CommandLineRunner demo2(UserDao userDao) {
        return ((args) -> {
            userDao.findAll().subscribe(System.out::println);
        });
    }

    //@Bean
    public CommandLineRunner demo3(StockDao stockDao) {
        return ((args) -> {
            stockDao.findAll().subscribe(System.out::println);
        });
    }

}
