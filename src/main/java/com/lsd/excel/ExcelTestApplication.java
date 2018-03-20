package com.lsd.excel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.lsd.excel"})
@MapperScan("com.lsd.excel.server.service.impl.dao")
public class ExcelTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExcelTestApplication.class, args);
		
	}
	
	
}
