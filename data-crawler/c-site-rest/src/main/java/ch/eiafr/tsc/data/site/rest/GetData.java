package ch.eiafr.tsc.data.site.rest;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.global.job.JobExecutorConfig;
import ch.eiafr.tsc.data.global.job.cache.JsonCache;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;
import ch.eiafr.tsc.data.site.model.KeywordResume;
import ch.eiafr.tsc.data.site.model.SiteModel;
import ch.eiafr.tsc.global.data.rest.MonitorList;

import com.google.gson.Gson;


public class GetData {

	private static Logger logger = Logger.getLogger(GetData.class);
	
	private static JobExecutorConfig jobExecutorConfig = null;
	private static HashMap<String, Date> getOrganizer() {
		SerializeObject<HashMap<String, Date>> obj = new SerializeObject<HashMap<String, Date>>();
		HashMap<String, Date> lastNew = obj.read(new File(jobExecutorConfig.getOrganiserFilePath()));
		return lastNew;
	}
	
	private static String getContainsKey(HashMap<String, ?> map, String s){
		for(String key : map.keySet()){
			if(key.replace("://", ".").replace('/', '.').replace('#', '.').equals(s)){
				return key;
			}
		}
		return null;
	}
	
	public static List<KeywordResume> getList(){
		logger.debug("Start getList");
		try{
			HashMap<String, Date> dates = getOrganizer();
			ArrayList<KeywordResume> keywords = new ArrayList<>();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath());
			for(File f : dir.listFiles()){
				KeywordResume kr = new KeywordResume();
				String key = getContainsKey(dates, f.getName());
				kr.setParam(f.getName());
				kr.updateLink();
				kr.setParam(key);
				if(dates.containsKey(key))
					kr.setLastUpdate(dates.get(key));
				keywords.add(kr);
			}
			return keywords;
		}catch(Exception e){
			logger.error("Cannot get the list");
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<KeywordResume> getActiveList(){
		logger.debug("Start getActiveList");
		try{
			HashMap<String, Date> dates = getOrganizer();
			HashMap<String,Boolean> actives = MonitorResource.getActive();
			ArrayList<KeywordResume> keywords = new ArrayList<>();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath());
			for(File f : dir.listFiles()){
				String key = getContainsKey(actives, f.getName());
				if(key!=null&&actives.get(key)){
					KeywordResume kr = new KeywordResume();
					kr.setParam(f.getName());
					kr.updateLink();
					kr.setParam(key);
					String key2 = getContainsKey(dates, f.getName());
					if(dates.containsKey(key2))
						kr.setLastUpdate(dates.get(key2));
					keywords.add(kr);
				}
			}
			return keywords;
		}catch(Exception e){
			logger.error("Cannot get the active list");
			e.printStackTrace();
			return null;
		}
	}
	
	public static SiteModel getNews(String keyword){
		try {
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath()
					+ keyword);
			if (dir.exists()) {
				File[] listFile = dir.listFiles();
				Arrays.sort(listFile);
				if (listFile.length > 0) {
					return new Gson().fromJson(JsonCache
							.Read(listFile[listFile.length - 1]
									.getAbsolutePath()), SiteModel.class);
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void setJobExecutorConfig(JobExecutorConfig jobExecutorConfig) {
		GetData.jobExecutorConfig = jobExecutorConfig;
	}

	public static void setMonitorList(MonitorList monitorList) {
	}
}