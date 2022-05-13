package com.nttdata.product.app.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RepositoryCrud <T> {
    
	public Mono<T> findById(String id);

	public Mono<T> save(T document);

	public Mono<Void> delete(T document);

	public Flux<T> findAll();
}
