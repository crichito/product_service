package com.nttdata.product.app.dto;

import java.util.Collection;

import com.nttdata.product.app.document.Operation;
import com.nttdata.product.app.document.ProductType;
import com.nttdata.product.app.document.State;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductNaturalPersonCreditCardListResponse {
    private String id;
    private String productCode;
    private Double balance;
    private Double creditLimit;
    private String idClient;
    private Collection<Operation> operations;
    private ProductType productType;
    private State productState;
}
