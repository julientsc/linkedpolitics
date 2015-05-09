import java.io.IOException;

import javax.servlet.ServletException;

import ch.eiafr.tsc.globalnoarg.data.rest.AMonitorResource;
import ch.eiafr.tsc.ontology.facebook.rest.MonitorResource;


public class Test {

	public static void main(String[] args) {
		AMonitorResource monitor = new MonitorResource();
		try {
			monitor.init(null);
			Thread.sleep(2000);
			monitor.forceStart();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
