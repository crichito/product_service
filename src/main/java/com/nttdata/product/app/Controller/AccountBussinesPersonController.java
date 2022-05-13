package com.nttdata.product.app.Controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nttdata.product.app.dto.AccountBusinessPersonListResponse;
import com.nttdata.product.app.dto.AccountBussinesPersonOperationListResponse;
import com.nttdata.product.app.dto.AccountBussinesRequest;
import com.nttdata.product.app.dto.OperationPersonBussinesAccountRequest;
import com.nttdata.product.app.service.AccountBusinessPersonService;

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
@RequestMapping("/api/accountBussinesPerson")
public class AccountBussinesPersonController {

    @Autowired
    AccountBusinessPersonService accountBusinessPersonService;

    @GetMapping("/account/{id}")
	@Cacheable(value = "account", key="#id")
	public Mono<ResponseEntity<Flux<AccountBusinessPersonListResponse>>> getAccounSaveByIdClient(@PathVariable(value = "id") String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(accountBusinessPersonService.findByIdClient(id))
				);
	}

    @PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> createAccountBussinesPerson(@RequestBody AccountBussinesRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return accountBusinessPersonService.save(entidad)
                .map( p -> {
					
                    respuesta.put("resultado", p.isResult());//p.getEntidad().getAccountNumber());
                    respuesta.put("mensaje", p.getMessage());
					
					if(p.isResult()) {
						respuesta.put("cuenta", p.getEntidad().getAccountNumber());
					}

                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					//.created(URI.create("/api/productos/".concat(p.getEntidad().getAccountNumber())))
					.created(URI.create("/api/accountBussinesPerson"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@PostMapping("/operation")
	public Mono<ResponseEntity<Map<String, Object>>> regOperationNaturalPerson(@RequestBody OperationPersonBussinesAccountRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return accountBusinessPersonService.regOperation(entidad)
                .map( p -> {
                    respuesta.put("resultado", p.isResult());//p.getEntidad().getAccountNumber());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					//.created(URI.create("/api/productos/".concat(p.getEntidad().getAccountNumber())))
					.created(URI.create("/api/accountBussinesPerson"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@GetMapping("/operations/{id}")
	@Cacheable(value = "operations", key="#id")
	public Mono<ResponseEntity<Mono<AccountBussinesPersonOperationListResponse>>> getOperationByIdAccount(@PathVariable(value = "id") String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(accountBusinessPersonService.getOperationByIdAccount(id))
				);
	}
    
}
