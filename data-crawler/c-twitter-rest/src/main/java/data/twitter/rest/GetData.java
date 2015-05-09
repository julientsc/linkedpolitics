package data.twitter.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.global.job.JobExecutorConfig;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;
import ch.eiafr.tsc.global.data.rest.MonitorList;

import com.google.gson.Gson;

import data.twitter.model.PageData;
import data.twitter.model.StatusData;
import data.twitter.model.TimelineData;
import data.twitter.model.UserInfoData;
import data.twitter.model.UserListData;

public class GetData {
	
private static Logger logger = Logger.getLogger(GetData.class);
	
	private static JobExecutorConfig jobExecutorConfig = null;
	public static final int NB_BY_PAGE = 1000;
	
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

	public static UserInfoData getUserInfoFromId(String id) {
		try {
			HashMap<String, Date> dates = getOrganizer();
			File f = new File(jobExecutorConfig.getJsonDirectoryPath() + "lookup/users/id/" + id);
			Gson gson = new Gson();
			UserInfoData userInfo = null;
			FileInputStream fis = null;
			InputStreamReader isr = null;
			try {
				fis = new FileInputStream(f);
				isr = new InputStreamReader(fis, "UTF-8");
				userInfo = gson.fromJson(isr, UserInfoData.class);
				
				userInfo.updateLink(
						new File(jobExecutorConfig.getJsonDirectoryPath() + "follower/" + id).exists(),
						new File(jobExecutorConfig.getJsonDirectoryPath() + "friend/" + id).exists(), 
						new File(jobExecutorConfig.getJsonDirectoryPath() + "timeline/" + id).exists()
						);
				String key = null;
				if((key=getContainsKey(dates, userInfo.getId()))!=null)
					userInfo.setLastUpdate(dates.get(key));
				if((key=getContainsKey(dates, userInfo.getScreen_name()))!=null)
					userInfo.setLastUpdate(dates.get(key));
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (isr != null) {
					isr.close();
				}
			}
			return userInfo;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static UserInfoData getUserInfoFromScreenName(String screenName) {
		try {
			File f = new File(jobExecutorConfig.getJsonDirectoryPath() + "lookup/users/tmp/" + screenName.toLowerCase());
			Gson gson = new Gson();
			UserInfoData userInfo = null;
			FileInputStream fis = null;
			InputStreamReader isr = null;
			try {
				fis = new FileInputStream(f);
				isr = new InputStreamReader(fis, "UTF-8");
				userInfo = gson.fromJson(isr, UserInfoData.class);
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (isr != null) {
					isr.close();
				}
			}
			return userInfo;
		} catch (Exception e) {
			return null;
		}
	}

	public static ArrayList<UserListData> getUserList() {
		try {
			HashMap<String, Date> dates = getOrganizer();
			
			ArrayList<UserListData> userList = new ArrayList<>();
			Gson gson = new Gson();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath() + "timeline/");
			File[] fList = dir.listFiles();
			for (File f : fList) {
				File f2 = new File(jobExecutorConfig.getJsonDirectoryPath() + "lookup/users/id/"
						+ f.getName());
				FileInputStream fis = null;
				InputStreamReader isr = null;
				try {
					fis = new FileInputStream(f2);
					isr = new InputStreamReader(fis, "UTF-8");
					UserListData uld = gson.fromJson(isr, UserListData.class);
					
					uld.updateLink();
					String key = null;
					if((key=getContainsKey(dates, uld.getId()))!=null)
						uld.setLastUpdate(dates.get(key));
					if((key=getContainsKey(dates, uld.getScreen_name()))!=null)
						uld.setLastUpdate(dates.get(key));
					userList.add(uld);
				} finally {
					if (fis != null) {
						fis.close();
					}
					if (isr != null) {
						isr.close();
					}
				}
			}
			return userList;
		} catch (Exception e) {
			return null;
		}
	}

	public static ArrayList<UserListData> getActiveUserList() {
		try {
			HashMap<String, Date> dates = getOrganizer();
			
			HashMap<String, Boolean> activeUser = MonitorResource.getActive();
			ArrayList<UserListData> userList = new ArrayList<>();
			Gson gson = new Gson();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath() + "timeline/");
			File[] fList = dir.listFiles();
			for (File f : fList) {
				File f2 = new File(jobExecutorConfig.getJsonDirectoryPath() + "lookup/users/id/"
						+ f.getName());
				FileInputStream fis = null;
				InputStreamReader isr = null;
				try {
					fis = new FileInputStream(f2);
					isr = new InputStreamReader(fis, "UTF-8");
					UserListData uld = gson.fromJson(isr, UserListData.class);
					
					if ((activeUser.containsKey(uld.getScreen_name())&&activeUser.get(uld.getScreen_name()))
							|| (activeUser.containsKey(uld.getId())&&activeUser.get(uld.getId()))) {
						uld.updateLink();
						String key = null;
						if((key=getContainsKey(dates, uld.getId()))!=null)
							uld.setLastUpdate(dates.get(key));
						if((key=getContainsKey(dates, uld.getScreen_name()))!=null)
							uld.setLastUpdate(dates.get(key));
						userList.add(uld);
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
			return userList;
		} catch (Exception e) {
			return null;
		}
	}

	public static ArrayList<PageData> getAllUserList() {
		ArrayList<PageData> chars = new ArrayList<>();
		try {
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath() + "lookup/users/id/");
			if(dir.listFiles()==null)
				return new ArrayList<PageData>();
			int nb = dir.listFiles().length;
			int i = 0;
			do {
				i++;
				PageData abc = new PageData();
				abc.setNum(i);
				abc.updateLink();
				chars.add(abc);
			} while (i * NB_BY_PAGE < nb);
			return chars;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<UserListData> getAllUserPage(int pageNum) {
		Gson gson = new Gson();
		ArrayList<UserListData> userList = new ArrayList<>();
		try {
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath() + "lookup/users/id/");
			File[] files = dir.listFiles();
			int nb = files.length;
			for (int i = ((pageNum - 1) * 1000); (i < pageNum * 1000)
					&& (i < nb); i++) {
				File f = files[i];
				FileInputStream fis = null;
				InputStreamReader isr = null;
				try {
					fis = new FileInputStream(f);
					isr = new InputStreamReader(fis, "UTF-8");
					UserListData uld = gson.fromJson(isr, UserListData.class);
					
					uld.updateLink();
					userList.add(uld);
				} finally {
					if (fis != null) {
						fis.close();
					}
					if (isr != null) {
						isr.close();
					}
				}
			}
			return userList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<TimelineData> getTimeline(String userId) {
		try {
			ArrayList<TimelineData> timeline = new ArrayList<>();
			Gson gson = new Gson();
			File dir = new File(jobExecutorConfig.getJsonDirectoryPath() + "timeline/" + userId);
			File[] fList = dir.listFiles();
			for (File f : fList) {
				FileInputStream fis = null;
				InputStreamReader isr = null;
				try {
					fis = new FileInputStream(f);
					isr = new InputStreamReader(fis, "UTF-8");
					TimelineData timelineElm = gson.fromJson(isr, TimelineData.class);
					
					timelineElm.updateLink(userId);
					timeline.add(timelineElm);
				} finally {
					if (fis != null) {
						fis.close();
					}
					if (isr != null) {
						isr.close();
					}
				}
			}
			return timeline;
		} catch (Exception e) {
			return null;
		}
	}

	public static StatusData getStatus(String userId, String statusId) {
		try {
			Gson gson = new Gson();
			File f = new File(jobExecutorConfig.getJsonDirectoryPath() + "timeline/" + userId + "/"
					+ statusId);
			StatusData status = null;
			FileInputStream fis = null;
			InputStreamReader isr = null;
			try {
				fis = new FileInputStream(f);
				isr = new InputStreamReader(fis, "UTF-8");
				status = gson.fromJson(isr, StatusData.class);
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
			return null;
		}
	}

	public static ArrayList<UserListData> getFollowers(String userId) {
		try {
			File f = new File(jobExecutorConfig.getJsonDirectoryPath() + "follower/" + userId);
			SerializeObject<ArrayList<String>> obj = new SerializeObject<>();
			ArrayList<String> followersId = obj.read(f);
			ArrayList<UserListData> userList = new ArrayList<>();
			Gson gson = new Gson();

			for (String id : followersId) {
				
				File followerFile = new File(jobExecutorConfig.getJsonDirectoryPath() + "lookup/users/id/"
						+ id);
				if (!followerFile.exists())
					continue;
				
				FileInputStream fis = null;
				InputStreamReader isr = null;
				try {
					fis = new FileInputStream(followerFile);
					isr = new InputStreamReader(fis, "UTF-8");
					UserListData uld = gson.fromJson(isr, UserListData.class);
					
					uld.updateLink();
					userList.add(uld);
				} finally {
					if (fis != null) {
						fis.close();
					}
					if (isr != null) {
						isr.close();
					}
				}
			}
			return userList;
		} catch (Exception e) {
			return null;
		}
	}

	public static ArrayList<UserListData> getFriends(String userId) {
		try {
			File f = new File(jobExecutorConfig.getJsonDirectoryPath() + "friend/" + userId);
			SerializeObject<ArrayList<String>> obj = new SerializeObject<>();
			ArrayList<String> friendsId = obj.read(f);
			ArrayList<UserListData> userList = new ArrayList<>();
			Gson gson = new Gson();
			for (String id : friendsId) {
				File friendFile = new File(jobExecutorConfig.getJsonDirectoryPath() + "lookup/users/id/"
						+ id);
				if (!friendFile.exists())
					continue;

				FileInputStream fis = null;
				InputStreamReader isr = null;
				try {
					fis = new FileInputStream(friendFile);
					isr = new InputStreamReader(fis, "UTF-8");
					UserListData uld = gson.fromJson(isr, UserListData.class);
					
					uld.updateLink();
					userList.add(uld);
				} finally {
					if (fis != null) {
						fis.close();
					}
					if (isr != null) {
						isr.close();
					}
				}
			}
			return userList;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getIdFromUsername(String username){
		UserInfoData uid = getUserInfoFromScreenName(username);
		if(uid==null)
			return "-";
		return uid.getId();
	}
	
	public static String getScreenNameFromId(String id){
		UserInfoData uid = getUserInfoFromId(id);
		if(uid==null)
			return "-";
		return uid.getScreen_name();
	}
	
	public static void setJobExecutorConfig(JobExecutorConfig jobExecutorConfig) {
		GetData.jobExecutorConfig = jobExecutorConfig;
	}

	public static void setMonitorList(MonitorList monitorList) {
	}
}
