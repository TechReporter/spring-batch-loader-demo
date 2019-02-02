/**
 * 
 */
package com.pluralsight.springbatch.patientbatchloader.web.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pluralsight.springbatch.patientbatchloader.config.Constants;

/**
 * @author Tanmoy Dasgupta
 */

@RestController
@RequestMapping("/job")
public class JobResource {

	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@GetMapping("/{fileName:.+}")
	public ResponseEntity<String> runJob(@PathVariable String fileName) {
	    Map<String, JobParameter> parameterMap = new HashMap<>();
	    parameterMap.put(Constants.JOB_PARAM_FILE_NAME, new JobParameter(fileName));
	    try {
	        jobLauncher.run(job, new JobParameters(parameterMap));
	    } catch (Exception e) {
	        return new ResponseEntity<String>("Failure: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    return new ResponseEntity<String>("Success", HttpStatus.OK);
	 }
}
