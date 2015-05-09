package ch.eiafr.tsc.data.bing.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.bing.model.BingResult;
import ch.eiafr.tsc.data.bing.model.BingResultModel;
import ch.eiafr.tsc.data.bing.model.KeywordResume;
import ch.eiafr.tsc.data.global.job.JobExecutorConfig;
import ch.eiafr.tsc.data.global.job.cache.JsonCache;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;
import ch.eiafr.tsc.global.data.rest.MonitorList;

import com.google.gson.Gson;

public class GetData {
	private static Logger logger = Logger.getLogger(GetData.class);

	private static JobExecutorConfig jobExecutorConfig = null;
	private static HashMap<String, Date> getOrganizer() {
		SerializeObject<HashMap<String, Date>> obj = new SerializeObject<HashMap<String, Date>>();
		HashMap<String, Date> lastNew = obj.read(new File(jobExecutorConfig
				.getOrganiserFilePath()));
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

	public static List<KeywordResume> getList() {
		try {
			HashMap<String, Date> dates = getOrganizer();
			ArrayList<KeywordResume> keywords = new ArrayList<>();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath()+"keywords");
			for (File f : dir.listFiles()) {
				KeywordResume kr = new KeywordResume();
				kr.setKeyword(f.getName());
				kr.updateLink();
				kr.setKeyword(kr.getKeyword().replaceAll("_", " "));
				String key = null;
				if((key=getContainsKey(dates, kr.getKeyword()))!=null)
					kr.setLastUpdate(dates.get(key));
				keywords.add(kr);
			}
			return keywords;
		} catch (Exception e) {
			return null;
		}
	}

	public static List<KeywordResume> getActiveList() {
		try {
			HashMap<String, Date> dates = getOrganizer();
			HashMap<String,Boolean> actives = MonitorResource.getActive();
			ArrayList<KeywordResume> keywords = new ArrayList<>();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath()+"keywords");
			for (File f : dir.listFiles()) {
				if (actives.containsKey(f.getName())&&actives.get(f.getName())) {
					KeywordResume kr = new KeywordResume();
					kr.setKeyword(f.getName());
					kr.updateLink();
					kr.setKeyword(kr.getKeyword().replaceAll("_", " "));
					String key = null;
					if((key=getContainsKey(dates, kr.getKeyword()))!=null)
						kr.setLastUpdate(dates.get(key));
					keywords.add(kr);
				}
			}
			return keywords;
		} catch (Exception e) {
			return null;
		}
	}

	public static BingResultModel getNews(String keyword) {
		try {
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath()+"keywords/"
					+ keyword);
			if (dir.exists()) {
				File[] listFile = dir.listFiles();
				Arrays.sort(listFile);
				if (listFile.length > 0) {
					BingResultModel brm = new Gson().fromJson(JsonCache
							.Read(listFile[listFile.length - 1]
									.getAbsolutePath()), BingResultModel.class);
					ArrayList<String> urls = new ArrayList<>();
					for(String s : brm.getIDs()){
						urls.add("/list/"+brm.getKeyword().replaceAll(" ", "_")+"/"+s);
					}
					brm.setIDs(urls);
					return brm;
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static BingResult getResultInfo(String keyword,String id) {
		try {
			File file = new File(jobExecutorConfig.getJsonDirectoryPath()+"results/"
					+ id);
			
			BingResult br = new Gson().fromJson(JsonCache
					.Read(file.getAbsolutePath()), BingResult.class);
			return br;
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