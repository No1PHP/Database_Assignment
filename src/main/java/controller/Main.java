package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import service.DataInitializer;

@SpringBootApplication
//@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Main {
    public static void main(String args[]) {
        SpringApplication.run(Main.class, args);
    }
}
