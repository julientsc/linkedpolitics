package ch.eiafr.tsc.data.facebook.test;

import java.util.Date;


public class Tests {

	public static void main(String[] args) {
		
		Tests t = new Tests();
		
		t.timestamp(1422643938, "Post of page A"); // post
		t.timestamp(1422661570, "Page A"); // page related
		
		t.timestamp(1422264063, "Other page"); // page
		
		t.timestamp(1424256873, "now"); // now
		
		t.timestamp(1422977491, "page nr.evi.allemann");
		t.timestamp(1329657953, "post from nr.ev.all..");
		t.timestamp(1322999583, "other post from nr.ev.");
		System.out.println("--> donc la date d'update des posts ne correspond pas à une date de fetching");
	}
	
	public void timestamp(long ts, String comment) {
		// timestamp to Date
		Date d = new Date(ts*1000);
		
		System.out.println(comment + ":\t\t" + d.toString());
	}

}
