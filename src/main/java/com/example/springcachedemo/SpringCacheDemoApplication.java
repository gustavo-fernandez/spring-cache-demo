package com.example.springcachedemo;

import com.example.springcachedemo.model.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class SpringCacheDemoApplication {

	public static void main(String[] args) {
		var appContext = SpringApplication.run(SpringCacheDemoApplication.class, args);
		var cacheManager = appContext.getBean(CacheManager.class);
		Cache peopleCache = cacheManager.getCache("people");
		peopleCache.put("gustavo", new Person("12345678", "Gustavo"));
		Person gustavo = peopleCache.get("gustavo", Person.class);
		System.out.println(gustavo);
	}

	@Bean
	CacheManager concurrentMapCacheManager() {
		return new ConcurrentMapCacheManager("people", "product");
	}

}
