package com.example.marlace;

import com.example.marlace.filters.AuthFilter;
import com.example.marlace.utilities.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class MarlaceApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(MarlaceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MarlaceApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();
		registrationBean.setFilter(authFilter);
		for (final String url : Constants.URL_PATTERNS) {
			log.info(String.format("Adding url pattern: '%s'", url));
			registrationBean.addUrlPatterns(url);
		}
		return registrationBean;
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
