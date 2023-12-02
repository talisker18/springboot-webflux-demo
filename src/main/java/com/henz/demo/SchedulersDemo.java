package com.henz.demo;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SchedulersDemo {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Flux.interval(Duration.ofNanos(1))
			.onBackpressureBuffer()
			.doOnNext(i -> System.out.println("before: " + i + ", " + Thread.currentThread().getName()))
			.publishOn(Schedulers.parallel())
			.doOnNext(i -> System.out.println("after: " + i + ", " + Thread.currentThread().getName()))
			.subscribe(i -> System.out.println("consumed: " + i + " on " + Thread.currentThread().getName()));
			
			
		Thread.sleep(10000);
		

	}

}
