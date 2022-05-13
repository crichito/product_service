package com.nttdata.product.app.dto;

import com.nttdata.product.app.document.AccountType;
import com.nttdata.product.app.document.State;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountSaveRegRequest {

    private String id;
    private String accountNumber;
    private Double balance;
    //private String idAccountType;
    //private String idState;
    private String idClient;

    private AccountType accountType;
    private State state;
    //private AccountBusiness accountBusiness;
    //private Collection<Operation> operations;
    
}
