package com.demo.spring;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.demo.spring")
@EnableBatchProcessing
public class AppConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Bean
	public JobLauncher jobLauncher(JobRepository repository) throws Exception{
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(repository);
		jobLauncher.afterPropertiesSet();
		return jobLauncher;

	}
	
	@Bean
	public ItemReader<String> itemReader(){
		return new MyStringGenerator();
	}
	
	@Bean
	public ItemProcessor<String, String> itemProcessor(){
		return new MyStringModifier();
	}
	
	@Bean
	public ItemWriter<String> itemWriter(){
		return new MyItemPrinter();
	}
	
	@Bean
	public Step step1() {
		return steps.get("step1")
				.<String,String>chunk(3)
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.build();
	}
	
	@Bean
	public Job job() {
		return jobs.get("my first Job").start(step1()).build();
	}
}
