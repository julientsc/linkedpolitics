package ch.eiafr.tsc.data.bing.job;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ch.eiafr.tsc.data.bing.driver.search.BingSearch;
import ch.eiafr.tsc.data.bing.model.BingResult;
import ch.eiafr.tsc.data.bing.model.BingResultModel;
import ch.eiafr.tsc.data.global.job.IJob;
import ch.eiafr.tsc.data.global.job.JobExecutor;
import ch.eiafr.tsc.data.global.job.cache.JsonCache;

public class BingJob implements IJob {

	private static Logger logger = Logger.getLogger(JobExecutor.class);

	@Override
	public boolean doProcAndContinue(String param, String cacheDir) {
		try{
			List<BingResult> results = BingSearch.search(param);
			File keywordDir = new File(cacheDir+"keywords/"+withoutSpecial(param));
			BingResultModel brm = new BingResultModel();
			brm.setKeyword(param);
			if(keywordDir.exists()){
				File[] listFile = keywordDir.listFiles();
				Arrays.sort(listFile);
				if(listFile.length>0){
					brm = new Gson().fromJson(JsonCache.Read(listFile[listFile.length-1].getAbsolutePath()), BingResultModel.class);
				}
			}else{
				keywordDir.mkdirs();
			}
			
			File resultsDir = new File(cacheDir+"results");
			if(!resultsDir.exists()){
				resultsDir.mkdirs();
			}
			
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			for(BingResult br : results){
				if(!brm.getIDs().contains(br.getId())){
					brm.getIDs().add(br.getId());
					br.setDate(new Date());
					String brJson = gson.toJson(br);
					JsonCache.Write(cacheDir+"results/"+br.getId(), brJson);
				}
			}
	
			
			
			String json = gson.toJson(brm);
			
			JsonCache.Write(cacheDir+"keywords/"+withoutSpecial(param) +"/"+ new Date().getTime(), json);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void doProcAndStop(String param, String cacheDir) throws Exception {

	}
	
	private static String withoutSpecial(String s){
		return s.replace(" ", "_").replace("/", "_");
	}

}
