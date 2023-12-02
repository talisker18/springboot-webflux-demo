package com.henz.demo.dao;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.henz.demo.dto.Customer;

import reactor.core.publisher.Flux;

@Component
public class CustomerDao {
	
	private static void sleepExec(int i) { //param int i is only used to make method reference call
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Customer> getCustomersSynchronousCall() {
		//simulate load that needs about 50 seconds
		//this call is blocking and synchronous, meaning that the browser (in this case the subscriber) has to wait for 50 seconds until he gets a result (with all customers)
		//if the browser aborts the loading, the publisher DOES NOT abort the loading of customers from backend
		return IntStream.rangeClosed(1, 50)
				.peek(CustomerDao::sleepExec)
				.peek(i -> System.out.println("processing count = " + i))
				.mapToObj(i -> new Customer(i, "customer " + i))
				.collect(Collectors.toList());
	}
	
	public Flux<Customer> getCustomersAsynchronousCall() {
		//simulate load that needs about 50 seconds
		//this call is non blocking and asynchronous, meaning that the browser (in this case the subscriber) is able to show each loaded customer live!
		//if the browser aborts the loading, the publisher also aborts the loading of customers from backend
		return Flux.range(1, 50)
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(i -> System.out.println("processing count = " + i))
				.map(i -> new Customer(i, "customer " + i));
	}
}
