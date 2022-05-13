package com.nttdata.product.app.dto;

import java.util.Collection;

import com.nttdata.product.app.document.AccountBusiness;
import com.nttdata.product.app.document.AccountType;
import com.nttdata.product.app.document.State;

import org.springframework.expression.Operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountBussinesRequest {
    //private String id;
    private String accountNumber;
    private Double balance;
    //private String idAccountType;
    //private String idState;
    private Collection<String> idClients;

    private AccountType accountType;
    private State state;
    private AccountBusiness accountBusiness;
    private Collection<Operation> operations;
}
