package com.formacionbdi.springboot.app.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Clase de arranque de la aplicación SpringBoot.
 * @EnableEurekaClient: Se Habilita Eureka Client.
 * @EnableFeignClients: Se habilita el cliente Feign.
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioOauthApplication.class, args);
	}

}
