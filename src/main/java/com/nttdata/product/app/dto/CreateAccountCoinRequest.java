package com.nttdata.product.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateAccountCoinRequest {
    private String document;
    private String phone;
    private String email;
    private boolean seller;
    private String account;
    private Double total;
}
