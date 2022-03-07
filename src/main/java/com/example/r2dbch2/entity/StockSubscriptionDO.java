package com.example.r2dbch2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("STOCK_SUBSCRIPTION")
@Data
@NoArgsConstructor
public class StockSubscriptionDO {
    @Id
    private Long id;
    private String email;
    private String symbol;
}
