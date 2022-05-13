package com.nttdata.product.app.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "virtual_coin")
public class VitualCoin {
    @Id
    private String id;
    private CoinClient coinClient;
    private int total;
    private Optional<Collection<OperationCoin>> operationCoins;
    private boolean flagSeller;
    private Optional<Account> account;

}
