package ch.eiafr.tsc.globalnoarg.data.rest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.global.job.cache.SerializeObject;
import ch.eiafr.tsc.data.globalnoarg.job.IJob;
import ch.eiafr.tsc.data.globalnoarg.job.JobExecutor;
import ch.eiafr.tsc.data.globalnoarg.job.JobExecutorConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AMonitorResource extends HttpServlet {
	private static final long serialVersionUID = 8031238469528586501L;

	private static Logger logger = Logger.getLogger(AMonitorResource.class);

	private static final String CONFIG_FILE = "config.properties";

	private static File organiserFile = null;
	private static File activeFile = null;

	protected static JobExecutorConfig jobExecutorConfig;
	protected static MonitorList monitorList = null;

	private static MonitorTask monitorTask = null;
	private static boolean initialized = false;
	private static Gson gson = new GsonBuilder().setPrettyPrinting()
			.disableHtmlEscaping().create();

	private static IJob job = null;

	public void setJob(IJob job) {
		AMonitorResource.job = job;
	}


	public HashMap<String, Date> getOrganizer() {
		SerializeObject<HashMap<String, Date>> obj = new SerializeObject<HashMap<String, Date>>();
		HashMap<String, Date> lastNew = obj.read(organiserFile);
		if(lastNew==null)
			return new HashMap<String, Date>();
		
		return lastNew;
	}

	public static HashMap<String, Boolean> getActive() {
		SerializeObject<HashMap<String, Boolean>> obj = new SerializeObject<HashMap<String, Boolean>>();
		HashMap<String, Boolean> active = obj.read(activeFile);
		if (active != null)
			return active;
		return new HashMap<String, Boolean>();
	}
	
	public void saveActive(HashMap<String, Boolean> active) {
		SerializeObject<HashMap<String, Boolean>> obj = new SerializeObject<HashMap<String, Boolean>>();
		obj.write(active, activeFile);
	}


	@Path("timer")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response getTimmer() throws IOException {
		int timmer = monitorTask.getTimer();
		return Response.ok(gson.toJson(timmer)).build();
	}

	@Path("timer")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response setTimer(@FormParam("time") int time) throws IOException {
		monitorList.setTimer(time);
		monitorTask.setTimer(time);
		return Response.ok().build();
	}

	@Path("status")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response getStatus() throws IOException {
		boolean status = monitorTask.isEnabled();
		return Response.ok(status ? "Enabled" : "Disabled").build();
	}

	@Path("status")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response setStatus(@FormParam("status") String status)
			throws IOException {
		if (status.toLowerCase().equals("enable") && !monitorTask.isEnabled())
			monitorTask.start();
		else if (status.toLowerCase().equals("disable")
				&& monitorTask.isEnabled())
			monitorTask.stop();
		return Response.ok().build();
	}
	
	@Path("active")
	@POST
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response activate(@FormParam("value") String value,@FormParam("state") String state) throws IOException {
		HashMap<String, Boolean> active = getActive();
		active.put(value, state.equals("true")?true:false);
		saveActive(active);
		return Response.ok().build();
	}

	@Path("forcestart")
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response forceStart() throws IOException {
		monitorTask.forceStart();
		return Response.ok().build();
	}

	@Path("lastexec")
	@GET
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response lastExec() throws IOException {
		return Response.ok(monitorTask.getLastExec() + "").build();
	}

	@Path("nextexec")
	@GET
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response nextExec() throws IOException {
		if (monitorTask.isRunning())
			return Response.ok("Now").build();
		if (monitorTask.isEnabled())
			return Response.ok(
					new Date(monitorTask.getLastExec().getTime()
							+ monitorList.getTimer() * 1000)
							+ "").build();
		return Response.ok("?").build();
	}
	

	public void init(ServletConfig config) throws ServletException {
		if (!initialized) {
			logger.debug("Init servlet");

			Properties prop = new Properties();
			try {
				logger.debug("Try to read data property file <" + CONFIG_FILE
						+ ">");
				prop.load(AMonitorResource.class.getClassLoader()
						.getResourceAsStream(CONFIG_FILE));
				logger.info("Success reading data property file <"
						+ CONFIG_FILE + ">");

				logger.debug("Create the job config executor");
				jobExecutorConfig = new JobExecutorConfig();
				jobExecutorConfig.setCacheDirectoryPath(prop
						.getProperty("CACHE_DIR"));
				jobExecutorConfig.setConfigurationDirectoryPath(prop
						.getProperty("CONFIG_DIR"));
				jobExecutorConfig.setJsonDirectoryPath(prop
						.getProperty("JSON_DIR"));
				jobExecutorConfig.setLogDirectoryPath(prop
						.getProperty("LOG_DIR"));

				logger.debug("Create the job executor");
				JobExecutor jobExecutor = new JobExecutor(job,
						jobExecutorConfig);

				monitorList = new MonitorList(
						jobExecutorConfig.getConfigurationDirectoryPath());

				logger.debug("Start the job executor with params");
				// jobExecutor.doProc(monitorList.getParams());
				logger.info("Success starting Job executor");

				monitorTask = new MonitorTask(monitorList, jobExecutor);
				monitorTask.start();
				initialized = true;

			} catch (IOException e) {
				monitorTask.stop();
				logger.fatal("Error at the servlet initialization and destroy the servlet");
				e.printStackTrace();
			}
		}
	}

	public void destroy() {
		monitorTask.stop();
		logger.debug("Destroy the servlet");
	}

	public JobExecutorConfig getJobExecutorConfig() {
		return jobExecutorConfig;
	}

	public MonitorList getMonitorList() {
		return monitorList;
	}

	
	private static String withoutSpecial(String s){
		if(s.matches("^https?://.*"))
			return s.replace("�","%C3%A9").
					replace("�","%C3%A8").
					replace("�","%C3%AB").
					replace("�","%C3%AA").
					replace("�","%C3%A0").
					replace("�","%C3%A4").
					replace("�","%C3%A2").
					replace("�","%C3%B6").
					replace("�","%C3%B4").
					replace("�","%C3%BC").
					replace("�","%C3%BB").
					replace("�","%C3%AF").
					replace("�","%C3%AE").
					replace("�","%C3%A7");
		else
			return s.replace("�","e").
					replace("�","e").
					replace("�","e").
					replace("�","e").
					replace("�","a").
					replace("�","a").
					replace("�","a").
					replace("�","o").
					replace("�","o").
					replace("�","u").
					replace("�","u").
					replace("�","i").
					replace("�","i").
					replace("�","c");
	}
}
