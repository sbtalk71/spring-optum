package com.demo.spring;



import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

	public static void main(String[] args) {
		ApplicationContext ctx=new AnnotationConfigApplicationContext(AppConfig.class);
		
		Job job= (Job)ctx.getBean("job");
		
		JobLauncher jobLauncher=(JobLauncher)ctx.getBean("jobLauncher");
		
		try {
			JobParameters params=new JobParametersBuilder()
					.addString("jobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
			
		JobExecution jobExecution=jobLauncher.run(job,params );
		
		System.out.println(jobExecution.getExitStatus());
		System.out.println("Job Completed..");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Job Failed..");
		}

	}

}
