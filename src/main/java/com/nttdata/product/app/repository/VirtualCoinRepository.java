package com.nttdata.product.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.nttdata.product.app.document.VitualCoin;
public interface VirtualCoinRepository extends ReactiveMongoRepository<VitualCoin,String>{
}
