package com.www.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BankProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankProjectApplication.class, args);
    }

}
