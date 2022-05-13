package com.nttdata.product.app.abstractions;

import com.nttdata.product.app.document.Account;
import com.nttdata.product.app.document.AccountType;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonNaturalAccountRequest;

public abstract class OperationsPersonNatural {
    
    public abstract EntidadDTO<Account> Dep(OperationPersonNaturalAccountRequest entidad, Account account, AccountType accountType);

    public abstract EntidadDTO<Account> Wit(OperationPersonNaturalAccountRequest entidad, Account account, AccountType accountType);

    public abstract EntidadDTO<Account> Pay(OperationPersonNaturalAccountRequest entidad, Account account, AccountType accountType);
}
