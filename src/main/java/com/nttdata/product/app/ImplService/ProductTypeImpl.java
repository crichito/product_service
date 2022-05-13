package com.nttdata.product.app.ImplService;

import com.nttdata.product.app.document.ProductType;
import com.nttdata.product.app.repository.ProductTypeRepository;
import com.nttdata.product.app.service.ProductTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductTypeImpl implements ProductTypeService {

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Override
    public Mono<ProductType> findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mono<ProductType> save(ProductType document) {
        // TODO Auto-generated method stub
        return productTypeRepository.save(document);
    }

    @Override
    public Mono<Void> delete(ProductType document) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Flux<ProductType> findAll() {
        // TODO Auto-generated method stub
        return productTypeRepository.findAll();
    }
    
}
