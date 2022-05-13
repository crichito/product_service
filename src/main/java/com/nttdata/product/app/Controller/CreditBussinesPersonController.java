package com.nttdata.product.app.Controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nttdata.product.app.dto.CreditCardBussinesPersonOperationListResponse;
import com.nttdata.product.app.dto.CreditNaturalBussinesOperationListResponse;
import com.nttdata.product.app.dto.OperationPersonBussinesCreditCardRequest;
import com.nttdata.product.app.dto.OperationPersonBussinesCreditRequest;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditCardListResponse;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditCardRegRequest;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditListResponse;
import com.nttdata.product.app.dto.ProductBussinesPersonCreditRegRequest;
import com.nttdata.product.app.service.ProductBussinesPersonService;

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
@RequestMapping("/api/creditBussinesPerson")
public class CreditBussinesPersonController {
    
    @Autowired
    ProductBussinesPersonService productBussainesPersonService;

    @GetMapping("/credit/{id}")
	@Cacheable(value = "credit", key="#id")
	public Mono<ResponseEntity<Flux<ProductBussinesPersonCreditListResponse>>> getCreditsByIdClient(@PathVariable(value = "id") String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productBussainesPersonService.getCredit(id))
				);
	}

    @PostMapping("/credit")
	public Mono<ResponseEntity<Map<String, Object>>> createCredit(@RequestBody ProductBussinesPersonCreditRegRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productBussainesPersonService.saveCredit(entidad)
                .map( p -> {
					
                    respuesta.put("resultado", p.isResult());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					//.created(URI.create("/api/productos/".concat(p.getEntidad().getAccountNumber())))
					.created(URI.create("/api/productos/"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@PostMapping("/operation/credit")
	public Mono<ResponseEntity<Map<String, Object>>> regOperationNaturalPerson(@RequestBody OperationPersonBussinesCreditRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productBussainesPersonService.regOperationCredit(entidad)
                .map( p -> {
                    respuesta.put("resultado", p.isResult());//p.getEntidad().getAccountNumber());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					//.created(URI.create("/api/productos/".concat(p.getEntidad().getAccountNumber())))
					.created(URI.create("/api/operation"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@GetMapping("/operations/credit/{id}")
	@Cacheable(value = "operations", key="#id")
	public Mono<ResponseEntity<Mono<CreditNaturalBussinesOperationListResponse>>> getOperationByIdAccount(@PathVariable(value = "id") String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productBussainesPersonService.getOperationCreditByIdProduct(id))
				);
	}

	@GetMapping("/creditCard/{id}")
	@Cacheable(value = "creditCard", key="#id")
	public Mono<ResponseEntity<Flux<ProductBussinesPersonCreditCardListResponse>>> getCreditsCardByIdClient(@PathVariable(value = "id") String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productBussainesPersonService.getCreditCard(id))
				);
	}

    @PostMapping("/creditCard")
	public Mono<ResponseEntity<Map<String, Object>>> createCreditCard(@RequestBody ProductBussinesPersonCreditCardRegRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productBussainesPersonService.saveCreditCard(entidad)
                .map( p -> {
					
                    respuesta.put("resultado", p.isResult());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					//.created(URI.create("/api/productos/".concat(p.getEntidad().getAccountNumber())))
					.created(URI.create("/api/productos/"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@PostMapping("/operation/creditCard")
	public Mono<ResponseEntity<Map<String, Object>>> regOperationCreditCardNaturalPerson(@RequestBody OperationPersonBussinesCreditCardRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productBussainesPersonService.regOperationCreditCard(entidad)
                .map( p -> {
                    respuesta.put("resultado", p.isResult());//p.getEntidad().getAccountNumber());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					//.created(URI.create("/api/productos/".concat(p.getEntidad().getAccountNumber())))
					.created(URI.create("/api/operation"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@GetMapping("/operations/creditCard/{id}")
	@Cacheable(value = "operations", key="#id")
	public Mono<ResponseEntity<Mono<CreditCardBussinesPersonOperationListResponse>>> getOperationCreditCardByIdAccount(@PathVariable(value = "id") String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productBussainesPersonService.getOperationCreditByIdProductCard(id))
				);
	}
}
