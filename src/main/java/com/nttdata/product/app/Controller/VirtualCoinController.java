package com.nttdata.product.app.Controller;

import com.nttdata.product.app.document.VitualCoin;
import com.nttdata.product.app.dto.ConfirmTransaccionCoinRequest;
import com.nttdata.product.app.dto.TransaccionCoinRequest;
import com.nttdata.product.app.service.VirtualCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/virtualCoin")
public class VirtualCoinController {
    @Autowired
    private VirtualCoinService virtualCoinService;


    @GetMapping("/{id}")
    @Cacheable(value = "virtualcoin", key="#id")
    public Mono<ResponseEntity<VitualCoin>> findById(@PathVariable("id") String id){
        return virtualCoinService.findById(id).map(v -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(v))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public Mono<ResponseEntity<VitualCoin>> add(VitualCoin vitualCoin){
        return virtualCoinService.save(vitualCoin).map(v -> ResponseEntity.created(URI.create("/api/virtualCoin".concat(v.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(v));
    }

    @PutMapping("/{id}")
    @CachePut(value = "virtualcoin",key = "#id")
    public Mono<ResponseEntity<VitualCoin>> update(@RequestBody VitualCoin vitualCoin,@PathVariable(value = "id") String id){
        return virtualCoinService.findById(id).flatMap(v ->{
            v.setTotal(vitualCoin.getTotal());
            return virtualCoinService.save(v);
        }).map(v -> ResponseEntity.created(URI.create("/api/virtualCoin".concat(v.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(v))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("virtualcoin/{id}")
    @CacheEvict(value = "virtualcoin", allEntries = true)
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
        return virtualCoinService.findById(id).flatMap(v ->{
            return virtualCoinService.delete(v).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/buy")
    public Mono<ResponseEntity<Map<String, Object>>> buy(@RequestBody TransaccionCoinRequest entidad){

        Map<String, Object> respuesta = new HashMap<String, Object>();

        return virtualCoinService.buy(entidad)
                .map( p -> {
                    respuesta.put("resultado", p.isResult());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());

                    return ResponseEntity
                            .created(URI.create("/api/virtualCoin/buy"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(respuesta);
                });

    }

    @PostMapping("/confirmBuy")
    public Mono<ResponseEntity<Map<String, Object>>> confirmBuy(@RequestBody ConfirmTransaccionCoinRequest entidad){

        Map<String, Object> respuesta = new HashMap<String, Object>();

        return virtualCoinService.confirm(entidad)
                .map( p -> {
                    respuesta.put("resultado", p.isResult());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());

                    return ResponseEntity
                            .created(URI.create("/api/virtualCoin/confirmBuy"))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(respuesta);
                });

    }

}
