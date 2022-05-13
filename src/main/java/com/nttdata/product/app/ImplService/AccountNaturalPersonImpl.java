package com.nttdata.product.app.ImplService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.nttdata.product.app.abstractions.OperationsPersonNatural;
import com.nttdata.product.app.abstractions.OperationsPersonNaturalCurrent;
import com.nttdata.product.app.abstractions.OperationsPersonNaturalFixed;
import com.nttdata.product.app.abstractions.OperationsPersonNaturalSave;
import com.nttdata.product.app.document.Account;
import com.nttdata.product.app.document.Operation;
import com.nttdata.product.app.dto.AccountNaturalPersonListResponse;
import com.nttdata.product.app.dto.AccountNaturalPersonOperationListResponse;
import com.nttdata.product.app.dto.AccountSaveRegRequest;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonNaturalAccountRequest;
import com.nttdata.product.app.dto.PersonNaturalReport;
import com.nttdata.product.app.dto.ProductNaturalPersonCreditListResponse;
import com.nttdata.product.app.repository.AccountRepository;
import com.nttdata.product.app.repository.AccountTypeRepository;
import com.nttdata.product.app.repository.ProductRepository;
import com.nttdata.product.app.service.AccountNaturalPersonService;
import com.nttdata.product.app.service.ProductNaturalPersonService;
import com.nttdata.product.app.util.AccountTypeEnum;
import com.nttdata.product.app.util.GenericFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountNaturalPersonImpl implements AccountNaturalPersonService {

        private static final Logger log = LoggerFactory.getLogger(AccountNaturalPersonImpl.class);

        @Autowired
        private AccountRepository accountRepository;
        @Autowired
        private AccountTypeRepository accountTypeRepository;
        @Autowired
        private ProductRepository productRepository;

        @Override
        public Mono<EntidadDTO<AccountSaveRegRequest>> save(AccountSaveRegRequest entidad) {

                log.info("Entro al metodo");

                return Mono.just(entidad)
                                .map(retorno -> new EntidadDTO<AccountSaveRegRequest>(true, "Cuenta creada", retorno))
                                .flatMap(obj -> {

                                        log.info("Inicia proceso");
                                        return accountRepository.findAll()
                                                        .filter(account -> {
                                                                log.info("Inicia Filtro 0 :");
                                                                log.info("Inicia Filtro 1 ->"
                                                                                .concat(entidad.getIdClient()));
                                                                return account.getIdClients()
                                                                                .contains(entidad.getIdClient())
                                                                                && account.getIdAccountType().equals(
                                                                                                entidad.getAccountType()
                                                                                                                .getId());
                                                        })
                                                        .collectList()
                                                        .flatMap(p -> {
                                                                log.info("Inicia validacion ->"
                                                                                .concat(String.valueOf(p.size())));
                                                                // return Mono.just(new
                                                                // EntidadDTO<AccountSaveRegRequest>(false,"Ya tiene una
                                                                // cuenta registrada", null));

                                                                if (p.size() > 0) {
                                                                        log.info("No Paso validacion");
                                                                        return Mono.just(
                                                                                        new EntidadDTO<AccountSaveRegRequest>(
                                                                                                        false,
                                                                                                        "Ya tiene una cuenta registrada",
                                                                                                        null));
                                                                } else {
                                                                        log.info("Paso validacion");

                                                                        String accountRandom = UUID.randomUUID()
                                                                                        .toString().replace("-", "");
                                                                        List<String> idClients = new ArrayList<String>();
                                                                        idClients.add(entidad.getIdClient());

                                                                        Account account = new Account(null,
                                                                                        accountRandom,
                                                                                        entidad.getBalance(),
                                                                                        entidad.getAccountType()
                                                                                                        .getId(),
                                                                                        entidad.getState().getId(),
                                                                                        idClients,
                                                                                        null,
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
                                                                                                                new EntidadDTO<AccountSaveRegRequest>(
                                                                                                                                true,
                                                                                                                                "Cuenta creada",
                                                                                                                                new AccountSaveRegRequest(
                                                                                                                                                nuevo.getId(),
                                                                                                                                                nuevo.getAccountNumber(),
                                                                                                                                                nuevo.getBalance(),
                                                                                                                                                entidad.getIdClient(),
                                                                                                                                                entidad.getAccountType(),
                                                                                                                                                entidad.getState())));
                                                                                        });

                                                                }

                                                        });
                                });

        }

        @Override
        public Flux<AccountNaturalPersonListResponse> findByIdClient(String id) {

                return accountRepository.findAll()
                                .filter(p -> p.getIdClients().contains(id))
                                .map(mapper -> new AccountNaturalPersonListResponse(mapper.getId(),
                                                mapper.getAccountNumber(),
                                                mapper.getBalance(),
                                                id,
                                                mapper.getAccountType().getDescription(),
                                                mapper.getState().getDescription()));
        }

        @Override
        public Mono<EntidadDTO<OperationPersonNaturalAccountRequest>> regOperation(
                        OperationPersonNaturalAccountRequest entidad) {

                log.info("Entro al metodo Reg. Operation");

                return Mono.just(entidad)
                                .map(retorno -> new EntidadDTO<OperationPersonNaturalAccountRequest>(true,
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
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_SAVE
                                                                                                                                .toString())) {

                                                                                                        log.info("Inicia validacion 5");
                                                                                                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalSave();
                                                                                                        var accountDto = operationsPersonNatural
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
                                                                                                                                                        new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });
                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                } else if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_CURRENT
                                                                                                                                .toString())) {

                                                                                                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalCurrent();
                                                                                                        var accountDto = operationsPersonNatural
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
                                                                                                                                                        new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });
                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                } else if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_FIXED
                                                                                                                                .toString())) {

                                                                                                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalFixed();
                                                                                                        var accountDto = operationsPersonNatural
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
                                                                                                                                                        new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });
                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                }

                                                                                        } else if (entidad
                                                                                                        .getOperationType()
                                                                                                        .getId()
                                                                                                        .equals("WIT")) {

                                                                                                if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_SAVE
                                                                                                                                .toString())) {
                                                                                                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalSave();
                                                                                                        var accountDto = operationsPersonNatural
                                                                                                                        .Wit(entidad, p, at);

                                                                                                        if (accountDto.isResult()) {
                                                                                                                accountDto.getEntidad()
                                                                                                                                .setBalance(accountDto
                                                                                                                                                .getEntidad()
                                                                                                                                                .getBalance()
                                                                                                                                                - entidad.getValue());
                                                                                                                return accountRepository
                                                                                                                                .save(accountDto.getEntidad())
                                                                                                                                .flatMap(na -> {
                                                                                                                                        return Mono.just(
                                                                                                                                                        new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });

                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                } else if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_CURRENT
                                                                                                                                .toString())) {
                                                                                                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalCurrent();
                                                                                                        var accountDto = operationsPersonNatural
                                                                                                                        .Wit(entidad, p, at);

                                                                                                        if (accountDto.isResult()) {
                                                                                                                accountDto.getEntidad()
                                                                                                                                .setBalance(accountDto
                                                                                                                                                .getEntidad()
                                                                                                                                                .getBalance()
                                                                                                                                                - entidad.getValue());
                                                                                                                return accountRepository
                                                                                                                                .save(accountDto.getEntidad())
                                                                                                                                .flatMap(na -> {
                                                                                                                                        return Mono.just(
                                                                                                                                                        new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });

                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                } else if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_FIXED
                                                                                                                                .toString())) {
                                                                                                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalFixed();
                                                                                                        var accountDto = operationsPersonNatural
                                                                                                                        .Wit(entidad, p, at);

                                                                                                        if (accountDto.isResult()) {
                                                                                                                accountDto.getEntidad()
                                                                                                                                .setBalance(accountDto
                                                                                                                                                .getEntidad()
                                                                                                                                                .getBalance()
                                                                                                                                                - entidad.getValue());
                                                                                                                return accountRepository
                                                                                                                                .save(accountDto.getEntidad())
                                                                                                                                .flatMap(na -> {
                                                                                                                                        return Mono.just(
                                                                                                                                                        new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });

                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                }
                                                                                        } else if (entidad
                                                                                                        .getOperationType()
                                                                                                        .getId()
                                                                                                        .equals("PAY")) {

                                                                                                if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_SAVE
                                                                                                                                .toString())) {
                                                                                                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalSave();
                                                                                                        var accountDto = operationsPersonNatural
                                                                                                                        .Pay(entidad, p, at);

                                                                                                        if (accountDto.isResult()) {

                                                                                                                accountDto.getEntidad()
                                                                                                                                .setBalance(accountDto
                                                                                                                                                .getEntidad()
                                                                                                                                                .getBalance()
                                                                                                                                                - entidad.getValue());

                                                                                                                return accountRepository
                                                                                                                                .save(accountDto.getEntidad())
                                                                                                                                .flatMap(na -> {
                                                                                                                                        return Mono.just(
                                                                                                                                                        new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });
                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                } else if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_CURRENT
                                                                                                                                .toString())) {
                                                                                                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalCurrent();
                                                                                                        var accountDto = operationsPersonNatural
                                                                                                                        .Pay(entidad, p, at);

                                                                                                        if (accountDto.isResult()) {

                                                                                                                accountDto.getEntidad()
                                                                                                                                .setBalance(accountDto
                                                                                                                                                .getEntidad()
                                                                                                                                                .getBalance()
                                                                                                                                                - entidad.getValue());

                                                                                                                return accountRepository
                                                                                                                                .save(accountDto.getEntidad())
                                                                                                                                .flatMap(na -> {
                                                                                                                                        return Mono.just(
                                                                                                                                                        new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });
                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                } else if (p.getIdAccountType()
                                                                                                                .equals(AccountTypeEnum.ACCOUNT_FIXED
                                                                                                                                .toString())) {
                                                                                                        OperationsPersonNatural operationsPersonNatural = new OperationsPersonNaturalFixed();
                                                                                                        var accountDto = operationsPersonNatural
                                                                                                                        .Pay(entidad, p, at);

                                                                                                        if (accountDto.isResult()) {

                                                                                                                accountDto.getEntidad()
                                                                                                                                .setBalance(accountDto
                                                                                                                                                .getEntidad()
                                                                                                                                                .getBalance()
                                                                                                                                                - entidad.getValue());

                                                                                                                return accountRepository
                                                                                                                                .save(accountDto.getEntidad())
                                                                                                                                .flatMap(na -> {
                                                                                                                                        return Mono.just(
                                                                                                                                                        new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                                        true,
                                                                                                                                                                        "Operación registrada",
                                                                                                                                                                        null));
                                                                                                                                });
                                                                                                        } else
                                                                                                                return Mono.just(
                                                                                                                                new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                                                false,
                                                                                                                                                accountDto.getMessage(),
                                                                                                                                                null));

                                                                                                }
                                                                                        }

                                                                                        return Mono.just(
                                                                                                        new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                                                                        false,
                                                                                                                        "No se permite la operacion",
                                                                                                                        null));
                                                                                });

                                                        })
                                                        .defaultIfEmpty(new EntidadDTO<OperationPersonNaturalAccountRequest>(
                                                                        false,
                                                                        "No se encontro la cuenta",
                                                                        null));
                                });

        }

        @Override
        public Mono<AccountNaturalPersonOperationListResponse> getOperationByIdAccount(String id) {
                log.info("Entro a listar");
                return accountRepository.findById(id)
                                .doOnNext(p -> log.info(p.toString()))
                                .map(mapper -> new AccountNaturalPersonOperationListResponse(
                                                mapper.getAccountNumber(),
                                                mapper.getBalance(),
                                                mapper.getIdClients(),
                                                mapper.getAccountType(),
                                                mapper.getState(),
                                                mapper.getOperations()));
        }

        @Override
        public Mono<EntidadDTO<PersonNaturalReport>> getReport(String id) {
                return accountRepository.findAll()
                                .filter(p -> p.getIdClients().contains(id))
                                .map(mapper -> new AccountNaturalPersonListResponse(mapper.getId(),
                                                mapper.getAccountNumber(),
                                                mapper.getBalance(),
                                                id,
                                                mapper.getAccountType().getDescription(),
                                                mapper.getState().getDescription()))
                                .collectList()
                                .flatMap(accounts -> {
                                        
                                        return productRepository.findAll()
                                        .filter(p1 -> p1.getIdClients().contains(id))
                                        .collectList()
                                        .map(products -> {

                                                return new EntidadDTO<PersonNaturalReport>(true,"Resultado",
                                                new PersonNaturalReport(accounts,products));
                                                
                                        });
                                });
        }

        
}
