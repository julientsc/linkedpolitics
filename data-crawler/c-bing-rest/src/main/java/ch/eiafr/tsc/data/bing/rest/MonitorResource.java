package ch.eiafr.tsc.data.bing.rest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.ws.rs.Path;

import org.apache.log4j.BasicConfigurator;

import ch.eiafr.tsc.data.bing.job.BingJob;
import ch.eiafr.tsc.global.data.rest.AMonitorResource;


@Path("monitor")
public class MonitorResource extends AMonitorResource{
	private static final long serialVersionUID = 1929738003231678873L;
	
	@Override
	public void init(ServletConfig config) 	throws ServletException{
		BasicConfigurator.configure();
		setModuleName("c-bing-rest");
		
		setJob(new BingJob());
		super.init(config);
		
		
		GetData.setJobExecutorConfig(jobExecutorConfig);
		GetData.setMonitorList(monitorList);
	}
}