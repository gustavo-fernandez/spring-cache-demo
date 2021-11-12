package com.example.springcachedemo;

import com.example.springcachedemo.model.Person;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

  public Person getPerson(String dni) {
    // va a RENIEC y consulta a la persona por su DNI
    simularDemoraDeReniec();
    return new Person(dni, "Gustavo");
  }

  @SneakyThrows
  private void simularDemoraDeReniec() {
    TimeUnit.SECONDS.sleep(5);
  }

}
