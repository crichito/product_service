package com.nttdata.product.app.abstractions;

import com.nttdata.product.app.document.Account;
import com.nttdata.product.app.document.AccountType;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonBussinesAccountRequest;

public abstract class OperationsPersonBussines {
    public abstract EntidadDTO<Account> Dep(OperationPersonBussinesAccountRequest entidad, Account account, AccountType accountType);

    public abstract EntidadDTO<Account> Wit(OperationPersonBussinesAccountRequest entidad, Account account, AccountType accountType);

    public abstract EntidadDTO<Account> Pay(OperationPersonBussinesAccountRequest entidad, Account account, AccountType accountType);
}
