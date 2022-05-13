package com.nttdata.product.app.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConfirmTransaccionCoinRequest {
    private String idVirualCoint;
    private String operation;
    private boolean confirmation;
}
