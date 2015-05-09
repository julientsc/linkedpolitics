package ch.eiafr.tsc.data.global.job;

public class JobExecutorConfig {

	private String cacheDirectoryPath = "/var/cache/semantic/cache/default/";
	private String jsonDirectoryPath = "/var/cache/semantic/cache/default/json/";
	private String logDirectoryPath = "/var/cache/semantic/log/default/";
	private String configurationDirectoryPath = "/var/cache/semantic/config/default/";
	
	private String organiserFilePath = "organizer.bin";
	private String activeFilePath = "active.bin";
	
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
	public String getOrganiserFilePath() {
		return configurationDirectoryPath + "/" + organiserFilePath;
	}
	public void setOrganiserFileName(String organiserFilePath) {
		this.organiserFilePath = organiserFilePath;
	}
	public String getActiveFilePath() {
		return configurationDirectoryPath + "/" + activeFilePath;
	}
	public void setActiveFileName(String activeFilePath) {
		this.activeFilePath = activeFilePath;
	}
	
}
