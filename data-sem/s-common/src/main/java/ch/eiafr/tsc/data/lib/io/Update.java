package ch.eiafr.tsc.data.lib.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Update {

	static String filename = "lastUpdate.json";
	
	static String VALUE = "value";
	static String TYPE = "type";
	static String UNIT = "unit";
	
	public static void main(String[] args) {
		
		// This is how to use this class :
		
		Update u = new Update();
		System.out.println("Last update : " + u.getLastUpdateValue("gnews"));
		
		u.updateFile("gnews");
		System.out.println("lastUpdate.json is up to date");
		
		System.out.println("This update : " + u.getLastUpdateValue("gnews"));
		System.out.println("Type : " + u.getLastUpdateType("gnews"));
		System.out.println("Unit : " + u.getLastUpdateUnit("gnews"));
	}

	@SuppressWarnings("unchecked")
	public boolean updateFile(String service) {
		
		Date update = new Date();
		
		JSONObject array = new JSONObject();
		array.put("type", "timestamp");
		array.put("value", update.getTime());
		array.put("unit", "ms");
		
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			// the file already exists, we update it
			Object rawFile = parser.parse(new FileReader(filename));
			obj = (JSONObject) rawFile;
			JSONObject lastUpdate = (JSONObject) obj.get("lastUpdate");
			
			lastUpdate.put(service, array);
			obj.put("lastUpdate", lastUpdate);
		} catch (FileNotFoundException e) {
			// the file doesn't exit, we build one
			obj = new JSONObject();
			JSONObject lastUpdate = new JSONObject();
			lastUpdate.put(service, array);
			obj.put("lastUpdate", lastUpdate);
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
		
		// write file
		try {
			File file = new File(filename);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(obj.toString());
			output.close();
        } catch (IOException e) {
        	e.printStackTrace();
        	return false;
        }
		return true;
	}
	
	public long getLastUpdateValue(String service) {
		JSONObject lastUpdate = getJSON(service);
		long value = 0;
		if (lastUpdate != null) {
        	value = (long) lastUpdate.get(VALUE);
        } 
		return value;
	}
	
	public String getLastUpdateType(String service) {
		JSONObject lastUpdate = getJSON(service);
		String type = null;
		if (lastUpdate != null) {
			type = (String) lastUpdate.get(TYPE);
        } 
		return type;
	}
	
	public String getLastUpdateUnit(String service) {
		JSONObject lastUpdate = getJSON(service);
		String unit = null;
		if (lastUpdate != null) {
			unit = (String) lastUpdate.get(UNIT);
        } 
		return unit;
	}
	
	private JSONObject getJSON(String service) {
		JSONParser parser = new JSONParser();
		
        try {     
            Object obj1 = parser.parse(new FileReader(filename));

            JSONObject jsonObject =  (JSONObject) obj1;

            JSONObject lastUpdate = (JSONObject) jsonObject.get("lastUpdate");
            
            JSONObject specificUpdate = (JSONObject) lastUpdate.get(service);
            return specificUpdate;

        } catch (FileNotFoundException e) {
        	// file not found = it's the first time we launch the program
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } 
		return null;
	}
}