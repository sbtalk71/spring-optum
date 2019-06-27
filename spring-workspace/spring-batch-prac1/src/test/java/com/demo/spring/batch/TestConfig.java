package com.demo.spring.batch;

import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

	@Bean
	public JobLauncherTestUtils jobLauncherUtils() {
		return new JobLauncherTestUtils();
	}
}
