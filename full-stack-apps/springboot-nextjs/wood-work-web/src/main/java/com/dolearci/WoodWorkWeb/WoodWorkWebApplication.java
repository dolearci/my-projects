package com.dolearci.WoodWorkWeb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dolearci.WoodWorkWeb.entities.WoodWork;
import com.dolearci.WoodWorkWeb.services.WoodWorkService;

@SpringBootApplication
public class WoodWorkWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(WoodWorkWebApplication.class, args);
	}
}
