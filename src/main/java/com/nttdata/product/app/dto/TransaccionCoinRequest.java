package com.nttdata.product.app.dto;

import com.nttdata.product.app.document.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransaccionCoinRequest {
    private String idVirualCoint;
    private String idVirtualCointSeller;
    private int total;
    private Exchange exchange;
    private AccountTransacction accountTransacction;
    private PhoneTransaccion phoneTransaccion;
}
