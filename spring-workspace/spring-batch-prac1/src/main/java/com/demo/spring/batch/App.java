package com.demo.spring.batch;



import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

	public static void main(String[] args) throws Exception{
		AnnotationConfigApplicationContext ctx= new AnnotationConfigApplicationContext(AppConfig.class);
		JobLauncher jobLauncher=(JobLauncher)ctx.getBean("jobLauncher");
		Job job=(Job)ctx.getBean("firstBatchJob");
		
		JobExecution jobExecution=jobLauncher.run(job, new JobParameters() {});
		

	}

}
