package ch.eiafr.tsc.data.rss.rest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.ws.rs.Path;

import org.apache.log4j.BasicConfigurator;

import ch.eia.fr.tsc.data.rss.job.RSSJob;
import ch.eiafr.tsc.global.data.rest.AMonitorResource;

@Path("monitor")
public class MonitorResource extends AMonitorResource{
	private static final long serialVersionUID = -8857025498971117442L;
	
	@Override
	public void init(ServletConfig config) 	throws ServletException{
		BasicConfigurator.configure();
		
		setJob(new RSSJob());

		setModuleName("c-rss-rest");
		
		super.init(config);
		
		GetData.setJobExecutorConfig(jobExecutorConfig);
		GetData.setMonitorList(monitorList);
	}
}
