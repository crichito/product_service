package com.nttdata.product.app.ImplService;

import java.util.ArrayList;
import java.util.UUID;

import com.nttdata.product.app.abstractions.OperationsPersonBussines;
import com.nttdata.product.app.abstractions.OperationsPersonBussinesCurrent;
import com.nttdata.product.app.document.Account;
import com.nttdata.product.app.document.Operation;
import com.nttdata.product.app.dto.AccountBusinessPersonListResponse;
import com.nttdata.product.app.dto.AccountBussinesPersonOperationListResponse;
import com.nttdata.product.app.dto.AccountBussinesRequest;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonBussinesAccountRequest;
import com.nttdata.product.app.repository.AccountRepository;
import com.nttdata.product.app.repository.AccountTypeRepository;
import com.nttdata.product.app.service.AccountBusinessPersonService;
import com.nttdata.product.app.util.AccountTypeEnum;
import com.nttdata.product.app.util.GenericFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountBussinesPersonImpl implements AccountBusinessPersonService {

        private static final Logger log = LoggerFactory.getLogger(AccountBussinesPersonImpl.class);

        @Autowired
        private AccountRepository accountRepository;
        @Autowired
        private AccountTypeRepository accountTypeRepository;

        @Override
        public Mono<EntidadDTO<AccountBussinesRequest>> save(AccountBussinesRequest entidad) {
                log.info("Entro al metodo");

                return Mono.just(entidad)
                                .map(retorno -> new EntidadDTO<AccountBussinesRequest>(true, "Cuenta creada", entidad))
                                .flatMap(obj -> {

                                        log.info("Inicia proceso");

                                        if (entidad.getAccountType().getId()
                                                        .equals(AccountTypeEnum.ACCOUNT_SAVE.toString())) {
                                                return Mono.just(
                                                                new EntidadDTO<AccountBussinesRequest>(
                                                                                false,
                                                                                "Las personas empresariales no pueden crear cuentas de ahorro.",
                                                                                null));

                                        } else if (entidad.getAccountType().getId()
                                                        .equals(AccountTypeEnum.ACCOUNT_FIXED.toString())) {
                                                return Mono.just(
                                                                new EntidadDTO<AccountBussinesRequest>(
                                                                                false,
                                                                                "Las personas empresariales no pueden crear cuentas de plazo fijo.",
                                                                                null));

                                        } else {

                                                log.info("Paso validacion");

                                                String accountRandom = UUID.randomUUID()
                                                                .toString().replace("-", "");

                                                Account account = new Account(null,
                                                                accountRandom,
                                                                entidad.getBalance(),
                                                                entidad.getAccountType()
                                                                                .getId(),
                                                                entidad.getState().getId(),
                                                                new ArrayList<String>(entidad.getIdClients()),
                                                                entidad.getAccountBusiness(),
                                                                new ArrayList<Operation>(),
                                                                entidad.getAccountType(),
                                                                entidad.getState(),
                                                                GenericFunction.generateCard());

                                                obj.getEntidad().setAccountNumber(
                                                                accountRandom);

                                                return accountRepository
                                                                .insert(account)
                                                                .flatMap(nuevo -> {

                                                                        return Mono.just(
                                                                                        new EntidadDTO<AccountBussinesRequest>(
                                                                                                        true,
                                                                                                        "Cuenta creada",
                                                                                                        new AccountBussinesRequest(
                                                                                                                        nuevo.getAccountNumber(),
                                                                                                                        nuevo.getBalance(),
                                                                                                                        entidad.getIdClients(),
                                                                                                                        entidad.getAccountType(),
                                                                                                                        entidad.getState(),
                                                                                                                        entidad.getAccountBusiness(),
                                                                                                                        entidad.getOperations())));
                                                                });
                                        }
                                });
        }

        @Override
        public Flux<AccountBusinessPersonListResponse> findByIdClient(String id) {
                return accountRepository.findAll()
                                .filter(p -> p.getIdClients().contains(id))
                                .map(mapper -> new AccountBusinessPersonListResponse(mapper.getId(),
                                                mapper.getAccountNumber(),
                                                mapper.getBalance(),
                                                id,
                                                mapper.getAccountType().getDescription(),
                                                mapper.getState().getDescription(),
                                                mapper.getAccountBusiness()));
        }

        @Override
        public Mono<EntidadDTO<OperationPersonBussinesAccountRequest>> regOperation(
                        OperationPersonBussinesAccountRequest entidad) {
                log.info("Entro al metodo Reg. Operation");

                return Mono.just(entidad)
                                .map(retorno -> new EntidadDTO<OperationPersonBussinesAccountRequest>(true,
                                                "Operacion con exito",
                                                retorno))
                                .flatMap(obj -> {
                                        log.info("Inicia validacion 1");

                                        return accountRepository.findById(entidad.getIdAccount())
                                                        .flatMap(p -> {
                                                                log.info("Inicia validacion 2");
                                                                return accountTypeRepository
                                                                                .findById(p.getIdAccountType())
                                                                                .flatMap(at -> {
                                                                                        log.info("Inicia validacion 3");

                                                                                        if (entidad.getOperationType()
                                                                                                        .getId()
                                                                                                        .equals("DEP")) {

                                                                                                log.info("Inicia validacion 4");
                                                                                                if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_CURRENT
                                                                                                                                .toString())) {

                                                                                                        OperationsPersonBussines operationsPersonBussines = new OperationsPersonBussinesCurrent();
                                                                                                        var accountDto = operationsPersonBussines
                                                                                                                        .Dep(entidad, p, at);

                                                                                                        if (accountDto.isResult()) {

                                                                                                                accountDto.getEntidad()
                                                                                                                                .setBalance(accountDto
                                                                                                                                                .getEntidad()
                                                                                                                                                .getBalance()
                                                                                                                                                + entidad.getValue());
                                                                                                                return accountRepository
                                                                                                                                .save(accountDto.getEntidad())
                                                                                                                                .flatMap(na -> {
                                                                                                                                        return Mono.just(
                                                                                                                                                        new EntidadDTO<OperationPersonBussinesAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });
                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonBussinesAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                }

                                                                                        } else if (entidad
                                                                                                        .getOperationType()
                                                                                                        .getId()
                                                                                                        .equals("WIT")) {

                                                                                                log.info("Inicia validacion 4");
                                                                                                if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_CURRENT
                                                                                                                                .toString())) {

                                                                                                        OperationsPersonBussines operationsPersonBussines = new OperationsPersonBussinesCurrent();
                                                                                                        var accountDto = operationsPersonBussines
                                                                                                                        .Wit(entidad, p, at);

                                                                                                        if (accountDto.isResult()) {

                                                                                                                accountDto.getEntidad()
                                                                                                                                .setBalance(accountDto
                                                                                                                                                .getEntidad()
                                                                                                                                                .getBalance()
                                                                                                                                                + entidad.getValue());
                                                                                                                return accountRepository
                                                                                                                                .save(accountDto.getEntidad())
                                                                                                                                .flatMap(na -> {
                                                                                                                                        return Mono.just(
                                                                                                                                                        new EntidadDTO<OperationPersonBussinesAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });
                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonBussinesAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                }

                                                                                        } else if (entidad
                                                                                                        .getOperationType()
                                                                                                        .getId()
                                                                                                        .equals("PAY")) {

                                                                                                log.info("Inicia validacion 4");
                                                                                                if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_CURRENT
                                                                                                                                .toString())) {

                                                                                                        OperationsPersonBussines operationsPersonBussines = new OperationsPersonBussinesCurrent();
                                                                                                        var accountDto = operationsPersonBussines
                                                                                                                        .Pay(entidad, p, at);

                                                                                                        if (accountDto.isResult()) {

                                                                                                                accountDto.getEntidad()
                                                                                                                                .setBalance(accountDto
                                                                                                                                                .getEntidad()
                                                                                                                                                .getBalance()
                                                                                                                                                + entidad.getValue());
                                                                                                                return accountRepository
                                                                                                                                .save(accountDto.getEntidad())
                                                                                                                                .flatMap(na -> {
                                                                                                                                        return Mono.just(
                                                                                                                                                        new EntidadDTO<OperationPersonBussinesAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });
                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonBussinesAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                }

                                                                                        }

                                                                                        return Mono.just(
                                                                                                        new EntidadDTO<OperationPersonBussinesAccountRequest>(
                                                                                                                        false,
                                                                                                                        "No se permite la operacion",
                                                                                                                        null));
                                                                                });

                                                        });
                                });

        }

        @Override
        public Mono<AccountBussinesPersonOperationListResponse> getOperationByIdAccount(String id) {
                log.info("Entro a listar");
                return accountRepository.findById(id)
                                .doOnNext(p -> log.info(p.toString()))
                                .map(mapper -> new AccountBussinesPersonOperationListResponse(
                                                mapper.getAccountNumber(),
                                                mapper.getBalance(),
                                                mapper.getIdClients(),
                                                mapper.getAccountType(),
                                                mapper.getState(),
                                                mapper.getOperations()));
        }

}
