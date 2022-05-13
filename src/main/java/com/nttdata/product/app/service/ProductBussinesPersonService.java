package com.nttdata.product.app.service;

import com.nttdata.product.app.dto.CreditCardBussinesPersonOperationListResponse;
import com.nttdata.product.app.dto.CreditNaturalBussinesOperationListResponse;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonBussinesCreditCardRequest;
import com.nttdata.product.app.dto.OperationPersonBussinesCreditRequest;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditCardListResponse;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditCardRegRequest;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditListResponse;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditRegRequest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductBussinesPersonService {
    
    public Mono<EntidadDTO<ProductBussinesPersonCreditRegRequest>> saveCredit(ProductBussinesPersonCreditRegRequest entidad);

    public Flux<ProductBussinesPersonCreditListResponse> getCredit(String idClient);

    public Mono<EntidadDTO<ProductBussinesPersonCreditCardRegRequest>> saveCreditCard(ProductBussinesPersonCreditCardRegRequest entidad);

    public Flux<ProductBussinesPersonCreditCardListResponse> getCreditCard(String idClient);

    public Mono<EntidadDTO<OperationPersonBussinesCreditRequest>> regOperationCredit(OperationPersonBussinesCreditRequest entidad);

    public Mono<CreditNaturalBussinesOperationListResponse> getOperationCreditByIdProduct(String id);

    public Mono<EntidadDTO<OperationPersonBussinesCreditCardRequest>> regOperationCreditCard(OperationPersonBussinesCreditCardRequest entidad);

    public Mono<CreditCardBussinesPersonOperationListResponse> getOperationCreditByIdProductCard(String id);
}
