package com.ex;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.ex.*"})
//@EntityScan(basePackages = {"com.ex.*"})
//@EnableJpaRepositories(basePackages = {"com.ex.*"})
@Configuration
public class CodeApplication {
//	 @PostConstruct
//	  public void init(){
//	    // Setting Spring Boot SetTimeZone
//	    TimeZone.setDefault(TimeZone.getTimeZone("IST"));
//	  }
	public static void main(String[] args) {
		SpringApplication.run(CodeApplication.class, args);
	}

}
