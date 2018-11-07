package com.anabatic.demoproduct.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anabatic.demoproduct.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,UUID>{

}
