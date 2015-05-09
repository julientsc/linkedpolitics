package ch.eiafr.tsc.data.global.job;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.global.job.cache.SerializeObject;

public class JobExecutor {
	
	private static Logger logger = Logger.getLogger(JobExecutor.class);
	
	private static SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
	
	private IJob job;
	
	private JobExecutorConfig jobExecutorConfig = null;

	private File organizerFile = null;
	private File activeFile = null;
	
	public JobExecutor(IJob job, JobExecutorConfig jobExecutorConfig) {
		this.job = job;
		this.jobExecutorConfig = jobExecutorConfig;
	}
	
	private String moduleName = "NotSet";

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void doProc(ArrayList<String> params) {
		logger.info("{"+ moduleName +"} Start Job Executor");
		
		if(prepareFiles()) {
			globalProc(params, job);
		}
		
		
		logger.info("{"+ moduleName +"} Stop Job Executor");
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
			logger.fatal("{"+ moduleName +"} Cannot create the cache directory");
			return false;
		}

		this.organizerFile = new File(jobExecutorConfig.getOrganiserFilePath());
		this.activeFile = new File(jobExecutorConfig.getActiveFilePath());
		
		if(this.organizerFile == null || this.activeFile == null)
			return false;
		
		return true;
	}

	private void globalProc(ArrayList<String> params, IJob job) {

		logger.debug("{"+ moduleName +"} Initialize file last <" + organizerFile.toString() + ">");
		SerializeObject<HashMap<String, Date>> obj = new SerializeObject<HashMap<String, Date>>();
		HashMap<String, Date> lastNew = obj.read(organizerFile);
		if (lastNew == null)
			lastNew = new HashMap<String, Date>();
		
		logger.debug("{"+ moduleName +"} Initialize file active <" + activeFile.toString() + ">");
		SerializeObject<HashMap<String, Boolean>> objA = new SerializeObject<HashMap<String, Boolean>>();
		HashMap<String, Boolean> active = objA.read(activeFile);
		if (active == null)
			active = new HashMap<String, Boolean>();
		
		// add new entry on the state if needed
		logger.debug("{"+ moduleName +"} Check new entries");
		for (String param : params) {
			if (!lastNew.containsKey(param)) {
				lastNew.put(param, new Date(0));
				logger.debug("{"+ moduleName +"} + " + param);
			}
			if (!active.containsKey(param)) {
				active.put(param, true);
				logger.debug("{"+ moduleName +"} + " + param);
			}
		}

		// save backup file
		obj.write(lastNew, organizerFile);
		objA.write(active, activeFile);

		// list users to update
		logger.debug("{"+ moduleName +"} Start params to monitoring");
		for (String keyword : lastNew.keySet()) {
			logger.debug("{"+ moduleName +"} " + keyword + ": last update :  "
					+ SDF.format(lastNew.get(keyword)));
		}

		logger.debug("{"+ moduleName +"} Update process start");
		try {
			// clean param list
			@SuppressWarnings("unchecked")
			ArrayList<String> activeParams = (ArrayList<String>) params.clone();
			for(String param: params){
				if(!active.get(param))
					activeParams.remove(param);
			}
			
			while (activeParams.size() > 0) {
				
				String paramToUpdate = getParamsToUpdate(activeParams, lastNew, active);

				logger.info("{"+ moduleName +"} Processing for " + paramToUpdate);
				
				if(!job.doProcAndContinue(paramToUpdate, jobExecutorConfig.getJsonDirectoryPath())) {
					logger.warn("{"+ moduleName +"} Finish with error for " + paramToUpdate);
				}

				job.doProcAndStop(paramToUpdate, jobExecutorConfig.getJsonDirectoryPath());
				
				lastNew.put(paramToUpdate, new Date());
				obj.write(lastNew, organizerFile);
				
				activeParams.remove(paramToUpdate);
			}
			
			logger.info("{"+ moduleName +"} Update process finish : OK");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("{"+ moduleName +"} Update process finish with error");
		}
	}
	
	private String getParamsToUpdate(ArrayList<String> params, HashMap<String, Date> lastNew, HashMap<String, Boolean> active) {
		// search the oldest user
		Date compareDate = null;
		String resultToUpdate = null;
		for (String param : params) {
			if (compareDate == null || compareDate.compareTo(lastNew.get(param)) > 0){
				compareDate = lastNew.get(param);
				resultToUpdate = param;
			}
		}
		return resultToUpdate;
	}
	
}
