package ch.eiafr.tsc.test;

import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.global.job.IJob;
import ch.eiafr.tsc.data.global.job.JobExecutor;
import ch.eiafr.tsc.data.global.job.JobExecutorConfig;

public class Test {

	final static Logger logger = Logger.getLogger(Test.class);
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		JobExecutorConfig jobExecutorConfig = new JobExecutorConfig();
		jobExecutorConfig.setCacheDirectoryPath("./cache");
		jobExecutorConfig.setConfigurationDirectoryPath("./config");
		jobExecutorConfig.setJsonDirectoryPath("./json");
		jobExecutorConfig.setLogDirectoryPath("./log/");
		jobExecutorConfig.setOrganiserFileName("organiser.bin");
		jobExecutorConfig.setActiveFileName("active.bin");
		
		ArrayList<String> params = new ArrayList<String>();
		for(int i = 0 ; i < 10 ; i ++)
			params.add(String.valueOf(i));
		
		JobExecutor jobExecutor = new JobExecutor(new IJob() {
			
			@Override
			public void doProcAndStop(String param, String cacheDir) throws Exception {
				// TODlogger.debug(param);O Auto-generated method stub
				
			}
			
			@Override
			public boolean doProcAndContinue(String param, String cacheDir) {
				return false;
			}
		}, jobExecutorConfig);
		
		jobExecutor.doProc(params);
	}

}
