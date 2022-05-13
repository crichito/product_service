package com.nttdata.product.app.dto;

import java.util.Collection;

import com.nttdata.product.app.document.AccountBusiness;
import com.nttdata.product.app.document.AccountType;
import com.nttdata.product.app.document.Operation;
import com.nttdata.product.app.document.State;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountNaturalPersonListResponse {
    
    private String id;
    private String accountNumber;
    private Double balance;
    //private String idAccountType;
    //private String idState;
    private String idClient;
    private String accountTypeDescription;
    private String stateDescription;

    //private AccountBusiness accountBusiness;
    //private Collection<Operation> operations;
}
