package com.example.springcachedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
@Slf4j
public class SpringCacheDemoApplication {

	public static void main(String[] args) {
		var appContext = SpringApplication.run(SpringCacheDemoApplication.class, args);
		PersonService personService = appContext.getBean(PersonService.class);

		log.info("Iniciando...");
		var gustavo1 = personService.getPerson("12345678");
		log.info("Gustavo 1: {}", gustavo1);
		// ...
		var gustavo2 = personService.getPerson("12345678");
		log.info("Gustavo 2: {}", gustavo2);
	}

	@Bean
	CacheManager concurrentMapCacheManager() {
		return new ConcurrentMapCacheManager("people", "product");
	}

}
