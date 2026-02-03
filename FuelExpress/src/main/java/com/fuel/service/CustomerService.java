package com.fuel.service;

import java.util.List;

import com.fuel.model.Customer;

public interface CustomerService {

	void saveCustomer(Customer customer);

	List<Customer> getAllCustomers();

}
