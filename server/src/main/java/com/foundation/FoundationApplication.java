package com.foundation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@MapperScan("com.foundation")
@EnableJpaRepositories(basePackages = "com.foundation")
@EntityScan(basePackages = "com.foundation")
public class FoundationApplication {

	public static void main(String[] args) {
	    SpringApplication.run(FoundationApplication.class, args);
	}

}
