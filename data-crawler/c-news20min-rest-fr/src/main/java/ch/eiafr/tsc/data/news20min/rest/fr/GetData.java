package ch.eiafr.tsc.data.news20min.rest.fr;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.global.job.JobExecutorConfig;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;
import ch.eiafr.tsc.data.news20min.model.Article;
import ch.eiafr.tsc.data.news20min.model.ArticleSummary;
import ch.eiafr.tsc.data.news20min.model.KeywordSummary;
import ch.eiafr.tsc.global.data.rest.MonitorList;


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
	
	public static List<KeywordSummary> getList(){
		logger.debug("Start getList");
		try{
			HashMap<String, Date> dates = getOrganizer();
			ArrayList<KeywordSummary> keywords = new ArrayList<>();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath()+"queries");
			if(dir.listFiles()== null)
				return new ArrayList<KeywordSummary>();
			for(File f : dir.listFiles()){
				KeywordSummary kr = new KeywordSummary();
				String keyword = f.getName().substring(0, f.getName().indexOf('.'));
				kr.setParam(keyword);
				kr.setLink("/list/"+keyword);
				kr.setParam(kr.getParam().replaceAll("_"," "));
				String key = null;
				if((key=getContainsKey(dates, kr.getParam()))!=null)
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
	
	public static List<KeywordSummary> getActiveList(){
		logger.debug("Start getActiveList");
		try{
			HashMap<String, Date> dates = getOrganizer();
			HashMap<String,Boolean> actives = MonitorResource.getActive();
			ArrayList<KeywordSummary> keywords = new ArrayList<>();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath()+"queries");
			for(File f : dir.listFiles()){
				String keyword = f.getName().substring(0, f.getName().indexOf('.'));
				if(actives.containsKey(keyword)&&actives.get(keyword)){
					KeywordSummary kr = new KeywordSummary();
					kr.setParam(keyword);
					kr.setLink("/list/"+keyword);
					kr.setParam(kr.getParam().replaceAll("_"," "));
					String key = null;
					if((key=getContainsKey(dates, kr.getParam()))!=null)
						kr.setLastUpdate(dates.get(key));
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
	
	public static ArrayList<ArticleSummary> getArticlesList(String keyword){
		logger.debug("Start getNews");
		try{
			ArrayList<ArticleSummary> articlesSummary = new ArrayList<>();
			SerializeObject<ArrayList<String>> querySerializer = new SerializeObject<ArrayList<String>>();
			SerializeObject<Article> articleSerializer = new SerializeObject<Article>();
			File f = new File(jobExecutorConfig.getJsonDirectoryPath()+ "queries/" +keyword+".FR.bin");
			ArrayList<String> articlesList = querySerializer.read(f);
			for(String s : articlesList){
				ArticleSummary summary = new ArticleSummary();
				summary.setLink20Min(s);
				String fileName = s.replace("://", ".").replace('/', '.').replace('#', '.');
				summary.setLinkRest("/list/"+keyword+"/"+fileName);
				File articleFile = new File(jobExecutorConfig.getJsonDirectoryPath()+"articles/"+fileName);
				System.err.println(articleFile.getAbsolutePath());
				Article article = articleSerializer.read(articleFile);
				if(article!=null){
					summary.setDate(article.getDate());
					summary.setTitle(article.getTitle());
					articlesSummary.add(summary);
				}
			}
			return articlesSummary;
		}catch(Exception e){
			logger.error("Cannot get the news");
			e.printStackTrace();
			return null;
		}
	}
	
	public static Article getArticle(String keyword,String articleLink){
		logger.debug("Start getNews");
		try{
			SerializeObject<Article> articleSerializer = new SerializeObject<Article>();
			File articleFile = new File(jobExecutorConfig.getJsonDirectoryPath()+"articles/"+articleLink);
			Article article = articleSerializer.read(articleFile);
			return article;
		}catch(Exception e){
			logger.error("Cannot get the news");
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