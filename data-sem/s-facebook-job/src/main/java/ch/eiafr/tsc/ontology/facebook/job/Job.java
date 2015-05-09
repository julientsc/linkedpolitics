package ch.eiafr.tsc.ontology.facebook.job;

import org.apache.log4j.Logger;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.rio.RDFHandlerException;

import ch.eiafr.tsc.data.globalnoarg.job.IJob;
import ch.eiafr.tsc.data.globalnoarg.job.JobExecutor;
import ch.eiafr.tsc.data.lib.io.Update;
import ch.eiafr.tsc.data.lib.stats.Stats;
import ch.eiafr.tsc.data.facebook.ontology.Sesame;

public class Job implements IJob {
	private static Logger logger = Logger.getLogger(JobExecutor.class);
	@Override
	public boolean doProc(String cacheDir) {
		
		try {
			Sesame s = new Sesame(Sesame.REMOTE);	
			
			Update u = new Update();
			System.out.println("Retrieving LastUpdate from local file");
			s.setLastUpdate(u.getLastUpdateValue(Sesame.serviceName));
			
			Stats.reset();
			
			System.out.println("FACEBOOK ONTOLOGY START");
			s.run();
			System.out.println("FACEBOOK ONTOLOGY END");
			
			u.updateFile(Sesame.serviceName);
			System.out.println("LastUpdate file updated");
			
			Stats.printResult();
		} catch (RepositoryException | RepositoryConfigException e) {
			e.printStackTrace();
		} catch (RDFHandlerException e) {
			e.printStackTrace();
		}	
		
		return false;
	}
}
