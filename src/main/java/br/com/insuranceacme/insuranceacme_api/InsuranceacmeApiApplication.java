package br.com.insuranceacme.insuranceacme_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class InsuranceacmeApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceacmeApiApplication.class, args);
    }

}
