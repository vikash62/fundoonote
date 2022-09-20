package com.bridgelabz.fundoonote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bridgelabz.fundoonote.user.utility.TokenManager;

import org.modelmapper.ModelMapper;

@SpringBootApplication
public class FundoonoteApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public TokenManager tokenManager() {
		return new TokenManager();
	}
	public static void main(String[] args) {
		SpringApplication.run(FundoonoteApplication.class, args);
	}

}
