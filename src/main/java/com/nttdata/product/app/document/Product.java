package com.nttdata.product.app.document;


import java.util.Collection;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection="product")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Product {
    @Id
    private String id;
    private String productCode;
    private Double balance;
    private Double creditLimit;
    private String idProductType;
    private String idState;
    private Collection<ProductSchedulePay> productSchedulePays;
    private Collection<String> idClients;
    private Collection<Operation> operations;
    private Card card;

    private ProductType productType;
    private State productState;
    //@Transient
    //private Collection<Client> clients;
    
    
}
