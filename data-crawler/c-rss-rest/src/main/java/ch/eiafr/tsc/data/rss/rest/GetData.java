package ch.eiafr.tsc.data.rss.rest;

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
import ch.eiafr.tsc.data.rss.model.RSSChannel;
import ch.eiafr.tsc.data.rss.model.RSSResume;
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
			if(key.replaceAll("://", ".").replaceAll("/", ".").replaceAll(":", ".")
					.replaceAll("#", ".").replaceAll("\\?",".")
					.replaceAll("<", ".").replaceAll(">",".")
					.replaceAll("&", ".").replaceAll("\\*",".")
					.replaceAll("=", ".").equals(s)){
				return key;
			}
		}
		return null;
	}
	
	public static List<RSSResume> getList(){
		try{
			HashMap<String, Date> dates = getOrganizer();
			ArrayList<RSSResume> rssList = new ArrayList<>();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath());
			Gson gson = new Gson();
			for(File f : dir.listFiles()){
				RSSChannel channel = null;
				FileInputStream fis = null;
				InputStreamReader isr = null;
				try {
					fis = new FileInputStream(f);
					isr = new InputStreamReader(fis, "UTF-8");
					channel = gson.fromJson(isr, RSSChannel.class);
					
					if(channel==null)
						logger.debug("Can't access to file "+f.getName());
					else{
						RSSResume resume = new RSSResume();
						resume.setTitle(channel.getTitle());
						resume.setDescription(channel.getDescription());
						resume.setWebsite(channel.getLink());
						String name = f.getName().substring(0, f.getName().length()-5);
						resume.setLink("/list/"+name);
						String decodedLink = getContainsKey(dates, name);
						if(decodedLink!=null)
							resume.setLastRetrival(dates.get(decodedLink));
						rssList.add(resume);
					}
				} finally {
					if (fis != null) {
						fis.close();
					}
					if (isr != null) {
						isr.close();
					}
				}	
			}
			return rssList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static List<RSSResume> getActiveList(){
		try{
			HashMap<String, Date> dates = getOrganizer();
			ArrayList<RSSResume> rssList = new ArrayList<>();
			HashMap<String,Boolean> actives = MonitorResource.getActive();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath());
			Gson gson = new Gson();
			for(File f : dir.listFiles()){
				RSSChannel channel = null;
				FileInputStream fis = null;
				InputStreamReader isr = null;
				try {
					fis = new FileInputStream(f);
					isr = new InputStreamReader(fis, "UTF-8");
					channel = gson.fromJson(isr, RSSChannel.class);

					RSSResume resume = new RSSResume();
					resume.setTitle(channel.getTitle());
					resume.setDescription(channel.getDescription());
					resume.setWebsite(channel.getLink());
					String name = f.getName().substring(0, f.getName().length()-5);
					resume.setLink("/list/"+name);
					String decodedLink = getContainsKey(dates, name);
					if(decodedLink!=null){
						resume.setLastRetrival(dates.get(decodedLink));
						if(actives.get(decodedLink))
							rssList.add(resume);
					}
				} finally {
					if (fis != null) {
						fis.close();
					}
					if (isr != null) {
						isr.close();
					}
				}	
			}
			return rssList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static RSSChannel getRss(String file){
		try{
			File f = new File(jobExecutorConfig.getJsonDirectoryPath()+file+".json");
			Gson gson = new Gson();
			RSSChannel channel = null;
			FileInputStream fis = null;
			InputStreamReader isr = null;
			try {
				fis = new FileInputStream(f);
				isr = new InputStreamReader(fis, "UTF-8");
				channel = gson.fromJson(isr, RSSChannel.class);
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (isr != null) {
					isr.close();
				}
			}	
			return channel;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void setJobExecutorConfig(JobExecutorConfig jobExecutorConfig) {
		GetData.jobExecutorConfig = jobExecutorConfig;
	}

	public static void setMonitorList(MonitorList monitorList) {		
	}

}
