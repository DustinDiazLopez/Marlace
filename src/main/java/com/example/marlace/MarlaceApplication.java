package com.example.marlace;

import com.example.marlace.filter.AuthFilter;
import com.example.marlace.model.Category;
import com.example.marlace.repository.CategoryRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MarlaceApplication implements CommandLineRunner {

//	@Autowired
//	private CategoryRepositoryImpl categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(MarlaceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/categories/*");
		return registrationBean;
	}

	@Override
	public void run(String... args) throws Exception {
//		categoryRepository.createCategory(1, "TEst title", "test descoprtopm");
	}
}
