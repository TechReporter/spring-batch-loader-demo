/**
 * 
 */
package com.pluralsight.springbatch.patientbatchloader.config;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author Tanmoy Dasgupta
 */

@Component
@EnableBatchProcessing
public class BatchConfiguration implements BatchConfigurer {

	private JobRepository jobRepo;
	private JobLauncher jobLauncher;
	private JobExplorer jobExplorer;
	
	@Autowired
	@Qualifier(value = "batchTransactionManager")
	private PlatformTransactionManager batchTransactionManager;

	@Autowired
	@Qualifier(value = "batchDataSource")
	private DataSource batchDataSource;
	
	@Override
	public JobRepository getJobRepository() throws Exception {
		return this.jobRepo;
	}
	
	@Override
	public PlatformTransactionManager getTransactionManager() throws Exception {
		return this.batchTransactionManager;
	}
	
	@Override
	public JobLauncher getJobLauncher() throws Exception {
		return this.jobLauncher;
	}

	@Override
	public JobExplorer getJobExplorer() throws Exception {
		return this.jobExplorer;
	}
	
	protected JobLauncher createJobLauncher() throws Exception {
	    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
	    jobLauncher.setJobRepository(jobRepo);
	    jobLauncher.afterPropertiesSet();
	    return jobLauncher;
	}
	
	protected JobRepository createJobRepository() throws Exception {
	    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
	    factory.setDataSource(this.batchDataSource);
	    factory.setTransactionManager(getTransactionManager());
	    factory.afterPropertiesSet();
	    return factory.getObject();
	}
	
	@PostConstruct
	public void afterPropertiesSet() throws Exception {
	    this.jobRepo = createJobRepository();
	    JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
	    jobExplorerFactoryBean.setDataSource(this.batchDataSource);
	    jobExplorerFactoryBean.afterPropertiesSet();
	    this.jobExplorer = jobExplorerFactoryBean.getObject();
	    this.jobLauncher = createJobLauncher();
	}

}
