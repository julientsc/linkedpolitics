package data.twitter.rest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.ws.rs.Path;
import org.apache.log4j.BasicConfigurator;
import ch.eiafr.tsc.global.data.rest.AMonitorResource;

import data.twitter.job.TwitterJob;

@Path("monitor")
public class MonitorResource extends AMonitorResource {

	private static final long serialVersionUID = -7362107763256872791L;

	@Override
	public void init(ServletConfig config) 	throws ServletException{
		BasicConfigurator.configure();
		
		setJob(new TwitterJob());
		setModuleName("c-twitter-rest");
		super.init(config);
		
		
		GetData.setJobExecutorConfig(jobExecutorConfig);
		GetData.setMonitorList(monitorList);
	}
}
