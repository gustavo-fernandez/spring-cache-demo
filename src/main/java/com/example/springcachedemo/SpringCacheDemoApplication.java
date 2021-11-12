package com.example.springcachedemo;

import com.example.springcachedemo.model.Person;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
@Slf4j
public class SpringCacheDemoApplication {

	public static void main(String[] args) {
		var appContext = SpringApplication.run(SpringCacheDemoApplication.class, args);
		PersonService personService = appContext.getBean(PersonService.class);
		var cacheManager = appContext.getBean(CacheManager.class);

		log.info("Iniciando...");
		var gustavo1 = personService.getPerson("12345678");
		log.info("Gustavo 1: {}", gustavo1);
		validateTimeInCache(cacheManager);
	}

	@SneakyThrows
	private static void validateTimeInCache(CacheManager cacheManager) {
		int seconds = 0;
		while (true) {
			TimeUnit.SECONDS.sleep(1);
			seconds++;
			var itemInCache = cacheManager.getCache("people").get("12345678", Person.class);
			if (itemInCache == null) {
				break;
			}
		}
		log.info("Seconds in cache: {}", seconds);
	}

	/*@Bean
	CacheManager concurrentMapCacheManager() {
		return new ConcurrentMapCacheManager("people", "product");
	}*/
	@Bean
	CacheManager caffeineCacheManager() {
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
		caffeineCacheManager.registerCustomCache("people",
			Caffeine.newBuilder()
				.expireAfterWrite(Duration.ofSeconds(8))
				.build());
		caffeineCacheManager.registerCustomCache("product",
			Caffeine.newBuilder()
				.maximumSize(100)
				.expireAfterWrite(Duration.ofMinutes(10))
				.build());
		return caffeineCacheManager;
	}

}
