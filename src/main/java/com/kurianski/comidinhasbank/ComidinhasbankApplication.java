package com.kurianski.comidinhasbank;

import com.kurianski.comidinhasbank.service.UserDetailsServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ComidinhasbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComidinhasbankApplication.class, args);
	}

	@Bean
	public UserDetailsServiceImpl userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
