package com.nttdata.product.app.service;
import com.nttdata.product.app.document.VitualCoin;
import com.nttdata.product.app.dto.*;
import reactor.core.publisher.Mono;

public interface VirtualCoinService extends RepositoryCrud<VitualCoin>{

    public Mono<EntidadDTO<TransaccionCoinRequest>> buy(TransaccionCoinRequest entidad);

    public Mono<EntidadDTO<ConfirmTransaccionCoinRequest>> confirm(ConfirmTransaccionCoinRequest entidad);
}
