package com.nttdata.product.app.dto;

import java.util.Collection;

import com.nttdata.product.app.document.Operation;
import com.nttdata.product.app.document.ProductType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardBussinesPersonOperationListResponse {
    private String productCode;
    private Double balance;
    private Double creditLimit;
    private Collection<String> idClients;
    private ProductType productType;
    private Collection<Operation> operations;
}
