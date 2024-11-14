package com.chitta.qbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chitta.qbs.entity.Customer;
import com.chitta.qbs.exception.ResourceNotFoundException;
import com.chitta.qbs.repo.CustomerRepository;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public Page<Customer> getCustomers(String customerId, String accountNumber, String description, Pageable pageable) {
		return customerRepository.findByCriteria(customerId, accountNumber, description, pageable);
	}

	@Transactional
	public Customer updateCustomerDescription(Long id, String newDescription) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
		customer.setDescription(newDescription);
		return customerRepository.save(customer);
	}
}
