package com.foundation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.foundation.dao.mapper")
public class FoundationApplication {

	public static void main(String[] args) {
	    SpringApplication.run(FoundationApplication.class, args);
	}

}
