package com.nttdata.product.app;

import com.nttdata.product.app.document.ProductType;
import com.nttdata.product.app.document.State;

import java.time.LocalDate;
import java.util.Date;

import com.nttdata.product.app.document.AccountType;
import com.nttdata.product.app.document.MovementsConfig;
import com.nttdata.product.app.service.AccountTypeService;
import com.nttdata.product.app.service.ProductTypeService;
import com.nttdata.product.app.service.StateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;


@SpringBootApplication
public class AppApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(AppApplication.class);

	@Autowired
	ProductTypeService productTypeService;
	@Autowired
	AccountTypeService accountTypeService;
	@Autowired
	ReactiveMongoTemplate reactiveMongoTemplate;
	@Autowired
	StateService stateService;

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		reactiveMongoTemplate.dropCollection("product_type").subscribe();
		reactiveMongoTemplate.dropCollection("account_type").subscribe();
		reactiveMongoTemplate.dropCollection("state").subscribe();

		ProductType productTypePerson = new ProductType(null, "Personal");
		ProductType productTypeBusiness = new ProductType(null, "Empresarial");
		ProductType productTypeCreditCardPerson = new ProductType(null, "Tarjeta de Credito Personal");
		ProductType productTypeCreditCardBusiness = new ProductType(null, "Tarjeta de Credito Empresarial");

		Flux.just(productTypePerson, productTypeBusiness, productTypeCreditCardPerson, productTypeCreditCardBusiness)
				.flatMap(c -> productTypeService.save(c))
				.doOnNext(c -> log.info("Product Type added :" + c.toString()))
				.subscribe();

		AccountType accountType1 = new AccountType(
				null,
				"Cuenta Ahorros",
				0.0,
				5,
				5,
				null);

		AccountType accountType2 = new AccountType(
				null,
				"Cuenta Corriente",
				10.0,
				-1,
				-1,
				null);

		AccountType accountType3 = new AccountType(
				null,
				"Plazo Fijo",
				0.0,
				1,
				1,
				new MovementsConfig((new Date(2014, 02, 15)), (new Date(2014, 02, 25))));

		Flux.just(accountType1, accountType2, accountType3)
				.flatMap(c -> accountTypeService.save(c))
				.doOnNext(c -> log.info("Account Type added :" + c.toString()))
				.subscribe();

		State state1 = new State(null, "Activa");
		State state2 = new State(null, "Suspendida");
		State state3 = new State(null, "Confiscada");

		Flux.just(state1, state2, state3)
				.flatMap(c -> stateService.save(c))
				.doOnNext(c -> log.info("State added :" + c.toString()))
				.subscribe();

	}

}
