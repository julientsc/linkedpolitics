package data.gnews.rest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.ws.rs.Path;
import org.apache.log4j.BasicConfigurator;

import ch.eiafr.tsc.global.data.rest.AMonitorResource;

import data.gnews.job.GnewsJob;

@Path("monitor")
public class MonitorResource extends AMonitorResource{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 772103064359364350L;

	@Override
	public void init(ServletConfig config) 	throws ServletException{
		BasicConfigurator.configure();
		
		setJob(new GnewsJob());
		setModuleName("c-gnews-rest");
		super.init(config);
		
		
		GetData.setJobExecutorConfig(jobExecutorConfig);
		GetData.setMonitorList(monitorList);
	}
}
