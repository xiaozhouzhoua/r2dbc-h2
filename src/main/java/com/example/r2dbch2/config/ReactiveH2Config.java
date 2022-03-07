package com.example.r2dbch2.config;

import com.example.r2dbch2.entity.converter.UserDoReadConverter;
import com.example.r2dbch2.entity.converter.UserDoWriteConverter;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableR2dbcRepositories
public class ReactiveH2Config {

    @Bean
    public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory factory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(factory);

        // h2数据库数据初始化
        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(
                new ResourceDatabasePopulator(new ClassPathResource("db/schema.sql")),
                new ResourceDatabasePopulator(new ClassPathResource("db/data.sql"))
        );
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    @Bean
    public R2dbcCustomConversions conversions() {
        List<Converter<?,?>> converters = new ArrayList<>();
        converters.add(new UserDoReadConverter());
        converters.add(new UserDoWriteConverter());
        return new R2dbcCustomConversions(converters);
    }
}
