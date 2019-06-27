package com.demo.spring;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import com.demo.spring.model.Emp;

@Configuration
@ComponentScan("com.demo.spring")
@EnableBatchProcessing
public class AppConfig {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Value("input/employees.xml")
	private Resource inputFile;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/springdb");
		ds.setUsername("root");
		ds.setPassword("root");
		return ds;
	}

	@Bean
	public JobRepository jobRepository(PlatformTransactionManager tx) throws Exception {
		JobRepositoryFactoryBean repo = new JobRepositoryFactoryBean();
		repo.setDataSource(dataSource());
		repo.setTransactionManager(tx);
		repo.afterPropertiesSet();
		return repo.getObject();
	}

	@Bean
	public JobLauncher jobLauncher(JobRepository repository) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(repository);
		jobLauncher.afterPropertiesSet();
		return jobLauncher;

	}

	@Bean
	public ItemReader<Emp> itemReader() {
		StaxEventItemReader<Emp> reader=new StaxEventItemReader<>();
		reader.setResource(inputFile);
		reader.setFragmentRootElementName("emp");
		reader.setUnmarshaller(marshaller());
		return reader;
	}

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller= new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Emp.class);
		return marshaller;
	}
	@Bean
	public ItemProcessor<Emp, Emp> itemProcessor() {
		return new EmpItemProcessor();
	}

	@Bean
	public ItemWriter<Emp> itemWriter() {
		JdbcBatchItemWriter<Emp> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(dataSource());
		writer.setSql("INSERT INTo EMP(EMPNO,NAME,ADDRESS,SALARY) VALUES (:empId,:name,:city,:salary)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		return writer;
	}

	@Bean
	public Step step1() {
		return steps.get("step1")
				.<Emp, Emp>chunk(5)
				.reader(itemReader())
				.processor(itemProcessor())
				.writer(itemWriter())
				.faultTolerant()
				//.retryLimit(5)
				//.retry(Exception.class)
				.skipLimit(1)
				.skip(Exception.class)
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobs.get("my first Job").start(step1()).build();
	}
}
