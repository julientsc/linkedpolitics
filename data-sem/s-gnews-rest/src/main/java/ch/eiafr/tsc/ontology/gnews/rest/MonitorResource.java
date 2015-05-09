package ch.eiafr.tsc.ontology.gnews.rest;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.BasicConfigurator;

import ch.eiafr.tsc.globalnoarg.data.rest.AMonitorResource;
import ch.eiafr.tsc.ontology.gnews.job.Job;

public class MonitorResource extends AMonitorResource {
	private static final long serialVersionUID = -1266761181066506444L;

	@Override
	public void init(ServletConfig config) 	throws ServletException{
		BasicConfigurator.configure();
		
		setJob(new Job());
		super.init(config);
		
		GetData.setJobExecutorConfig(jobExecutorConfig);
		GetData.setMonitorList(monitorList);
	}
}
