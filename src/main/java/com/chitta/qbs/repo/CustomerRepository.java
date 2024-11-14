package com.chitta.qbs.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chitta.qbs.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	@Query("SELECT c FROM Customer c WHERE " + "(c.customerId = :customerId OR :customerId IS NULL) AND "
			+ "(c.accountNumber = :accountNumber OR :accountNumber IS NULL) AND "
			+ "(c.description LIKE %:description% OR :description IS NULL)")
	Page<Customer> findByCriteria(@Param("customerId") String customerId, @Param("accountNumber") String accountNumber,
			@Param("description") String description, Pageable pageable);
}
