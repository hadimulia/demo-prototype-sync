package com.anabatic.demoproduct.handler;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anabatic.demoproduct.command.CreateProductCommand;
import com.anabatic.demoproduct.entity.Product;
import com.anabatic.demoproduct.services.ProductServices;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductServices productServices;
	
	@PostMapping("")
	public ResponseEntity<?> add(@RequestBody CreateProductCommand body,HttpServletRequest request,HttpServletResponse response){
		try {
			Product p =  new Product(UUID.randomUUID(), body.getName(), body.getQty(), body.getDesc());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(productServices.save(p));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(e);
		}
	}
}
