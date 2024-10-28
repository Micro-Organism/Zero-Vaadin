package com.zero.vaddin.common.config;

import com.zero.vaddin.common.repository.SystemUserRepository;
import com.zero.vaddin.domain.entity.SystemUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class VaadinConfig {

    @Bean
    public CommandLineRunner loadData(SystemUserRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new SystemUserEntity("Jack", "Bauer"));
            repository.save(new SystemUserEntity("Chloe", "O'Brian"));
            repository.save(new SystemUserEntity("Kim", "Bauer"));
            repository.save(new SystemUserEntity("David", "Palmer"));
            repository.save(new SystemUserEntity("Michelle", "Dessler"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (SystemUserEntity systemUserEntity : repository.findAll()) {
                log.info(systemUserEntity.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            SystemUserEntity systemUserEntity = repository.findById(1L).get();
            log.info("Customer found with findOne(1L):");
            log.info("--------------------------------");
            log.info(systemUserEntity.toString());
            log.info("");

            // fetch customers by last name
            log.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
            log.info("--------------------------------------------");
            for (SystemUserEntity bauer : repository.findByLastNameStartsWithIgnoreCase("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");
        };
    }

}
