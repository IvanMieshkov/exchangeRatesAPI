package com.exchange.currency;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CurrencyApplication {
	public static void main(String[] args) {
		SpringApplication.run(CurrencyApplication.class, args);
	}
	@Value("${api.title}")
	private String TITLE;
	@Value("${api.description}")
	private String DESCRIPTION;
	@Bean
	public OpenAPI usersMicroserviceOpenAPI() {
		return new OpenAPI()
				.info(new Info().title(TITLE)
						.description(DESCRIPTION)
						.version("1.0"));
	}
}
