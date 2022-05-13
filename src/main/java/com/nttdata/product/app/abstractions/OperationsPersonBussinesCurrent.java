package com.nttdata.product.app.abstractions;

import java.util.Date;
import java.util.UUID;

import com.nttdata.product.app.document.Account;
import com.nttdata.product.app.document.AccountType;
import com.nttdata.product.app.document.Audit;
import com.nttdata.product.app.document.Operation;
import com.nttdata.product.app.document.OperationType;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonBussinesAccountRequest;

public class OperationsPersonBussinesCurrent extends OperationsPersonBussines {

    @Override
    public EntidadDTO<Account> Dep(OperationPersonBussinesAccountRequest entidad, Account p,
            AccountType at) {
        
                if (entidad.getValue() < at.getCommission()) {

                    return new EntidadDTO<Account>(false,
                    "El monto de deposito debe ser mayor a la comision.", null);
                } else {
        
                    Operation operation = new Operation(
                            UUID.randomUUID().toString().replace("-", ""),
                            new OperationType("DEP", "Deposito"),
                            entidad.getDescription(),
                            entidad.getValue(),
                            at.getCommission(),
                            entidad.getValue() - at.getCommission(),
                            new Date(),
                            new Audit(entidad.getIdClient(), new Date(), null, null),
                            entidad.getChannelOperation());
        
                    p.getOperations().add(operation);
        
                    return new EntidadDTO<Account>(true,
                            "Operación permitida.", p);
        
                    }
    }

    @Override
    public EntidadDTO<Account> Wit(OperationPersonBussinesAccountRequest entidad, Account p,
            AccountType at) {

                if (entidad.getValue() <= at.getCommission())
                return new EntidadDTO<Account>(false,
                        "El valor a retirar debe ser mayor a la comision.", null);
            else if (entidad.getValue() > (p.getBalance() + at.getCommission()))
                return new EntidadDTO<Account>(false,
                        "El valor a retirar debe ser mayor a la saldo de la cuenta.", null);
            else {
                Operation operation = new Operation(
                        UUID.randomUUID().toString().replace("-", ""),
                        new OperationType("WIT", "Retiro"),
                        entidad.getDescription(),
                        entidad.getValue(),
                        at.getCommission(),
                        entidad.getValue() - at.getCommission(),
                        new Date(),
                        new Audit(entidad.getIdClient(), new Date(), null, null),
                        entidad.getChannelOperation());
    
                p.getOperations().add(operation);
    
                return new EntidadDTO<Account>(true,
                        "Operación permitida.", p);
            }
    }

    @Override
    public EntidadDTO<Account> Pay(OperationPersonBussinesAccountRequest entidad, Account p,
            AccountType at) {
        if (entidad.getValue() >= (p.getBalance()))
            return new EntidadDTO<Account>(false,
                    "El monto a pagar no debe superar el saldo de la cuenta", null);
        else {
            Operation operation = new Operation(
                    UUID.randomUUID().toString().replace("-", ""),
                    new OperationType("PAY", "Pago"),
                    entidad.getDescription(),
                    entidad.getValue(),
                    at.getCommission(),
                    entidad.getValue() - at.getCommission(),
                    new Date(),
                    new Audit(entidad.getIdClient(), new Date(), null, null),
                    entidad.getChannelOperation());

            p.getOperations().add(operation);

            return new EntidadDTO<Account>(true,
                    "Operación permitida.", p);
        }
    }
    
}
