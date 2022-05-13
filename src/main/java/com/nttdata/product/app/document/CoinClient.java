package com.nttdata.product.app.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class CoinClient {

    private String id;
    private String document;
    private String phone;
    private String email;
}
