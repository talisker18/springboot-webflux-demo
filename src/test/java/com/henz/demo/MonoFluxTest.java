package com.henz.demo;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {
	
	@Test
	public void testMono() {
		//publisher. when subscriber calls subscribe(), onSubscribe() method gets triggered on publisher and subscriber makes his request
		//then, publisher starts sending data by calling onNext(). subscriber only makes 1 request, so onNext on publisher is called only 1 time
		//if publisher has no more data to send, onComplete is triggered on publisher (or onError if error occurred)
		Mono<String> monoString = Mono.just("abc").log();
		
		//subscribe to publisher. when subscribe is called, publisher starts emitting the events
		monoString.subscribe(System.out::println);
	}
	
	@Test
	public void testMonoOnError() {
		Mono<?> monoString = Mono.just("abc") //make unknow return type since an error occurs
				.then(Mono.error(new RuntimeException("some error")))
				.log();
		
		monoString.subscribe(System.out::println, (e) -> System.out.println("on subscriber site: " + e.getMessage())); //do error handling on subscriber site
	}
	
	@Test
	public void testFlux() {
		//publisher, multiple data to send = Flux
		//publisher does now 4 onNext calls + 1 more call because of the concatWithValues()
		Flux<String> fluxString = Flux.just("Spring","Springboot","Hibernate","microservice")
				.concatWithValues("AWS")
				.log();
		
		fluxString.subscribe(System.out::println);
	}
	
	@Test
	public void testFluxOnError() {
		Flux<String> fluxString = Flux.just("Spring","Springboot","Hibernate","microservice")
				.concatWithValues("AWS")
				.concatWith(Flux.error(new RuntimeException("some error")))
				.concatWithValues("cloud") //will not be called because of error
				.log();
		
		fluxString.subscribe(System.out::println, (e) -> System.out.println("on subscriber site: " + e.getMessage()));
	}
}
