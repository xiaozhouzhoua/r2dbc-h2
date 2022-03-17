package com.example.r2dbch2.db;

import com.example.r2dbch2.entity.StockDO;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring5.3之前使用R2dbcEntityTemplate进行CRUD
 * 功能不够多，后面使用DatabaseClient了
 */
//@Component
public class R2dbcEntityTemplateDemo implements CommandLineRunner {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("R2dbcEntityTemplateDemo begin！");
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);
        insert(template).thenMany(select(template)).subscribe(System.out::println);
    }

    private Flux<StockDO> select(R2dbcEntityTemplate template) {
        return template.select(StockDO.class)
                .from("STOCK")
                .matching(Query.query(Criteria.where("symbol").like("T%")))
                .all();
    }

    private Mono<StockDO> insert(R2dbcEntityTemplate template) {
        return template.insert(StockDO.class)
                .using(new StockDO("TEL", "Telegram"));
    }

    private Mono<Integer> update(R2dbcEntityTemplate template) {
        return template.update(StockDO.class)
                .inTable("stock")
                .matching(Query.query(Criteria.where("symbol").like("T%")))
                .apply(Update.update("name", "FOO"));
    }

    private Mono<Integer> delete(R2dbcEntityTemplate template) {
        return template.delete(StockDO.class)
                .from("stock")
                .matching(Query.query(Criteria.where("symbol").like("T%")))
                .all();
    }
}
