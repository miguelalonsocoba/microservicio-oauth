package com.formacionbdi.springboot.app.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Clase de arranque de la aplicación SpringBoot.
 * @EnableEurekaClient: Se Habilita Eureka Client.
 */
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioOauthApplication.class, args);
	}

}
