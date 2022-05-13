package com.nttdata.product.app.repository;

import com.nttdata.product.app.document.State;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StateRepository extends ReactiveMongoRepository<State,String> {
    
}
