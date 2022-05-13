package com.nttdata.product.app.service;

import com.nttdata.product.app.dto.AccountBusinessPersonListResponse;
import com.nttdata.product.app.dto.AccountBussinesPersonOperationListResponse;
import com.nttdata.product.app.dto.AccountBussinesRequest;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonBussinesAccountRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountBusinessPersonService {
    public Mono<EntidadDTO<AccountBussinesRequest>> save(AccountBussinesRequest entidad);

    public Flux<AccountBusinessPersonListResponse> findByIdClient(String id);

    public Mono<EntidadDTO<OperationPersonBussinesAccountRequest>> regOperation(OperationPersonBussinesAccountRequest entidad);

    public Mono<AccountBussinesPersonOperationListResponse> getOperationByIdAccount(String id);    
}
