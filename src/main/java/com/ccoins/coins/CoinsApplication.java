package com.ccoins.coins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoinsApplication
//		extends SpringBootServletInitializer
{

	public static void main(String[] args) {
		SpringApplication.run(CoinsApplication.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(CoinsApplication.class);
//	}
}
