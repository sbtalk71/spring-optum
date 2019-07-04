package com.demo.spring.batch;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


//@SpringBatchTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {AppConfig.class,TestConfig.class})
public class SpringBatchDemoTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

   

    @Test
    public void testJob() throws Exception {
       

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();


        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }
    
    @Test
    public void testStep1() throws Exception{
    	JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1");
    	
    	Assert.assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
    }
}