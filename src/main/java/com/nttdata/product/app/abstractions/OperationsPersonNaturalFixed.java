package com.nttdata.product.app.abstractions;

import java.util.Date;
import java.util.UUID;

import com.nttdata.product.app.document.Account;
import com.nttdata.product.app.document.AccountType;
import com.nttdata.product.app.document.Audit;
import com.nttdata.product.app.document.Operation;
import com.nttdata.product.app.document.OperationType;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonNaturalAccountRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationsPersonNaturalFixed extends OperationsPersonNatural {

    private static final Logger log = LoggerFactory.getLogger(OperationsPersonNaturalFixed.class);

    @Override
    public EntidadDTO<Account> Dep(OperationPersonNaturalAccountRequest entidad, Account p,
            AccountType at) {

        if (at.getMovementsConfig().getDepositDate().getDay() != (new Date()).getDay())
            return new EntidadDTO<Account>(false,
                    "Solo puede realizar retiros en las fecha indicadas.", null);
        else {
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
    public EntidadDTO<Account> Wit(OperationPersonNaturalAccountRequest entidad, Account p,
            AccountType at) {

        if (at.getMovementsConfig().getWithdrawalDate().getDay() != (new Date()).getDay())
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
    public EntidadDTO<Account> Pay(OperationPersonNaturalAccountRequest entidad, Account p,
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
