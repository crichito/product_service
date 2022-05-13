package com.nttdata.product.app.ImplService;

import com.nttdata.product.app.document.State;
import com.nttdata.product.app.repository.StateRepository;
import com.nttdata.product.app.service.StateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StateImpl  implements StateService {

    @Autowired
    StateRepository stateRepository;

    @Override
    public Mono<State> findById(String id) {
        // TODO Auto-generated method stub
        return stateRepository.findById(id);
    }

    @Override
    public Mono<State> save(State document) {
        // TODO Auto-generated method stub
        return stateRepository.save(document);
    }

    @Override
    public Mono<Void> delete(State document) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Flux<State> findAll() {
        // TODO Auto-generated method stub
        return stateRepository.findAll();
    }
    
}
