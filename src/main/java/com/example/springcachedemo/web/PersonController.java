package com.example.springcachedemo.web;

import com.example.springcachedemo.PersonService;
import com.example.springcachedemo.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonController {

  private final CacheManager cacheManager;
  private final PersonService personService;

  @GetMapping()
  public Object getAllEntriesFromCache() {
    return cacheManager.getCacheNames()
      .stream()
      .map(cacheName -> cacheManager.getCache(cacheName))
      .map(Cache::getNativeCache);
  }

  @GetMapping("person/{dni}")
  public Person getPersonByDni(@PathVariable String dni) {
    return personService.getPerson(dni);
  }

}
