package com.nttdata.product.app.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationCoin {

    private String operation;
    private int total;
    private String idVirtualCoinDetiny;
    private Date dateOperation;
    private boolean confirm;
    private Exchange exchange;
    private OperationType operationType;
    private AccountTransacction accountTransacction;
    private PhoneTransaccion phoneTransaccion;

}
