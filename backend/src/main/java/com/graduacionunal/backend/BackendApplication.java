package com.graduacionunal.backend;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.graduacionunal.backend")
@EntityScan(basePackages = "com.graduacionunal.backend.models")
@EnableJpaRepositories(basePackages = "com.graduacionunal.backend.repositories")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
