package com.nttdata.product.app.dto;

import com.nttdata.product.app.document.AccountBusiness;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBusinessPersonListResponse {
    private String id;
    private String accountNumber;
    private Double balance;
    //private String idAccountType;
    //private String idState;
    private String idClient;
    private String accountTypeDescription;
    private String stateDescription;

    private AccountBusiness accountBusiness;
    //private Collection<Operation> operations;
}
