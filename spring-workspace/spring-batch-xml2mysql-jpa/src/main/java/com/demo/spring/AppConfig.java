package com.demo.spring;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.demo.spring.model.Emp;

@Configuration
@ComponentScan("com.demo.spring")
@EnableBatchProcessing
@EnableTransactionManagement
public class AppConfig implements BatchConfigurer{

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
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lb = new LocalContainerEntityManagerFactoryBean();
		lb.setDataSource(dataSource());
		lb.setPackagesToScan("com.demo.spring.model");

		HibernateJpaVendorAdapter va = new HibernateJpaVendorAdapter();
		va.setShowSql(true);
		va.setDatabase(Database.MYSQL);

		lb.setJpaVendorAdapter(va);
		return lb;

	}

	
	
	public JpaTransactionManager getTransactionManager() {
		JpaTransactionManager tx= new JpaTransactionManager();
		tx.setEntityManagerFactory(entityManagerFactory().getObject());
		return tx;
	}
	//@Bean
	public JobRepository getJobRepository() throws Exception {
		JobRepositoryFactoryBean repo = new JobRepositoryFactoryBean();
		repo.setDataSource(dataSource());
		repo.setTransactionManager(getTransactionManager());
		repo.afterPropertiesSet();
		return repo.getObject();
	}

	/*@Bean
	public JobLauncher jobLauncher(JobRepository repository) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(repository);
		jobLauncher.afterPropertiesSet();
		return jobLauncher;

	}*/

	@Bean
	public ItemReader<Emp> itemReader() {
		StaxEventItemReader<Emp> reader = new StaxEventItemReader<>();
		reader.setResource(inputFile);
		reader.setFragmentRootElementName("emp");
		reader.setUnmarshaller(marshaller());
		return reader;
	}

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Emp.class);
		return marshaller;
	}

	@Bean
	public ItemProcessor<Emp, Emp> itemProcessor() {
		return new EmpItemProcessor();
	}

	@Bean
	public ItemWriter<Emp> itemWriter() {
		JpaItemWriter<Emp> writer=new JpaItemWriter<>();
		writer.setEntityManagerFactory(entityManagerFactory().getObject());
		
		return writer;
	}

	@Bean
	public Step step1() {
		return steps.get("step1").<Emp, Emp>chunk(5).reader(itemReader()).processor(itemProcessor())
				.writer(itemWriter())
				//.faultTolerant()
				// .retryLimit(5)
				// .retry(Exception.class)
				//.skipLimit(1).skip(Exception.class)
				.build();
	}

	@Bean
	public Job job() throws Exception {
		return jobs.get("my first Job").start(step1()).build();
	}

	@Override
	public JobLauncher getJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(getJobRepository());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}

	@Override
	public JobExplorer getJobExplorer() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
