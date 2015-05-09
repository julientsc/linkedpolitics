package ch.eiafr.tsc.data.globalnoarg.job;

public class JobExecutorConfig {

	private String cacheDirectoryPath = "/var/cache/semantic/cache/default/";
	private String jsonDirectoryPath = "/var/cache/semantic/cache/default/json/";
	private String logDirectoryPath = "/var/cache/semantic/log/default/";
	private String configurationDirectoryPath = "/var/cache/semantic/config/default/";
	
	public String getCacheDirectoryPath() {
		return cacheDirectoryPath;
	}
	public void setCacheDirectoryPath(String cacheDirectoryPath) {
		this.cacheDirectoryPath = cacheDirectoryPath;
	}
	public String getJsonDirectoryPath() {
		return jsonDirectoryPath;
	}
	public void setJsonDirectoryPath(String jsonDirectoryPath) {
		this.jsonDirectoryPath = jsonDirectoryPath;
	}
	public String getLogDirectoryPath() {
		return logDirectoryPath;
	}
	public void setLogDirectoryPath(String logDirectoryPath) {
		this.logDirectoryPath = logDirectoryPath;
	}
	public String getConfigurationDirectoryPath() {
		return configurationDirectoryPath;
	}
	public void setConfigurationDirectoryPath(String configurationDirectoryPath) {
		this.configurationDirectoryPath = configurationDirectoryPath;
	}	
}
