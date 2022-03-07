package com.example.r2dbch2;

import com.example.r2dbch2.dao.StockDao;
import com.example.r2dbch2.dao.StockSubscriptionDao;
import com.example.r2dbch2.dao.UserDao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class R2dbcH2Application {

    public static void main(String[] args) {
        SpringApplication.run(R2dbcH2Application.class, args);
    }

    @Bean
    public CommandLineRunner demo1(StockSubscriptionDao stockSubscriptionDao) {
        return ((args) -> {
            stockSubscriptionDao.findAll().subscribe(System.out::println);
        });
    }

    @Bean
    public CommandLineRunner demo2(UserDao userDao) {
        return ((args) -> {
            userDao.findAll().subscribe(System.out::println);
        });
    }

    @Bean
    public CommandLineRunner demo3(StockDao stockDao) {
        return ((args) -> {
            stockDao.findAll().subscribe(System.out::println);
        });
    }

}
