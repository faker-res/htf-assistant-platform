package com.htf.bigdata.registercenter;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableEurekaServer
public class RegisterCenterApplication extends SpringBootServletInitializer {

	@PostConstruct
	void postConstruct() {
		TimeZone.setDefault(TimeZone.getTimeZone("PRC"));
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RegisterCenterApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(RegisterCenterApplication.class);
		final ApplicationContext applicationContext = springApplication.run(args);
	}

}
