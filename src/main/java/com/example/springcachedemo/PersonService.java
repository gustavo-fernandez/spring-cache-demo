package com.example.springcachedemo;

import com.example.springcachedemo.model.Person;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonService {

  @Cacheable(cacheNames = "people", key = "#dni")
  public Person getPerson(String dni) {
    log.info("executing PersonService.getPerson({})", dni);
    // va a RENIEC y consulta a la persona por su DNI
    simularDemoraDeReniec();
    return new Person(dni, "Gustavo");
  }

  @SneakyThrows
  private void simularDemoraDeReniec() {
    TimeUnit.SECONDS.sleep(5);
  }

}
