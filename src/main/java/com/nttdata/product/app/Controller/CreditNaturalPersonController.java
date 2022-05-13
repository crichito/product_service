package com.nttdata.product.app.Controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nttdata.product.app.dto.CreditCardNaturalPersonOperationListResponse;
import com.nttdata.product.app.dto.CreditNaturalPersonOperationListResponse;
import com.nttdata.product.app.dto.OperationPersonNaturalCreditCardRequest;
import com.nttdata.product.app.dto.OperationPersonNaturalCreditRequest;
import com.nttdata.product.app.dto.ProductNaturalPersonCreditListResponse;
import com.nttdata.product.app.dto.ProductNaturalPersonalCreditCardRegRequest;
import com.nttdata.product.app.dto.ProductNaturalPersonalCreditRegRequest;
import com.nttdata.product.app.service.ProductNaturalPersonService;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/creditNaturalPerson")
public class CreditNaturalPersonController {
    
    @Autowired
    ProductNaturalPersonService productNaturalPersonService;

    @GetMapping("/credit/{id}")
	public Mono<ResponseEntity<Flux<ProductNaturalPersonCreditListResponse>>> getCreditsByIdClient(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productNaturalPersonService.getCredit(id))
				);
	}

    @PostMapping("/credit")
	public Mono<ResponseEntity<Map<String, Object>>> createCredit(@RequestBody ProductNaturalPersonalCreditRegRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productNaturalPersonService.saveCredit(entidad)
                .map( p -> {
					
                    respuesta.put("resultado", p.isResult());
                    respuesta.put("mensaje", p.getMessage());
                    respuesta.put("timestamp", new Date());
					
                    return ResponseEntity
					//.created(URI.create("/api/productos/".concat(p.getEntidad().getAccountNumber())))
					.created(URI.create("/api/creditNaturalPerson"))
					.contentType(MediaType.APPLICATION_JSON)
					.body(respuesta);
                });

	}

	@PostMapping("/operation/credit")
	public Mono<ResponseEntity<Map<String, Object>>> regOperationNaturalPerson(@RequestBody OperationPersonNaturalCreditRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productNaturalPersonService.regOperationCredit(entidad)
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
	public Mono<ResponseEntity<Mono<CreditNaturalPersonOperationListResponse>>> getOperationByIdAccount(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productNaturalPersonService.getOperationCreditByIdProduct(id))
				);
	}

    @GetMapping("/creditCard/{id}")
	public Mono<ResponseEntity<Flux<ProductNaturalPersonCreditListResponse>>> getCreditsCardByIdClient(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productNaturalPersonService.getCredit(id))
				);
	}

    @PostMapping("/creditCard")
	public Mono<ResponseEntity<Map<String, Object>>> createCreditCard(@RequestBody ProductNaturalPersonalCreditCardRegRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productNaturalPersonService.saveCreditCard(entidad)
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
	public Mono<ResponseEntity<Map<String, Object>>> regOperationCreditCardNaturalPerson(@RequestBody OperationPersonNaturalCreditCardRequest entidad){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
        return productNaturalPersonService.regOperationCreditCard(entidad)
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
	public Mono<ResponseEntity<Mono<CreditCardNaturalPersonOperationListResponse>>> getOperationCreditCardByIdAccount(@PathVariable String id){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(productNaturalPersonService.getOperationCreditByIdProductCard(id))
				);
	}
}
