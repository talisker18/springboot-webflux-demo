package com.henz.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henz.demo.dto.Customer;
import com.henz.demo.service.CustomerService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@GetMapping("/allSynchronousCall")
	public List<Customer> getAllCustomersSynchronousCall() {
		return this.service.loadAllCustomersSynchronousCall();
	}
	
	@GetMapping(value = "/allStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE) //needed to activate the stream
	public Flux<Customer> getAllCustomersViaReactive() { //asynch! using Flux
		return this.service.loadAllCustomersAsynchronousCall(); //browser is showing new customer each sec!
	}
}
