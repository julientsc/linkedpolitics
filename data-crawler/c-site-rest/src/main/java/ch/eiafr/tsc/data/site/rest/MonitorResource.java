package ch.eiafr.tsc.data.site.rest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.ws.rs.Path;

import org.apache.log4j.BasicConfigurator;

import ch.eiafr.tsc.data.site.job.SiteJob;
import ch.eiafr.tsc.global.data.rest.AMonitorResource;

@Path("monitor")
public class MonitorResource extends AMonitorResource{
	private static final long serialVersionUID = -1266761181066506763L;

	@Override
	public void init(ServletConfig config) 	throws ServletException{
		BasicConfigurator.configure();
		
		setJob(new SiteJob());

		setModuleName("c-site-rest");
		super.init(config);
		
		
		GetData.setJobExecutorConfig(jobExecutorConfig);
		GetData.setMonitorList(monitorList);
	}
}