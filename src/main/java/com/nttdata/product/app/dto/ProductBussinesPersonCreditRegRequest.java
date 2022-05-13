package com.nttdata.product.app.dto;

import java.util.Collection;

import com.nttdata.product.app.document.ProductSchedulePay;
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
public class ProductBussinesPersonCreditRegRequest {
    private Double balance;
    private Collection<ProductSchedulePay> productSchedulePays;
    private String idClient;
    private ProductType productType;
    private State productState;
}
