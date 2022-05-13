package com.nttdata.product.app.repository;

import com.nttdata.product.app.document.ProductType;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductTypeRepository extends ReactiveMongoRepository<ProductType,String>  {
    
}
