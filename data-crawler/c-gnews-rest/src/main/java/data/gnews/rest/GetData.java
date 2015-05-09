package data.gnews.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.global.job.JobExecutorConfig;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;
import ch.eiafr.tsc.global.data.rest.MonitorList;

import com.google.gson.Gson;

import data.gnews.model.GoogleNewsModel;
import data.gnews.model.KeywordResume;

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
			if(key.replaceAll(".", "").toLowerCase().equals(s.replaceAll(".", "").toLowerCase())){
				return key;
			}
		}
		return null;
	}
	
	public static List<KeywordResume> getList(){
		try{
			HashMap<String, Date> dates = getOrganizer();
			ArrayList<KeywordResume> keywords = new ArrayList<>();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath());
			for(File f : dir.listFiles()){
				KeywordResume kr = new KeywordResume();
				kr.setKeyword(f.getName());
				kr.updateLink();
				kr.setKeyword(kr.getKeyword().replaceAll("_"," "));
				String key = null;
				if((key=getContainsKey(dates, kr.getKeyword()))!=null)
					kr.setLastUpdate(dates.get(key));
				keywords.add(kr);
			}
			return keywords;
		}catch(Exception e){
			return null;
		}
	}
	
	public static List<KeywordResume> getActiveList(){
		try{
			HashMap<String, Date> dates = getOrganizer();
			HashMap<String,Boolean> actives = MonitorResource.getActive();
			ArrayList<KeywordResume> keywords = new ArrayList<>();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath());
			for(File f : dir.listFiles()){
				if(actives.containsKey(f.getName())&&actives.get(f.getName())){
					KeywordResume kr = new KeywordResume();
					kr.setKeyword(f.getName());
					kr.updateLink();
					kr.setKeyword(kr.getKeyword().replaceAll("_"," "));
					String key = null;
					if((key=getContainsKey(dates, kr.getKeyword()))!=null)
						kr.setLastUpdate(dates.get(key));
					keywords.add(kr);
				}
			}
			return keywords;
		}catch(Exception e){
			return null;
		}
	}
	
	public static GoogleNewsModel getNews(String keyword){
		try{
			File f = new File(jobExecutorConfig.getJsonDirectoryPath()+"/"+keyword);
			Gson gson = new Gson();
			GoogleNewsModel googleNews = null;
			FileInputStream fis = null;
			InputStreamReader isr = null;
			try {
				fis = new FileInputStream(f);
				isr = new InputStreamReader(fis, "UTF-8");
				googleNews = gson.fromJson(isr, GoogleNewsModel.class);
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (isr != null) {
					isr.close();
				}
			}
			return googleNews;
		}catch(Exception e){
			return null;
		}
	}
	
	public static void setJobExecutorConfig(JobExecutorConfig jobExecutorConfig) {
		GetData.jobExecutorConfig = jobExecutorConfig;
	}

	public static void setMonitorList(MonitorList monitorList) {
	}
}