package ch.eiafr.tsc.data.news20min.rest.fr;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.ws.rs.Path;

import org.apache.log4j.BasicConfigurator;

import ch.eiafr.tsc.data.news20min.job.News20FRJob;
import ch.eiafr.tsc.global.data.rest.AMonitorResource;

@Path("monitor")
public class MonitorResource extends AMonitorResource{
	private static final long serialVersionUID = -1266761181066506763L;

	@Override
	public void init(ServletConfig config) 	throws ServletException{
		BasicConfigurator.configure();
		
		setJob(new News20FRJob());
		setModuleName("c-news20min-fr-rest");
		
		super.init(config);
		
		GetData.setJobExecutorConfig(jobExecutorConfig);
		GetData.setMonitorList(monitorList);
	}
}