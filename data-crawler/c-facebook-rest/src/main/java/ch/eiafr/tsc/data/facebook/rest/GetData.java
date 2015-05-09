package ch.eiafr.tsc.data.facebook.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.facebook.model.Comment;
import ch.eiafr.tsc.data.facebook.model.From;
import ch.eiafr.tsc.data.facebook.model.PageListData;
import ch.eiafr.tsc.data.facebook.model.PageToShow;
import ch.eiafr.tsc.data.facebook.model.PostToShow;
import ch.eiafr.tsc.data.facebook.model.PostsData;
import ch.eiafr.tsc.data.facebook.rest.resources.MonitorResource;
import ch.eiafr.tsc.data.global.job.JobExecutorConfig;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;
import ch.eiafr.tsc.global.data.rest.MonitorList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetData{
private static Logger logger = Logger.getLogger(GetData.class);
	
	private static JobExecutorConfig jobExecutorConfig = null;
	private static HashMap<String, Date> getOrganizer() {
		SerializeObject<HashMap<String, Date>> obj = new SerializeObject<HashMap<String, Date>>();
		HashMap<String, Date> lastNew = obj.read(new File(jobExecutorConfig.getOrganiserFilePath()));
		return lastNew;
	}
	
	public static FilenameFilter postsFilter = new FilenameFilter() {
        public boolean accept(File directory, String fileName) {
            return !fileName.contains(".");
        }
    };
	
	
	public static PageToShow getPageFromId(String id) {
		try {
			File f = new File(jobExecutorConfig.getJsonDirectoryPath() + "pages/" + id);
			Gson gson = new Gson();
			PageToShow pageToShow = null;
			FileInputStream fis = null;
			InputStreamReader isr = null;
			try {
				fis = new FileInputStream(f);
				isr = new InputStreamReader(fis, "UTF-8");
				pageToShow = gson.fromJson(isr, PageToShow.class);
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (isr != null) {
					isr.close();
				}
			}
			return pageToShow;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static PageToShow getPageFromScreenName(String screenName) {
		try {
			File f = new File(jobExecutorConfig.getCacheDirectoryPath() + "lookup/pages/tmp/" + screenName.toLowerCase());
			Gson gson = new Gson();
			PageToShow pageToShow = null;
			FileInputStream fis = null;
			InputStreamReader isr = null;
			try {
				fis = new FileInputStream(f);
				isr = new InputStreamReader(fis, "UTF-8");
				pageToShow = gson.fromJson(isr, PageToShow.class);
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (isr != null) {
					isr.close();
				}
			}
			return pageToShow;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static ArrayList<PageListData> getPageList() {
		try {
			HashMap<String, Date> dates = getOrganizer();
			
			ArrayList<PageListData> pageList = new ArrayList<>();
			Gson gson = new Gson();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath() + "posts/");
			File[] fList = dir.listFiles();
			if(fList==null)
				return new ArrayList<PageListData>();
			for (File f : fList) {
				File f2 = new File(jobExecutorConfig.getJsonDirectoryPath() + "pages/"
						+ f.getName());
				if(f2.exists()){
					PageListData pld = null;
					FileInputStream fis = null;
					InputStreamReader isr = null;
					try {
						fis = new FileInputStream(f2);
						isr = new InputStreamReader(fis, "UTF-8");
						pld = gson.fromJson(isr, PageListData.class);
						pld.updateLink();
						if(dates.containsKey(pld.getId()))
							pld.setLastUpdate(dates.get(pld.getId()).getTime());
						if(dates.containsKey(pld.getUsername()))
							pld.setLastUpdate(dates.get(pld.getUsername()).getTime());
						pageList.add(pld);
					} finally {
						if (fis != null) {
							fis.close();
						}
						if (isr != null) {
							isr.close();
						}
					}
				}
			}
			return pageList;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}
	
	
	public static ArrayList<PageListData> getActivePageList() {
		try {
			HashMap<String, Date> dates = getOrganizer();
			
			HashMap<String,Boolean> activePages = MonitorResource.getActive();
			
			ArrayList<PageListData> pageList = new ArrayList<>();
			Gson gson = new Gson();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath() + "posts/");
			File[] fList = dir.listFiles();
			for (File f : fList) {
				File f2 = new File(jobExecutorConfig.getJsonDirectoryPath() + "pages/"
						+ f.getName());
				if(f2.exists()){
					PageListData pld = null;
					FileInputStream fis = null;
					InputStreamReader isr = null;
					try {
						fis = new FileInputStream(f2);
						isr = new InputStreamReader(fis, "UTF-8");
						pld = gson.fromJson(isr, PageListData.class);
						if ((activePages.containsKey(pld.getId())&&activePages.get(pld.getId()))
								|| (activePages.containsKey(pld.getUsername())&&activePages.get(pld.getUsername()))) {
							pld.updateLink();
							if(dates.containsKey(pld.getId()))
								pld.setLastUpdate(dates.get(pld.getId()).getTime());
							if(dates.containsKey(pld.getUsername()))
								pld.setLastUpdate(dates.get(pld.getUsername()).getTime());
							pageList.add(pld);
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
			}
			return pageList;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static ArrayList<PostsData> getPosts(String pageId) {
		try {
			ArrayList<PostsData> posts = new ArrayList<>();
			Gson gson = new Gson();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath() + "posts/" + pageId);
			for (File f : dir.listFiles(postsFilter)) {
				PostsData postElm = null;
				FileInputStream fis = null;
				InputStreamReader isr = null;
				try {
					fis = new FileInputStream(f);
					isr = new InputStreamReader(fis, "UTF-8");
					postElm = gson.fromJson(isr, PostsData.class);
					postElm.updateLink(pageId);
					posts.add(postElm);
				} finally {
					if (fis != null) {
						fis.close();
					}
					if (isr != null) {
						isr.close();
					}
				}
			}
			return posts;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static PostToShow getPost(String pageId, String postId) {
		try {
			Gson gson = new Gson();
			File f = new File(jobExecutorConfig.getJsonDirectoryPath() + "posts"
					+ "/" + pageId + "/"
					+ postId);
			
			PostToShow status = null;
			FileInputStream fis = null;
			InputStreamReader isr = null;
			try {
				fis = new FileInputStream(f);
				isr = new InputStreamReader(fis, "UTF-8");
				status = gson.fromJson(isr, PostToShow.class);
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (isr != null) {
					isr.close();
				}
			}
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<From> getLikes(String pageId, String postId) {
		try {
			File f = new File(jobExecutorConfig.getJsonDirectoryPath() + "posts"
					+ "/" + pageId + "/"
					+ postId+".like");
			Gson gson = new Gson();
			ArrayList<From> likes = null;
			FileInputStream fis = null;
			InputStreamReader isr = null;
			try {
				fis = new FileInputStream(f);
				isr = new InputStreamReader(fis, "UTF-8");
				likes = gson.fromJson(isr, new TypeToken<ArrayList<From>>() {}.getType());
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (isr != null) {
					isr.close();
				}
			}
			return likes;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static ArrayList<Comment> getComments(String pageId, String postId) {
		try {
			File f = new File(jobExecutorConfig.getJsonDirectoryPath() + "posts"
					+ "/" + pageId + "/"
					+ postId+".comment");
			Gson gson = new Gson();
			ArrayList<Comment> comments = null;
			FileInputStream fis = null;
			InputStreamReader isr = null;
			try {
				fis = new FileInputStream(f);
				isr = new InputStreamReader(fis, "UTF-8");
				comments = gson.fromJson(isr, new TypeToken<ArrayList<Comment>>() {}.getType());
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (isr != null) {
					isr.close();
				}
			}
			return comments;
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
