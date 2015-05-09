package ch.eiafr.tsc.data.global.job;


public interface IJob {

	public boolean doProcAndContinue(String param, String cacheDir);
	
	public void doProcAndStop(String param, String cacheDir) throws Exception;

}
