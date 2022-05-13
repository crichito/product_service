package com.nttdata.product.app.repository;

import com.nttdata.product.app.document.Account;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountRepository extends ReactiveMongoRepository<Account,String> {
    
}
