package com.nttdata.product.app.Controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nttdata.product.app.dto.AccountNaturalPersonListResponse;
import com.nttdata.product.app.dto.AccountNaturalPersonOperationListResponse;
import com.nttdata.product.app.dto.AccountSaveRegRequest;
import com.nttdata.product.app.dto.OperationPersonNaturalAccountRequest;
import com.nttdata.product.app.service.AccountNaturalPersonService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountNaturalPersonService accountNaturalPersonService;

    @GetMapping("/account/{id}")
	@Cacheable(value = "account", key="#id")
	public Mono<ResponseEntity<Flux<AccountNaturalPersonListResponse>>> getAccounSaveByIdClient(@PathVariable(value = "id") String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(accountNaturalPersonService.findByIdClient(id))
				);
	}

    @PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> createAccountNaturalPerson(@RequestBody AccountSaveRegRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return accountNaturalPersonService.save(entidad)
                .map( p -> {
					
                    respuesta.put("resultado", p.isResult());//p.getEntidad().getAccountNumber());
                    respuesta.put("mensaje", p.getMessage());
					
					if(p.isResult()) {
						respuesta.put("cuenta", p.getEntidad().getAccountNumber());
					}

                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					//.created(URI.create("/api/productos/".concat(p.getEntidad().getAccountNumber())))
					.created(URI.create("/api/account"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@PostMapping("/operation")
	public Mono<ResponseEntity<Map<String, Object>>> regOperationNaturalPerson(@RequestBody OperationPersonNaturalAccountRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return accountNaturalPersonService.regOperation(entidad)
                .map( p -> {
                    respuesta.put("resultado", p.isResult());//p.getEntidad().getAccountNumber());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					//.created(URI.create("/api/productos/".concat(p.getEntidad().getAccountNumber())))
					.created(URI.create("/api/account"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@GetMapping("/operations/{id}")
	@Cacheable(value = "operations", key="#id")
	public Mono<ResponseEntity<Mono<AccountNaturalPersonOperationListResponse>>> getOperationByIdAccount(@PathVariable(value = "id") String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(accountNaturalPersonService.getOperationByIdAccount(id))
				);
	}
    
}
