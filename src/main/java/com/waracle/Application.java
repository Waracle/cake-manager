package com.waracle;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * A sample Spring Boot application that starts the Camel routes.
 */
@SpringBootApplication
public class Application{


    /**
     * A main method to start this application.
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
    

}