package com.nttdata.product.app.ImplService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.nttdata.product.app.document.Audit;
import com.nttdata.product.app.document.Operation;
import com.nttdata.product.app.document.OperationType;
import com.nttdata.product.app.document.Product;
import com.nttdata.product.app.document.ProductSchedulePay;
import com.nttdata.product.app.document.State;
import com.nttdata.product.app.dto.CreditCardBussinesPersonOperationListResponse;
import com.nttdata.product.app.dto.CreditNaturalBussinesOperationListResponse;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonBussinesCreditCardRequest;
import com.nttdata.product.app.dto.OperationPersonBussinesCreditRequest;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditCardListResponse;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditCardRegRequest;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditListResponse;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditRegRequest;
import com.nttdata.product.app.repository.ProductRepository;
import com.nttdata.product.app.service.ProductBussinesPersonService;
import com.nttdata.product.app.util.GenericFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductBussinesPersonServiceImpl implements ProductBussinesPersonService {

    private static final Logger log = LoggerFactory.getLogger(ProductBussinesPersonServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Mono<EntidadDTO<ProductBussinesPersonCreditRegRequest>> saveCredit(
            ProductBussinesPersonCreditRegRequest entidad) {

        

        return Mono.just(entidad)
                .map(retorno -> new EntidadDTO<ProductBussinesPersonCreditRegRequest>(true, "Cuenta creada", retorno))
                .flatMap(obj -> {

                    String productRandom = UUID.randomUUID()
                            .toString().replace("-", "");
                    List<String> idClients = new ArrayList<String>();
                    idClients.add(entidad.getIdClient());

                    Product product = new Product(
                            null,
                            productRandom.toString(),
                            0.0,
                            entidad.getBalance(),
                            entidad.getProductType().getId(),
                            entidad.getProductState().getId(),
                            entidad.getProductSchedulePays(),
                            idClients,
                            null,
                            null,
                            entidad.getProductType(),
                            entidad.getProductState());

                    return productRepository
                            .insert(product)
                            .flatMap(nuevo -> {

                                return Mono.just(
                                        new EntidadDTO<ProductBussinesPersonCreditRegRequest>(
                                                true,
                                                "Cuenta creada",
                                                new ProductBussinesPersonCreditRegRequest(
                                                        nuevo.getBalance(),
                                                        nuevo.getProductSchedulePays(),
                                                        entidad.getIdClient(),
                                                        entidad.getProductType(),
                                                        entidad.getProductState())));

                            });
                });
    }

    @Override
    public Flux<ProductBussinesPersonCreditListResponse> getCredit(String idClient) {
        return productRepository.findAll()
        .filter(p -> p.getIdClients().contains(idClient))
        .map(mapper -> new ProductBussinesPersonCreditListResponse(
                        mapper.getId(),
                        mapper.getProductCode(),
                        mapper.getBalance(),
                        mapper.getCreditLimit(),
                        mapper.getProductSchedulePays(),
                        idClient,
                        null,
                        mapper.getProductType(),
                        mapper.getProductState()
                        )
                    );
    }

    @Override
    public Mono<EntidadDTO<ProductBussinesPersonCreditCardRegRequest>> saveCreditCard(
            ProductBussinesPersonCreditCardRegRequest entidad) {
                log.info("Entro al metodo");

                return Mono.just(entidad)
                        .map(retorno -> new EntidadDTO<ProductBussinesPersonCreditCardRegRequest>(true, "Cuenta creada", retorno))
                        .flatMap(obj -> {
        
                            log.info("Inicia proceso");
                            String productRandom = UUID.randomUUID()
                                                    .toString().replace("-", "");
                                            List<String> idClients = new ArrayList<String>();
                                            idClients.add(entidad.getIdClient());
        
                                            Product product = new Product(
                                                null, 
                                                productRandom.toString(), 
                                                entidad.getCreditLimit(), 
                                                entidad.getCreditLimit(), 
                                                entidad.getProductType().getId(), 
                                                entidad.getProductState().getId(),
                                                null, 
                                                idClients, 
                                                new ArrayList<Operation>(), 
                                                GenericFunction.generateCard(), 
                                                entidad.getProductType(),
                                                entidad.getProductState());
        
            
                                            return productRepository
                                                    .insert(product)
                                                    .flatMap(nuevo -> {
        
                                                        return Mono.just(
                                                                new EntidadDTO<ProductBussinesPersonCreditCardRegRequest>(
                                                                        true,
                                                                        "Cuenta creada",
                                                                        new ProductBussinesPersonCreditCardRegRequest(
                                                                                nuevo.getCreditLimit(),
                                                                                entidad.getIdClient(),
                                                                                entidad.getProductType(),
                                                                                entidad.getProductState())));
        
                                                    });
                        });
    }

    @Override
    public Flux<ProductBussinesPersonCreditCardListResponse> getCreditCard(String idClient) {
        return productRepository.findAll()
        .filter(p -> p.getIdClients().contains(idClient))
        .map(mapper -> new ProductBussinesPersonCreditCardListResponse(
                        mapper.getId(),
                        mapper.getProductCode(),
                        mapper.getBalance(),
                        mapper.getCreditLimit(),
                        idClient,
                        mapper.getOperations(),
                        mapper.getProductType(),
                        mapper.getProductState()
                        )
                    );
    }

@Override
public Mono<EntidadDTO<OperationPersonBussinesCreditRequest>> regOperationCredit(
                OperationPersonBussinesCreditRequest entidad) {
                        log.info("Entro al metodo Reg. Operation");

                        return Mono.just(entidad)
                                        .map(retorno -> new EntidadDTO<OperationPersonBussinesCreditRequest>(true,
                                                        "Operacion con exito",
                                                        retorno))
                                        .flatMap(obj -> {
        
                                                return productRepository.findById(entidad.getIdProduct())
                                                                .flatMap(p -> {
        
                                                                        var pay = p.getProductSchedulePays().stream()
                                                                        .sorted(Comparator.comparing(ProductSchedulePay::getDatePay))
                                                                        .filter(item -> (item.getState().getId().equals("D")))
                                                                        .findFirst()
                                                                        .orElse(null);
                                                                        
                                                                        if(pay == null) 
                                                                             return Mono.just(
                                                                                        new EntidadDTO<OperationPersonBussinesCreditRequest>(
                                                                                                        false,
                                                                                                        "No se encontro deuda",
                                                                                                        null));
                                                                        else {
                                                                                p.getProductSchedulePays().forEach(item -> {
                                                                                        if(pay.equals(pay)){
                                                                                                item.setDatePayed(new Date());
                                                                                                item.setState(new State("P","Pagado"));
                                                                                        }
                                                                                });
        
                                                                                Operation operation = new Operation(
                                                                                UUID.randomUUID().toString().replace("-", ""),
                                                                                new OperationType("PAY", "Pago"),
                                                                                entidad.getDescription(),
                                                                                entidad.getValue(),
                                                                                0.0,
                                                                                entidad.getValue(),
                                                                                new Date(),
                                                                                new Audit(entidad.getIdClient(), new Date(), null, null),
                                                                                entidad.getChannelOperation());
        
                                                                                p.getOperations().add(operation);
        
                                                                                productRepository.save(p);
        
                                                                                return Mono.just(
                                                                                        new EntidadDTO<OperationPersonBussinesCreditRequest>(
                                                                                                        true,
                                                                                                        "Letra Pagada",
                                                                                                        null));
                                                                        }
        
                                                                });
        
                                        });
}

@Override
public Mono<CreditNaturalBussinesOperationListResponse> getOperationCreditByIdProduct(String id) {
        return productRepository.findById(id)
                                .doOnNext(p -> log.info(p.toString()))
                                .map(mapper -> new CreditNaturalBussinesOperationListResponse(
                                                mapper.getProductCode(),
                                                mapper.getBalance(),
                                                mapper.getIdClients(),
                                                mapper.getProductType(),
                                                mapper.getOperations()));
}

@Override
public Mono<EntidadDTO<OperationPersonBussinesCreditCardRequest>> regOperationCreditCard(
                OperationPersonBussinesCreditCardRequest entidad) {
                        return Mono.just(entidad)
                        .map(retorno -> new EntidadDTO<OperationPersonBussinesCreditCardRequest>(true,
                                        "Operacion con exito",
                                        retorno))
                        .flatMap(obj -> {

                                return productRepository.findById(entidad.getIdProduct())
                                                .flatMap(p -> {

                                                        
                                                        if(entidad.getOperationType().getId().equals("WIT")) {

                                                                Operation operation = new Operation(
                                                                UUID.randomUUID().toString().replace("-", ""),
                                                                new OperationType("WIT", "Pago Tarjeta"),
                                                                entidad.getDescription(),
                                                                entidad.getValue(),
                                                                0.0,
                                                                entidad.getValue(),
                                                                new Date(),
                                                                new Audit(entidad.getIdClient(), new Date(), null, null),
                                                                entidad.getChannelOperation());

                                                                p.getOperations().add(operation);

                                                                p.setBalance(p.getBalance() + entidad.getValue());

                                                                productRepository.save(p);

                                                                return Mono.just(
                                                                        new EntidadDTO<OperationPersonBussinesCreditCardRequest>(
                                                                                        true,
                                                                                        "Letra Pagada",
                                                                                        null));


                                                        } else if(entidad.getOperationType().getId().equals("PAY")) {

                                                                if(p.getBalance() <entidad.getValue()) {
                                                                        return Mono.just(
                                                                        new EntidadDTO<OperationPersonBussinesCreditCardRequest>(
                                                                                        false,
                                                                                        "La operación supera el monto disponible",
                                                                                        null));

                                                                } else {
                                                                        Operation operation = new Operation(
                                                                        UUID.randomUUID().toString().replace("-", ""),
                                                                        new OperationType("WIT", "Pago"),
                                                                        entidad.getDescription(),
                                                                        entidad.getValue(),
                                                                        0.0,
                                                                        entidad.getValue(),
                                                                        new Date(),
                                                                        new Audit(entidad.getIdClient(), new Date(), null, null),
                                                                        entidad.getChannelOperation());

                                                                        p.getOperations().add(operation);

                                                                        p.setBalance(p.getBalance() - entidad.getValue());

                                                                        productRepository.save(p);

                                                                        return Mono.just(
                                                                                new EntidadDTO<OperationPersonBussinesCreditCardRequest>(
                                                                                                true,
                                                                                                "Operación permitida",
                                                                                                null));
                                                                }
                                                                
                                                        } else {
                                                                return Mono.just(
                                                                        new EntidadDTO<OperationPersonBussinesCreditCardRequest>(
                                                                                        false,
                                                                                        "Operación no permitida",
                                                                                        null));
                                                        }
                                                });

                        });
}

@Override
public Mono<CreditCardBussinesPersonOperationListResponse> getOperationCreditByIdProductCard(String id) {
        return productRepository.findById(id)
                                .doOnNext(p -> log.info(p.toString()))
                                .map(mapper -> new CreditCardBussinesPersonOperationListResponse(
                                                mapper.getProductCode(),
                                                mapper.getBalance(),
                                                mapper.getCreditLimit(),
                                                mapper.getIdClients(),
                                                mapper.getProductType(),
                                                mapper.getOperations()));
}

}
