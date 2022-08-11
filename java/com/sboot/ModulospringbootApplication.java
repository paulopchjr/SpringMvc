package com.sboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = "com.sboot.model")
@ComponentScan(basePackages= {"com.*"})
@EnableJpaRepositories(basePackages ="com.sboot.repository" )
@EnableTransactionManagement
@EnableWebMvc

public class ModulospringbootApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ModulospringbootApplication.class, args);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/login").setViewName("/login") ; // mapeando a url login para pagina que foi criada no login.html
		registry.setOrder(Ordered.LOWEST_PRECEDENCE);
		
	}

}
