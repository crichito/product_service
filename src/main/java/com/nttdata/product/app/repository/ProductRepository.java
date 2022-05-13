package com.nttdata.product.app.repository;

import com.nttdata.product.app.document.Product;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product,String> {
    
}
