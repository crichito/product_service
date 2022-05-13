package com.nttdata.product.app.ImplService;

import com.nttdata.product.app.service.AccountTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.nttdata.product.app.document.AccountType;
import com.nttdata.product.app.repository.AccountTypeRepository;

@Service
public class AccountTypeImpl implements AccountTypeService {

    @Autowired
    private AccountTypeRepository accountTypeRepository;
    
    @Override
    public Mono<AccountType> findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<AccountType> save(AccountType document) {
        return accountTypeRepository.save(document);
    }

    @Override
    public Mono<Void> delete(AccountType document) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Flux<AccountType> findAll() {
        // TODO Auto-generated method stub
        return accountTypeRepository.findAll();
    }
    
}
