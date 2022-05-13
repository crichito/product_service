package com.nttdata.product.app.ImplService;

import com.nttdata.product.app.document.*;
import com.nttdata.product.app.dto.*;
import com.nttdata.product.app.repository.VirtualCoinRepository;
import com.nttdata.product.app.service.VirtualCoinService;
import com.nttdata.product.app.util.GenericFunction;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class VirtualCoinServiceImpl implements VirtualCoinService {
    @Autowired
    private VirtualCoinRepository virtualCoinRepository;

    @Override
    public Mono<VitualCoin> findById(String id) {
        return virtualCoinRepository.findById(id);
    }

    @Override
    public Mono<VitualCoin> save(VitualCoin document) {
        return virtualCoinRepository.save(document);
    }

    @Override
    public Mono<Void> delete(VitualCoin document) {
        return virtualCoinRepository.delete(document);
    }

    @Override
    public Flux<VitualCoin> findAll() {

        return virtualCoinRepository.findAll();
    }

    @Override
    public Mono<EntidadDTO<TransaccionCoinRequest>> buy(TransaccionCoinRequest entidad) {

        return Mono.just(entidad)
                .map(retorno -> new EntidadDTO<TransaccionCoinRequest>(true,
                        "Operacion con exito",
                        retorno))
                .flatMap(obj -> {

                    return virtualCoinRepository.findById(entidad.getIdVirualCoint())
                            .flatMap(p -> {

                                return virtualCoinRepository.findById(entidad.getIdVirtualCointSeller())
                                        .flatMap(seller -> {

                                            if (seller.getTotal() < entidad.getTotal())
                                                return Mono.just(new EntidadDTO<TransaccionCoinRequest>(false,"Supera el valor permitido por el vendedor",null));

                                            String operation = GenericFunction.getNumericString(10);

                                            p.getOperationCoins()
                                                    .orElse(new ArrayList<OperationCoin>())
                                                    .add(new OperationCoin(
                                                            operation,
                                                            entidad.getTotal(),
                                                            entidad.getIdVirtualCointSeller(),
                                                            new Date(),
                                                            false,
                                                            entidad.getExchange(),
                                                            new OperationType("BUY","COMPRA"),
                                                            entidad.getAccountTransacction(),
                                                            entidad.getPhoneTransaccion()
                                                     ));

                                            virtualCoinRepository.save(p);

                                            seller.getOperationCoins()
                                                    .orElse(new ArrayList<OperationCoin>())
                                                    .add(new OperationCoin(
                                                            operation,
                                                            entidad.getTotal(),
                                                            entidad.getIdVirualCoint(),
                                                            new Date(),
                                                            false,
                                                            entidad.getExchange(),
                                                            new OperationType("SEL","VENTA"),
                                                            entidad.getAccountTransacction(),
                                                            entidad.getPhoneTransaccion()
                                                    ));

                                            virtualCoinRepository.save(seller);

                                            return Mono.just(new EntidadDTO<TransaccionCoinRequest>(true,"Transaccion por confirmar por vendedor",null));
                                        });
                            });

                });
    }

    @Override
    public Mono<EntidadDTO<ConfirmTransaccionCoinRequest>> confirm(ConfirmTransaccionCoinRequest entidad) {
        return Mono.just(entidad)
                .map(retorno -> new EntidadDTO<ConfirmTransaccionCoinRequest>(true,
                        "Operacion con exito",
                        retorno))
                .flatMap(obj -> {

                    return virtualCoinRepository.findById(entidad.getIdVirualCoint())
                            .flatMap(seller -> {

                                var operationFilter = seller.getOperationCoins()
                                        .orElse(new ArrayList<OperationCoin>())
                                        .stream()
                                        .filter(filter -> filter.getOperation().equals(entidad.getOperation()));

                                if (operationFilter.count() == 0)
                                    return Mono.just(new EntidadDTO<ConfirmTransaccionCoinRequest>(false,"No se encontro la operacion.",null));

                                if (seller.getTotal() < operationFilter.findFirst().get().getTotal())
                                    return Mono.just(new EntidadDTO<ConfirmTransaccionCoinRequest>(false, "La operación no se puede permitir por no tener la cantidad solicitada de coins.", null));

                                return virtualCoinRepository.findById(operationFilter.findFirst().get().getIdVirtualCoinDetiny())
                                        .flatMap(destiny -> {

                                            var item = destiny.getOperationCoins()
                                                    .orElse(new ArrayList<OperationCoin>())
                                                    .stream()
                                                    .filter(filter -> filter.getOperation().equals(entidad.getOperation()));

                                            if (item.count() == 0)
                                                return Mono.just(new EntidadDTO<ConfirmTransaccionCoinRequest>(false,"No se encontro la operacion.",null));

                                            destiny.setTotal(destiny.getTotal() + item.findFirst().get().getTotal());

                                            destiny.getOperationCoins()
                                                    .orElse(new ArrayList<OperationCoin>())
                                                    .forEach(confirm -> {
                                                        if(confirm.getOperation().equals(entidad.getOperation()))
                                                            confirm.setConfirm(true);
                                                    });

                                            virtualCoinRepository.save(destiny);

                                            seller.setTotal(seller.getTotal() - item.findFirst().get().getTotal());
                                            seller.getOperationCoins()
                                                    .orElse(new ArrayList<OperationCoin>())
                                                    .forEach(confirm -> {
                                                        if(confirm.getOperation().equals(entidad.getOperation()))
                                                            confirm.setConfirm(true);
                                                    });

                                            virtualCoinRepository.save(seller);

                                            return Mono.just(new EntidadDTO<ConfirmTransaccionCoinRequest>(true, "Operacion confirmada.", null));
                                        });
                            });

                });
    }
}
