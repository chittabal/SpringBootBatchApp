package com.chitta.qbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chitta.qbs.entity.Customer;
import com.chitta.qbs.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Page<Customer>> getCustomers(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String description,
            Pageable pageable) {
        
        Page<Customer> customers = customerService.getCustomers(customerId, accountNumber, description, pageable);
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomerDescription(@PathVariable Long id, @RequestBody String newDescription) {
        Customer updatedCustomer = customerService.updateCustomerDescription(id, newDescription);
        return ResponseEntity.ok(updatedCustomer);
    }
}

