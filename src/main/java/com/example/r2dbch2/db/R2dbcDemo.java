package com.example.r2dbch2.db;

import com.example.r2dbch2.entity.StockDO;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 使用ConnectionFactory操作数据库
 */
//@Component
public class R2dbcDemo implements CommandLineRunner {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("R2dbcDemo begin！");
        // 直接使用ConnectionFactory方式
        Publisher<? extends Connection> publisher = connectionFactory.create();
        Mono.from(publisher)
                .flatMapMany(connection -> connection.createStatement("select * from STOCK")
                        .execute())
                .concatMap(result -> result.map((row, rowMetadata) -> new StockDO(
                        String.valueOf(row.get("symbol")),
                        String.valueOf(row.get("name"))
                )))
                .subscribe(System.out::println);

        // 使用连接池方式
        ConnectionPoolConfiguration poolConfig = ConnectionPoolConfiguration.builder()
                .connectionFactory(connectionFactory)
                .maxSize(10)
                .build();

        ConnectionPool pool = new ConnectionPool(poolConfig);

        Mono<Connection> connectionFromPool = pool.create();
        connectionFromPool.flatMapMany(connection -> connection.createStatement("select * from STOCK")
                        .execute())
                .concatMap(result -> result.map((row, rowMetadata) -> new StockDO(
                        String.valueOf(row.get("symbol")),
                        String.valueOf(row.get("name"))
                )))
                .subscribe(System.out::println);
    }
}
