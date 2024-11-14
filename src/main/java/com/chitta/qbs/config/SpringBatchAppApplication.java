package com.chitta.qbs.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({ "com.chitta.qbs" })
@ComponentScan({ "com.chitta.qbs" })
@EnableJpaRepositories(basePackages = { "com.chitta.qbs.repo" })
public class SpringBatchAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchAppApplication.class, args);
	}

}
