package com.example.r2dbch2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("STOCK")
@Data
@NoArgsConstructor
public class StockDO {
    @Id
    private String symbol;

    private String name;
}
