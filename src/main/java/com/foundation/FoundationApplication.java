package com.foundation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@MapperScan("com.foundation.dao.mapper")
@ImportResource("classpath:/config/component/spring-config.xml")
public class FoundationApplication {

	public static void main(String[] args) {
	    SpringApplication.run(FoundationApplication.class, args);
	}

}
