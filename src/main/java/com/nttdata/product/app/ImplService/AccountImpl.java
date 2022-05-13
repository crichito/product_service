package com.nttdata.product.app.ImplService;

import com.nttdata.product.app.document.Account;
import com.nttdata.product.app.service.AccountService;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public abstract class AccountImpl implements AccountService {

    @Override
    public Mono<Account> findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public abstract Mono<Account> save(Account document);

    @Override
    public Mono<Void> delete(Account document) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Flux<Account> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
     
}
