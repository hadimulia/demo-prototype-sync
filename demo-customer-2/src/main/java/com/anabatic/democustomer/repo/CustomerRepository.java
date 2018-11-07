package com.anabatic.democustomer.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anabatic.democustomer.entity.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,UUID>{

}
