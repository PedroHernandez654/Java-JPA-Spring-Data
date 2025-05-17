package com.platzi.pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//Para utilizar el repositorio de JPA se requiere este @
@EnableJpaRepositories
public class PlatziPizzeriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlatziPizzeriaApplication.class, args);
	}

}