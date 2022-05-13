package com.nttdata.product.app.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection="account_type")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class AccountType {
    
    @Id
    private String id;
    private String description;
    private Double commission;
    private Integer depositMovements;
    private Integer withdrawalMovements;
    private MovementsConfig movementsConfig;
}
