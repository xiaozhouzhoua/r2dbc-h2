package com.example.r2dbch2.db;

import com.example.r2dbch2.entity.StockDO;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * DatabaseClient使用
 */
@Component
public class R2dbcDatabaseClientDemo implements CommandLineRunner {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public void run(String... args) throws Exception {
        ReactiveTransactionManager rtm = new R2dbcTransactionManager(connectionFactory);
        TransactionalOperator to = TransactionalOperator.create(rtm);

        DatabaseClient client = DatabaseClient.create(connectionFactory);

        select(client).subscribe(System.out::println);
        find("TSLA", client).subscribe(System.out::println);
        insert(new StockDO("TEL", "Telegram"), client).subscribe(System.out::println);

        // 事务作用于整个update
        update(client).then(insert(new StockDO("TEL", "Telegram"), client).then())
                .as(to::transactional).subscribe(System.out::println);
        // 只作用于insert
        update(client).then(to.transactional(insert(new StockDO("TEL", "Telegram"), client)).then())
                .subscribe(System.out::println);
    }

    private Mono<StockDO> select(DatabaseClient client) {
        return client.sql("select symbol, name from stock")
                .map(row -> new StockDO(
                        row.get("symbol", String.class),
                        row.get("name", String.class)
                ))
                .first();
    }

    private Flux<StockDO> find(String symbol, DatabaseClient client) {
        return client.sql("select symbol, name from stock where symbol like :symbol")
                .bind("symbol", symbol)
                .filter(statement -> statement.fetchSize(2))
                .map(row -> new StockDO(
                        row.get("symbol", String.class),
                        row.get("name", String.class)
                )).all();
    }

    @Transactional
    public Mono<StockDO> insert(StockDO stockDO, DatabaseClient client) {
        return client.sql("insert into stock(symbol, name) values (:symbol, :name )")
                .bind("symbol", stockDO.getSymbol())
                .bind("name", stockDO.getName())
                .filter(statement -> statement.returnGeneratedValues("symbol", "name"))
                .fetch()
                .first()
                .map(r -> new StockDO((String) r.get("symbol"), (String) r.get("name")));
    }

    public Mono<Integer> update(DatabaseClient client) {
        return client.sql("update stock set name=:name where symbol=:symbol")
                .bind("name", "FOO")
                .bind("symbol", "APPL")
                .fetch()
                .rowsUpdated();
    }
}
