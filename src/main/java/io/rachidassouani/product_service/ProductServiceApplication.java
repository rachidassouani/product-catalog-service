package io.rachidassouani.product_service;

import com.github.javafaker.Faker;
import io.rachidassouani.product_service.product.Product;
import io.rachidassouani.product_service.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ProductRepository productRepository) {
		return args -> {
			var productToSave = new Faker().random();
			String name = productToSave.hex();
			String description = productToSave.hex();
			double price = productToSave.nextDouble();

			Product product = new Product(
					name,
					description,
					price
					);
			productRepository.save(product);
		};
	}
}
