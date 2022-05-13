package com.nttdata.product.app.dto;

import com.nttdata.product.app.document.ProductType;
import com.nttdata.product.app.document.State;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductNaturalPersonalCreditCardRegRequest {
    private Double creditLimit;
    private String idClient;
    private ProductType productType;
    private State productState;
}
