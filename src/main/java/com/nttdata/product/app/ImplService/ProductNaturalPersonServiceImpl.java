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
import com.nttdata.product.app.dto.CreditCardNaturalPersonOperationListResponse;
import com.nttdata.product.app.dto.CreditNaturalPersonOperationListResponse;
import com.nttdata.product.app.dto.EntidadDTO;
import com.nttdata.product.app.dto.OperationPersonNaturalCreditCardRequest;
import com.nttdata.product.app.dto.OperationPersonNaturalCreditRequest;
import com.nttdata.product.app.dto.ProductNaturalPersonCreditCardListResponse;
import com.nttdata.product.app.dto.ProductNaturalPersonCreditListResponse;
import com.nttdata.product.app.dto.ProductNaturalPersonalCreditCardRegRequest;
import com.nttdata.product.app.dto.ProductNaturalPersonalCreditRegRequest;
import com.nttdata.product.app.dto.ProductOperationCreditCardRequest;
import com.nttdata.product.app.repository.ProductRepository;
import com.nttdata.product.app.service.ProductNaturalPersonService;
import com.nttdata.product.app.util.GenericFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductNaturalPersonServiceImpl implements ProductNaturalPersonService {

        private static final Logger log = LoggerFactory.getLogger(ProductNaturalPersonServiceImpl.class);

        @Autowired
        private ProductRepository productRepository;

        @Override
        public Mono<EntidadDTO<ProductNaturalPersonalCreditRegRequest>> saveCredit(
                        ProductNaturalPersonalCreditRegRequest entidad) {

                return Mono.just(entidad)
                                .map(retorno -> new EntidadDTO<ProductNaturalPersonalCreditRegRequest>(true,
                                                "Cuenta creada", retorno))
                                .flatMap(obj -> {

                                        log.info("Inicia proceso de credito");
                                        log.info(entidad.toString());
                                        return productRepository.findAll()
                                                        .filter(product -> {

                                                                return product.getIdClients()
                                                                                .contains(entidad.getIdClient())
                                                                                && product.getIdProductType().equals(
                                                                                                entidad.getProductType()
                                                                                                                .getId());
                                                        })
                                                        .collectList()
                                                        .flatMap(p -> {

                                                                if (p.size() > 0) {
                                                                        log.info("No Paso validacion");
                                                                        return Mono.just(
                                                                                        new EntidadDTO<ProductNaturalPersonalCreditRegRequest>(
                                                                                                        false,
                                                                                                        "Ya tiene un credito registrado",
                                                                                                        null));
                                                                } else {

                                                                        String productRandom = UUID.randomUUID()
                                                                                        .toString().replace("-", "");
                                                                        List<String> idClients = new ArrayList<String>();
                                                                        idClients.add(entidad.getIdClient());

                                                                        Product product = new Product(
                                                                                        null,
                                                                                        productRandom.toString(),
                                                                                        0.0,
                                                                                        entidad.getBalance(),
                                                                                        entidad.getProductType()
                                                                                                        .getId(),
                                                                                        entidad.getProductState()
                                                                                                        .getId(),
                                                                                        entidad.getProductSchedulePays(),
                                                                                        idClients,
                                                                                        new ArrayList<Operation>(),
                                                                                        null,
                                                                                        entidad.getProductType(),
                                                                                        entidad.getProductState());

                                                                        return productRepository
                                                                                        .insert(product)
                                                                                        .flatMap(nuevo -> {

                                                                                                return Mono.just(
                                                                                                                new EntidadDTO<ProductNaturalPersonalCreditRegRequest>(
                                                                                                                                true,
                                                                                                                                "Cuenta creada",
                                                                                                                                new ProductNaturalPersonalCreditRegRequest(
                                                                                                                                                nuevo.getBalance(),
                                                                                                                                                nuevo.getProductSchedulePays(),
                                                                                                                                                entidad.getIdClient(),
                                                                                                                                                entidad.getProductType(),
                                                                                                                                                entidad.getProductState())));

                                                                                        });

                                                                }

                                                        });
                                });
        }

        @Override
        public Flux<ProductNaturalPersonCreditListResponse> getCredit(String idClient) {
                return productRepository.findAll()
                                .filter(p -> p.getIdClients().contains(idClient))
                                .map(mapper -> new ProductNaturalPersonCreditListResponse(
                                                mapper.getId(),
                                                mapper.getProductCode(),
                                                mapper.getBalance(),
                                                mapper.getCreditLimit(),
                                                mapper.getProductSchedulePays(),
                                                idClient,
                                                null,
                                                mapper.getProductType(),
                                                mapper.getProductState()));

        }

        @Override
        public Mono<EntidadDTO<ProductNaturalPersonalCreditCardRegRequest>> saveCreditCard(
                        ProductNaturalPersonalCreditCardRegRequest entidad) {
                log.info("Entro al metodo");

                return Mono.just(entidad)
                                .map(retorno -> new EntidadDTO<ProductNaturalPersonalCreditCardRegRequest>(true,
                                                "Cuenta creada", retorno))
                                .flatMap(obj -> {

                                        log.info("Inicia proceso");
                                        return productRepository.findAll()
                                                        .filter(product -> {

                                                                return product.getIdClients()
                                                                                .contains(entidad.getIdClient())
                                                                                && product.getIdProductType().equals(
                                                                                                entidad.getProductType()
                                                                                                                .getId());
                                                        })
                                                        .collectList()
                                                        .flatMap(p -> {
                                                                if (p.size() > 0) {
                                                                        log.info("No Paso validacion");
                                                                        return Mono.just(
                                                                                        new EntidadDTO<ProductNaturalPersonalCreditCardRegRequest>(
                                                                                                        false,
                                                                                                        "Ya tiene una tarjeta credito registrado",
                                                                                                        null));
                                                                } else {

                                                                        String productRandom = UUID.randomUUID()
                                                                                        .toString().replace("-", "");
                                                                        List<String> idClients = new ArrayList<String>();
                                                                        idClients.add(entidad.getIdClient());

                                                                        Product product = new Product(
                                                                                        null,
                                                                                        productRandom.toString(),
                                                                                        entidad.getCreditLimit(),
                                                                                        entidad.getCreditLimit(),
                                                                                        entidad.getProductType()
                                                                                                        .getId(),
                                                                                        entidad.getProductState()
                                                                                                        .getId(),
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
                                                                                                                new EntidadDTO<ProductNaturalPersonalCreditCardRegRequest>(
                                                                                                                                true,
                                                                                                                                "Cuenta creada",
                                                                                                                                new ProductNaturalPersonalCreditCardRegRequest(
                                                                                                                                                nuevo.getCreditLimit(),
                                                                                                                                                entidad.getIdClient(),
                                                                                                                                                entidad.getProductType(),
                                                                                                                                                entidad.getProductState())));

                                                                                        });

                                                                }

                                                        });
                                });
        }

        @Override
        public Flux<ProductNaturalPersonCreditCardListResponse> getCreditCard(String idClient) {
                return productRepository.findAll()
                                .filter(p -> p.getIdClients().contains(idClient))
                                .map(mapper -> new ProductNaturalPersonCreditCardListResponse(
                                                mapper.getId(),
                                                mapper.getProductCode(),
                                                mapper.getBalance(),
                                                mapper.getCreditLimit(),
                                                idClient,
                                                mapper.getOperations(),
                                                mapper.getProductType(),
                                                mapper.getProductState()));
        }

        @Override
        public Mono<EntidadDTO<OperationPersonNaturalCreditRequest>> regOperationCredit(
                        OperationPersonNaturalCreditRequest entidad) {
                log.info("Entro al metodo Reg. Operation");

                return Mono.just(entidad)
                                .map(retorno -> new EntidadDTO<OperationPersonNaturalCreditRequest>(true,
                                                "Operacion con exito",
                                                retorno))
                                .flatMap(obj -> {

                                        return productRepository.findById(entidad.getIdProduct())
                                                        .flatMap(p -> {

                                                                log.info("Entro al metodo Reg. Operation 2");

                                                                var pay = p.getProductSchedulePays().stream()
                                                                                .sorted(Comparator.comparing(
                                                                                                ProductSchedulePay::getDatePay))
                                                                                .filter(item -> (item.getState().getId()
                                                                                                .equals("D")))
                                                                                .findFirst()
                                                                                .orElse(null);

                                                                log.info("Entro al metodo Reg. Operation 3");
                                                                log.info(pay.toString());
                                                                log.info(entidad.toString());

                                                                if (pay == null)
                                                                        return Mono.just(
                                                                                        new EntidadDTO<OperationPersonNaturalCreditRequest>(
                                                                                                        false,
                                                                                                        "No se encontro deuda",
                                                                                                        null));
                                                                else {
                                                                        log.info("Entro al metodo Reg. Operation 4");

                                                                        if (Double.compare(pay.getValue(), entidad.getValue())  != 0) {
                                                                                return Mono.just(
                                                                                                new EntidadDTO<OperationPersonNaturalCreditRequest>(
                                                                                                                false,
                                                                                                                "El monto no corresponde a la deuda.",
                                                                                                                null));
                                                                        }

                                                                        log.info("Entro al metodo Reg. Operation 5");

                                                                        p.getProductSchedulePays().forEach(item -> {
                                                                                if (item.equals(pay)) {
                                                                                        item.setDatePayed(new Date());
                                                                                        item.setState(new State("P",
                                                                                                        "Pagado"));
                                                                                }
                                                                        });

                                                                        Operation operation = new Operation(
                                                                                        UUID.randomUUID().toString()
                                                                                                        .replace("-", ""),
                                                                                        new OperationType("PAY",
                                                                                                        "Pago"),
                                                                                        entidad.getDescription(),
                                                                                        entidad.getValue(),
                                                                                        0.0,
                                                                                        entidad.getValue(),
                                                                                        new Date(),
                                                                                        new Audit(entidad.getIdClient(),
                                                                                                        new Date(),
                                                                                                        null, null),
                                                                                        entidad.getChannelOperation());

                                                                        p.getOperations().add(operation);

                                                                        productRepository.save(p);

                                                                        return Mono.just(
                                                                                        new EntidadDTO<OperationPersonNaturalCreditRequest>(
                                                                                                        true,
                                                                                                        "Letra Pagada",
                                                                                                        null));
                                                                }

                                                        });

                                });
        }

        @Override
        public Mono<CreditNaturalPersonOperationListResponse> getOperationCreditByIdProduct(String id) {

                return productRepository.findById(id)
                                .doOnNext(p -> log.info(p.toString()))
                                .map(mapper -> new CreditNaturalPersonOperationListResponse(
                                                mapper.getProductCode(),
                                                mapper.getBalance(),
                                                mapper.getIdClients(),
                                                mapper.getProductType(),
                                                mapper.getOperations()));
        }

        @Override
        public Mono<EntidadDTO<OperationPersonNaturalCreditCardRequest>> regOperationCreditCard(
                        OperationPersonNaturalCreditCardRequest entidad) {
                return Mono.just(entidad)
                                .map(retorno -> new EntidadDTO<OperationPersonNaturalCreditCardRequest>(true,
                                                "Operacion con exito",
                                                retorno))
                                .flatMap(obj -> {

                                        return productRepository.findById(entidad.getIdProduct())
                                                        .flatMap(p -> {

                                                                if (entidad.getOperationType().getId().equals("WIT")) {

                                                                        Operation operation = new Operation(
                                                                                        UUID.randomUUID().toString()
                                                                                                        .replace("-", ""),
                                                                                        new OperationType("WIT",
                                                                                                        "Pago Tarjeta"),
                                                                                        entidad.getDescription(),
                                                                                        entidad.getValue(),
                                                                                        0.0,
                                                                                        entidad.getValue(),
                                                                                        new Date(),
                                                                                        new Audit(entidad.getIdClient(),
                                                                                                        new Date(),
                                                                                                        null, null),
                                                                                        entidad.getChannelOperation());

                                                                        p.getOperations().add(operation);

                                                                        p.setBalance(p.getBalance()
                                                                                        + entidad.getValue());

                                                                        productRepository.save(p);

                                                                        return Mono.just(
                                                                                        new EntidadDTO<OperationPersonNaturalCreditCardRequest>(
                                                                                                        true,
                                                                                                        "Letra Pagada",
                                                                                                        null));

                                                                } else if (entidad.getOperationType().getId()
                                                                                .equals("PAY")) {

                                                                        if (p.getBalance() < entidad.getValue()) {
                                                                                return Mono.just(
                                                                                                new EntidadDTO<OperationPersonNaturalCreditCardRequest>(
                                                                                                                false,
                                                                                                                "La operación supera el monto disponible",
                                                                                                                null));

                                                                        } else {
                                                                                Operation operation = new Operation(
                                                                                                UUID.randomUUID()
                                                                                                                .toString()
                                                                                                                .replace("-", ""),
                                                                                                new OperationType("WIT",
                                                                                                                "Pago"),
                                                                                                entidad.getDescription(),
                                                                                                entidad.getValue(),
                                                                                                0.0,
                                                                                                entidad.getValue(),
                                                                                                new Date(),
                                                                                                new Audit(entidad
                                                                                                                .getIdClient(),
                                                                                                                new Date(),
                                                                                                                null,
                                                                                                                null),
                                                                                                entidad.getChannelOperation());

                                                                                p.getOperations().add(operation);

                                                                                p.setBalance(p.getBalance()
                                                                                                - entidad.getValue());

                                                                                productRepository.save(p);

                                                                                return Mono.just(
                                                                                                new EntidadDTO<OperationPersonNaturalCreditCardRequest>(
                                                                                                                true,
                                                                                                                "Operación permitida",
                                                                                                                null));
                                                                        }

                                                                } else {
                                                                        return Mono.just(
                                                                                        new EntidadDTO<OperationPersonNaturalCreditCardRequest>(
                                                                                                        false,
                                                                                                        "Operación no permitida",
                                                                                                        null));
                                                                }
                                                        });

                                });
        }

        @Override
        public Mono<CreditCardNaturalPersonOperationListResponse> getOperationCreditByIdProductCard(String id) {
                return productRepository.findById(id)
                                .doOnNext(p -> log.info(p.toString()))
                                .map(mapper -> new CreditCardNaturalPersonOperationListResponse(
                                                mapper.getProductCode(),
                                                mapper.getBalance(),
                                                mapper.getCreditLimit(),
                                                mapper.getIdClients(),
                                                mapper.getProductType(),
                                                mapper.getOperations()));
        }

        @Override
        public Mono<EntidadDTO<ProductOperationCreditCardRequest>> regOperationCreditCardByCard(
                        ProductOperationCreditCardRequest entidad) {
                return Mono.just(entidad)
                                .map(retorno -> new EntidadDTO<ProductOperationCreditCardRequest>(true,
                                                "Operacion con exito",
                                                retorno))
                                .flatMap(obj -> {

                                        return productRepository.findAll()
                                                        .filter(p -> p.getCard().getCardNumber()
                                                                        .equals(entidad.getCardNumber())
                                                                        && p.getCard().getPin()
                                                                                        .equals(entidad.getPin()))
                                                        .collectList()
                                                        .flatMap(list -> {

                                                                var p = list.get(0);

                                                                if (p.getBalance() < entidad.getValue()) {
                                                                        return Mono.just(
                                                                                        new EntidadDTO<ProductOperationCreditCardRequest>(
                                                                                                        false,
                                                                                                        "La operación supera el monto disponible",
                                                                                                        null));

                                                                } else {
                                                                        Operation operation = new Operation(
                                                                                        UUID.randomUUID()
                                                                                                        .toString()
                                                                                                        .replace("-", ""),
                                                                                        new OperationType("WIT",
                                                                                                        "Pago"),
                                                                                        entidad.getDescription(),
                                                                                        entidad.getValue(),
                                                                                        0.0,
                                                                                        entidad.getValue(),
                                                                                        new Date(),
                                                                                        new Audit("",
                                                                                                        new Date(),
                                                                                                        null,
                                                                                                        null),
                                                                                        entidad.getChannelOperation());

                                                                        p.getOperations().add(operation);

                                                                        p.setBalance(p.getBalance()
                                                                                        - entidad.getValue());

                                                                        productRepository.save(p);

                                                                        return Mono.just(
                                                                                        new EntidadDTO<ProductOperationCreditCardRequest>(
                                                                                                        true,
                                                                                                        "Operación permitida",
                                                                                                        null));
                                                                }

                                                        });

                                });
        }

}
