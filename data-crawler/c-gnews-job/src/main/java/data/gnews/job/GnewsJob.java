package data.gnews.job;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import ch.eiafr.tsc.data.global.job.IJob;
import ch.eiafr.tsc.data.global.job.JobExecutor;
import ch.eiafr.tsc.data.global.job.cache.JsonCache;
import data.gnews.driver.GnewsDriver;
import data.gnews.model.GoogleNew;
import data.gnews.model.GoogleNewsModel;

public class GnewsJob implements IJob{
	
	private static Logger logger = Logger.getLogger(JobExecutor.class);

	@Override
	public boolean doProcAndContinue(String param, String cacheDir) {
		try{
			File f = new File(cacheDir+withoutSpecial(param));
			GoogleNewsModel ntu;
			if(f.exists()){
				String json = JsonCache.Read(f.getAbsolutePath());
				ntu = new Gson().fromJson(json, GoogleNewsModel.class);
			}else{
				ntu = new GoogleNewsModel();
				ntu.setKeyword(param);
			}
			List<GoogleNew> newNews = GnewsDriver.search(param);
			for(GoogleNew newNew : newNews){
				if(!ntu.getNews().contains(newNew)){
					ntu.getNews().add(newNew);
				}else{
					GoogleNew oldNew = ntu.getNews().get(ntu.getNews().indexOf(newNew));
					if(!oldNew.getPubDate().equals(newNew.getPubDate())){
						oldNew.setPubDate(newNew.getPubDate());
						for(URL url : newNew.getLinks()){
							if(!oldNew.getLinks().contains(url))
								oldNew.getLinks().add(url);
						}
					}
				}
			}
			String json = new Gson().toJson(ntu);
			JsonCache.Write(f.getAbsolutePath(), json);
		} catch (Exception e) {
			logger.warn("Ending job with error : param = " + param);
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
