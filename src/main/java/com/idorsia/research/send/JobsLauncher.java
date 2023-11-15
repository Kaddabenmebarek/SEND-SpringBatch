package com.idorsia.research.send;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
//import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.actelion.research.spirit.core.services.dao.DAOStudy;
import com.idorsia.research.send.processors.Constants;

public class JobsLauncher {
	
	/**
	 * @param args StudyId
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ApplicationContext context = null;
		JobParameters jobParameters = null;
		Job jobToLaunch = null;
		if(args.length == 0) {
			String[] springConfig  = {"spring/batch/config/context.xml","spring/batch/jobs/dataToExcelJob.xml"};
			context = new ClassPathXmlApplicationContext(springConfig);	
			jobParameters = new JobParameters();
			jobToLaunch = (Job) context.getBean("dataToExcelJob");
		}else {
			String[] springConfig  = {"spring/batch/config/context.xml","spring/batch/config/database.xml","spring/batch/jobs/dBToXptJob.xml"};
			context = new ClassPathXmlApplicationContext(springConfig);
//			DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry) ((ConfigurableApplicationContext) context).getBeanFactory();
//			//registry.destroySingleton("currentStudy");
//			registry.registerSingleton("currentStudy", DAOStudy.getStudyByStudyId(args[0])); 
			jobParameters = new JobParametersBuilder().addString(Constants.STUDYID, args[0]).toJobParameters();
			jobToLaunch = (Job) context.getBean("dBToXptJob");
			//TODO ADD A NEW JOB TO PARSE FLAT FILES (TEMPLATES) FOR MISSING DOMAINS
		}
		JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
		try {
			JobExecution inputToExcelJobExecution = jobLauncher.run(jobToLaunch, jobParameters);
			System.out.println("DbToXpt Job Exit Status : " + inputToExcelJobExecution.getStatus());
//			if(dataToExcelJobExecution.getStatus() == BatchStatus.COMPLETED) {
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Process finished");

	}
	
}
