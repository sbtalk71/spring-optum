package com.demo.spring.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.demo.spring.batch")

public class AppConfig {
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/springdb");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}

	/*
	 * @Bean public JobLauncher jobLauncher(JobRepository repo) throws Exception {
	 * SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
	 * jobLauncher.setJobRepository(repo); jobLauncher.afterPropertiesSet(); return
	 * jobLauncher; }
	 */

	@Bean
	public ItemReader<String> itemReader() throws UnexpectedInputException, ParseException {
		return new InputGenerator();
	}

	@Bean
	public ItemWriter<String> itemWriter() {
		return new OutputPrinter();
	}

	@Bean
	public ItemProcessor<String, String> itemProcessor() {
		return new PassThroughItemProcessor<>();
	}

	@Bean
	public Step step1() {
		return steps.get("step1").<String, String>chunk(10).reader(itemReader()).processor(itemProcessor())
				.writer(itemWriter()).faultTolerant().retry(RuntimeException.class).retryLimit(6).build();
	}

	@Bean(name = "firstBatchJob")
	public Job job(@Qualifier("step1") Step step1) {
		return jobs.get("firstBatchJob").incrementer(new RunIdIncrementer()).start(step1).build();
	}

}
