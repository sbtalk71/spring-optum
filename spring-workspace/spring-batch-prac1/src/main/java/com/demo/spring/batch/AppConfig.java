package com.demo.spring.batch;

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
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
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
import com.demo.spring.batch.model.RecordFieldSetMapper;
import com.demo.spring.batch.model.Transaction;

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

	@Bean
	public JobLauncher jobLauncher(JobRepository repo) throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(repo);
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}

	@Bean
	public ItemReader<Transaction> itemReader() throws UnexpectedInputException, ParseException {

		FlatFileItemReader<Transaction> itemReader = new FlatFileItemReader<Transaction>();
		itemReader.setResource(inputFile);
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		String[] tokens = { "username", "userid", "transactiondate", "amount" };
		tokenizer.setNames(tokens);
		
		DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<Transaction>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
		
		itemReader.setLineMapper(lineMapper);
		return itemReader;
	}

	@Bean
	public ItemProcessor<Transaction, Transaction> itemProcessor() {
		return new CustomItemProcessor();
	}

	@Bean
	public ItemWriter<Transaction> itemWriter(Marshaller marshaller) {
		StaxEventItemWriter<Transaction> itemWriter = new StaxEventItemWriter<Transaction>();
		itemWriter.setMarshaller(marshaller);
		itemWriter.setRootTagName("transaction");
		itemWriter.setResource(output);
		return itemWriter;
	}

	@Bean
	public Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Transaction.class);
		return marshaller;
	}

	@Bean
	public Step step1(ItemReader<Transaction> itemReader, ItemProcessor<Transaction, Transaction> itemProcessor,
			ItemWriter<Transaction> writer) {
		return steps.get("step1").<Transaction, Transaction>chunk(10).reader(itemReader).processor(itemProcessor)
				.writer(writer).build();
	}

	@Bean(name = "firstBatchJob")
	public Job job(@Qualifier("step1") Step step1) {
		return jobs.get("firstBatchJob").start(step1).build();
	}

}
