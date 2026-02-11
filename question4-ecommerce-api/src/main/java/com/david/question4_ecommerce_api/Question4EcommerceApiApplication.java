package com.david.question4_ecommerce_api;

import com.david.question4_ecommerce_api.model.Product;
import com.david.question4_ecommerce_api.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Question4EcommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Question4EcommerceApiApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ProductService productService) {
		return args -> {
			productService
					.addProduct(new Product(null, "iPhone 15", "Apple smartphone", 999.99, "Electronics", 50, "Apple"));
			productService.addProduct(new Product(null, "Samsung Galaxy S24", "Samsung smartphone", 899.99,
					"Electronics", 40, "Samsung"));
			productService
					.addProduct(new Product(null, "MacBook Pro", "Apple laptop", 1999.99, "Electronics", 20, "Apple"));
			productService
					.addProduct(new Product(null, "Nike Air Max", "Running shoes", 129.99, "Fashion", 100, "Nike"));
			productService.addProduct(
					new Product(null, "Adidas Ultraboost", "Running shoes", 139.99, "Fashion", 80, "Adidas"));
			productService.addProduct(new Product(null, "Sony WH-1000XM5", "Noise cancelling headphones", 349.99,
					"Electronics", 30, "Sony"));
			productService.addProduct(
					new Product(null, "Kindle Paperwhite", "E-reader", 139.99, "Electronics", 60, "Amazon"));
			productService.addProduct(new Product(null, "Levi's 501", "Jeans", 59.99, "Fashion", 150, "Levi's"));
			productService
					.addProduct(new Product(null, "Instant Pot", "Pressure cooker", 89.99, "Home", 45, "Instant Pot"));
			productService
					.addProduct(new Product(null, "Yoga Mat", "Non-slip yoga mat", 29.99, "Sports", 200, "Lululemon"));

			System.out.println("Seeded 10 products successfully.");
		};
	}

}
