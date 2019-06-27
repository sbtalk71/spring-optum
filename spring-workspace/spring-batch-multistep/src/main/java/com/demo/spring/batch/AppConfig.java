package com.demo.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.demo.spring.batch.model.CustomItemProcessor;
import com.demo.spring.batch.model.CustomItemProcessor2;
import com.demo.spring.batch.model.Employee;
import com.demo.spring.batch.model.RecordFieldSetMapper;
import com.demo.spring.batch.model.Report;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.demo.spring.batch")
public class AppConfig {
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;
	@Value("input/records.csv")
	private Resource inputFile;

	@Value("file:xml/presons.xml")
	private Resource output;
	
	@Autowired
	MemItemReader reader2;

	@Bean
	public JobLauncher jobLauncher(JobRepository repo) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(repo);
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}

	@Bean
	public ItemReader<Employee> itemReader() throws UnexpectedInputException, ParseException {

		FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<Employee>();
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		String[] tokens = { "id", "name", "city", "position" };
		tokenizer.setNames(tokens);
		itemReader.setResource(inputFile);
		DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<Employee>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}

	@Bean
	public ItemProcessor<Employee, Employee> itemProcessor() {
		return new CustomItemProcessor();
	}
	@Bean
	public ItemProcessor<Employee, Report> itemProcessor2() {
		return new CustomItemProcessor2();
	}

	@Bean
	public ItemWriter<Report> itemWriter() {
		StaxEventItemWriter<Report> itemWriter = new StaxEventItemWriter<Report>();
		itemWriter.setMarshaller(marshaller());
		itemWriter.setRootTagName("employee-report");
		itemWriter.setResource(output);
		return itemWriter;
	}

	@Bean
	public Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Report.class);
		return marshaller;
	}
	
	@Bean
	public ListItemWriter<Employee> listItemWriter(){
		return new ListItemWriter<>();
	}
	
	@Bean
	@StepScope
	public MemItemReader memReader(ListItemWriter<Employee> listItemWriter) {
		return new MemItemReader(listItemWriter);
	}

	@Bean
	public Step step1() {
		return steps.get("step1").<Employee, Employee>chunk(10).reader(itemReader()).processor(itemProcessor())
				.writer(listItemWriter()).build();
	}
	@Bean
	public Step step2() {
		return steps.get("step2").<Employee, Report>chunk(10).reader(reader2).processor(itemProcessor2())
				.writer(itemWriter()).build();
	}

	@Bean(name = "firstBatchJob")
	public Job job(@Qualifier("step1") Step step1) {
		return jobs.get("firstBatchJob").start(step1).next(step2()).build();
	}

}
