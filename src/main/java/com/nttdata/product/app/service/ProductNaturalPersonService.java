package com.nttdata.product.app.service;

import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonNaturalCreditCardRequest;
import com.nttdata.product.app.dto.OperationPersonNaturalCreditRequest;
import com.nttdata.product.app.dto.ProductNaturalPersonCreditCardListResponse;
import com.nttdata.product.app.dto.ProductNaturalPersonCreditListResponse;
import com.nttdata.product.app.dto.CreditCardNaturalPersonOperationListResponse;
import com.nttdata.product.app.dto.CreditNaturalPersonOperationListResponse;
import com.nttdata.product.app.dto.ProductNaturalPersonalCreditCardRegRequest;
import com.nttdata.product.app.dto.ProductNaturalPersonalCreditRegRequest;
import com.nttdata.product.app.dto.ProductOperationCreditCardRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductNaturalPersonService {
    
    public Mono<EntidadDTO<ProductNaturalPersonalCreditRegRequest>> saveCredit(ProductNaturalPersonalCreditRegRequest entidad);

    public Flux<ProductNaturalPersonCreditListResponse> getCredit(String idClient);

    public Mono<EntidadDTO<ProductNaturalPersonalCreditCardRegRequest>> saveCreditCard(ProductNaturalPersonalCreditCardRegRequest entidad);

    public Flux<ProductNaturalPersonCreditCardListResponse> getCreditCard(String idClient);

    public Mono<EntidadDTO<OperationPersonNaturalCreditRequest>> regOperationCredit(OperationPersonNaturalCreditRequest entidad);

    public Mono<CreditNaturalPersonOperationListResponse> getOperationCreditByIdProduct(String id);

    public Mono<EntidadDTO<OperationPersonNaturalCreditCardRequest>> regOperationCreditCard(OperationPersonNaturalCreditCardRequest entidad);

    public Mono<CreditCardNaturalPersonOperationListResponse> getOperationCreditByIdProductCard(String id);

    public Mono<EntidadDTO<ProductOperationCreditCardRequest>> regOperationCreditCardByCard(ProductOperationCreditCardRequest entidad);
}
