package com.nttdata.product.app.service;


import com.nttdata.product.app.dto.AccountNaturalPersonListResponse;
import com.nttdata.product.app.dto.AccountNaturalPersonOperationListResponse;
import com.nttdata.product.app.dto.AccountSaveRegRequest;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonNaturalAccountRequest;
import com.nttdata.product.app.dto.PersonNaturalReport;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountNaturalPersonService {
    public Mono<EntidadDTO<AccountSaveRegRequest>> save(AccountSaveRegRequest entidad);

    public Flux<AccountNaturalPersonListResponse> findByIdClient(String id);

    public Mono<EntidadDTO<OperationPersonNaturalAccountRequest>> regOperation(OperationPersonNaturalAccountRequest entidad);

    public Mono<AccountNaturalPersonOperationListResponse> getOperationByIdAccount(String id);

    public Mono<EntidadDTO<PersonNaturalReport>> getReport(String id);
}
