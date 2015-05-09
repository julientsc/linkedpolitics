package ch.eiafr.tsc.data.globalnoarg.job;

import java.io.File;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;

public class JobExecutor {
	
	private static Logger logger = Logger.getLogger(JobExecutor.class);
	
	private IJob job;
	
	private JobExecutorConfig jobExecutorConfig = null;
	
	public JobExecutor(IJob job, JobExecutorConfig jobExecutorConfig) {
		this.job = job;
		this.jobExecutorConfig = jobExecutorConfig;
	}

	public void doProc() {
		logger.info("Start Job Executor");
		
		if(prepareFiles()) {
			globalProc(job);
		}
		
		
		logger.info("Stop Job Executor");
	}

	private boolean prepareFiles()  {
		try {
			File cache = new File(jobExecutorConfig.getCacheDirectoryPath());
			if (!cache.isDirectory())
				cache.mkdirs();
			File log = new File(jobExecutorConfig.getLogDirectoryPath());
			if (!log.isDirectory())
				log.mkdirs();
			File json = new File(jobExecutorConfig.getJsonDirectoryPath());
			if (!json.isDirectory())
				json.mkdirs();
			File config = new File(jobExecutorConfig.getConfigurationDirectoryPath());
			if (!config.isDirectory())
				config.mkdirs();
		} catch(Exception e) {
			logger.fatal("Cannot create the cache directory");
			return false;
		}
		
		return true;
	}

	private void globalProc(IJob job) {
		logger.debug("Update process start");
		try {
			job.doProc(jobExecutorConfig.getJsonDirectoryPath());
	
			logger.info("Update process finish : OK");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Update process finish with error");
		}
	}

	
}
