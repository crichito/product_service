package com.nttdata.product.app.dto;

import java.util.Collection;

import com.nttdata.product.app.document.AccountType;
import com.nttdata.product.app.document.Operation;
import com.nttdata.product.app.document.State;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountBussinesPersonOperationListResponse {
    private String accountNumber;
    private Double balance;
    private Collection<String> idClients;
    private AccountType accountType;
    private State state;
    private Collection<Operation> operations;
}
