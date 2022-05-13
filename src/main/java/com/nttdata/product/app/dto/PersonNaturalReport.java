package com.nttdata.product.app.dto;

import java.util.Collection;

import com.nttdata.product.app.document.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonNaturalReport {
    private Collection<AccountNaturalPersonListResponse> accounts;
    private Collection<Product> products;
}
