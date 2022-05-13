package com.nttdata.product.app.repository;

import com.nttdata.product.app.document.AccountType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountTypeRepository extends ReactiveMongoRepository<AccountType,String>  {
    
}
