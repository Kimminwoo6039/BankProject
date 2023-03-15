package com.www.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BankProjectApplication {

    public static void main(String[] args) {
       SpringApplication.run(BankProjectApplication.class, args);
    }

//    public static void main(String[] args) {
//        ConfigurableApplicationContext context =  SpringApplication.run(BankProjectApplication.class, args);
//        String[] names = context.getBeanDefinitionNames();
//        for (String name : names ) {
//            System.out.println("name = " + name);
//        }
//
//    }

}
