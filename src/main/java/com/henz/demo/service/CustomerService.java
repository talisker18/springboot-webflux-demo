package com.henz.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henz.demo.dao.CustomerDao;
import com.henz.demo.dto.Customer;

import reactor.core.publisher.Flux;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao dao;
	
	public List<Customer> loadAllCustomersSynchronousCall() {
		long startValue = System.currentTimeMillis();
		List<Customer> list = this.dao.getCustomersSynchronousCall();
		long endValue = System.currentTimeMillis();
		
		System.out.println("exec time = " + (endValue - startValue));
		System.out.println("list size = " + list.size());
		return list;
	}
	
	public Flux<Customer> loadAllCustomersAsynchronousCall() {
		long startValue = System.currentTimeMillis();
		Flux<Customer> stream = this.dao.getCustomersAsynchronousCall();
		long endValue = System.currentTimeMillis();
		
		System.out.println("exec time = " + (endValue - startValue));
		return stream;
	}
}
