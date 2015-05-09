package ch.eiafr.tsc.global.data.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import ch.eiafr.tsc.data.global.job.cache.SerializeObject;

public class MonitorList {

	private ArrayList<String> links;
	private int timer;
	private HashMap<String,Integer> nb;
	private File linksFile;
	private File timmerFile;
	private File nbFile;

	public MonitorList(String configDir) {
		linksFile = new File(configDir + "params");
		timmerFile = new File(configDir + "timer");
		nbFile = new File(configDir + "nb");
		links = new ArrayList<>();
		nb = new HashMap<>();
		timer = 1800; //TODO change to 1800
		loadList();
		loadTimer();
		loadNb();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> getParams() {
		return (ArrayList<String>) links.clone();
	}

	public ArrayList<String> add(String s) {
		if (!links.contains(s)){
			links.add(s);
			nb.put(s, 1);
		}else{
			nb.put(s, nb.get(s)+1);
		}
		saveList();
		saveNb();
		return links;
	}

	public ArrayList<String> remove(String s) {
		if (links.contains(s)){
			nb.put(s, nb.get(s)-1);
			if(nb.get(s)==0){
				links.remove(s);
				nb.remove(s);
			}
		}
		saveList();
		saveNb();
		return links;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
		saveTimer();
	}

	private void loadList() {
		try {
			// read link list file
			if (linksFile.exists()) {
				BufferedReader br = new BufferedReader(
						new FileReader(linksFile));
				String line;
				while ((line = br.readLine()) != null) {
					links.add(line);
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void saveList() {
		try {
			PrintWriter writer = new PrintWriter(linksFile, "UTF-8");
			for (String s : links) {
				writer.println(s);
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadTimer() {
		try {
			// read link list file
			if (timmerFile.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(
						timmerFile));
				String line;
				if ((line = br.readLine()) != null) {
					timer = Integer.valueOf(line);
				}
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveTimer() {
		try {
			PrintWriter writer = new PrintWriter(timmerFile, "UTF-8");
			writer.println(timer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadNb() {
		SerializeObject<HashMap<String,Integer>> serialize = new SerializeObject<>();
		nb = serialize.read(nbFile);
		if(nb==null){
			nb=new HashMap<>();
			for(String s : links){
				nb.put(s, 1);
			}
		}
		
	}
	
	private void saveNb(){
		SerializeObject<HashMap<String,Integer>> serialize = new SerializeObject<>();
		serialize.write(nb, nbFile);
	}

}
