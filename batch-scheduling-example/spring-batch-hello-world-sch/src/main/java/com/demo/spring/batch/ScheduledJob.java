package com.demo.spring.batch;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@Scope("singleton")
public class ScheduledJob {

	@Autowired
	Job job;
	@Autowired
	JobLauncher jobLauncher;

	//@Scheduled(cron = "0 */1 * * * ?")  /job triggers every 1 minute
	@Scheduled(fixedRate=20000) //job triggers every 20 seconds
	public void perform() throws Exception {
		JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
	}
}