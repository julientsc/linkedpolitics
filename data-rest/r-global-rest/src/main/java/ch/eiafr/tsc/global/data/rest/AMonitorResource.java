package ch.eiafr.tsc.global.data.rest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import ch.eiafr.tsc.data.global.job.IJob;
import ch.eiafr.tsc.data.global.job.JobExecutor;
import ch.eiafr.tsc.data.global.job.JobExecutorConfig;
import ch.eiafr.tsc.data.global.job.cache.SerializeObject;

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

	@Path("param")
	@PUT
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response put(@FormParam("value") String value) throws IOException {
		if (value == null) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
		value = withoutSpecial(value);
		monitorList.add(value);
		monitorTask.setList(monitorList.getParams());
		return Response.ok(value).build();
	}

	@Path("param")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response delete(@QueryParam("value") String value)
			throws IOException {
		value = withoutSpecial(value);
		monitorList.remove(value);
		monitorTask.setList(monitorList.getParams());
		return Response.ok(value).build();
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

	@Path("param")
	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf8")
	public Response getList() throws IOException {
		ArrayList<String> params = monitorList.getParams();

		HashMap<String, Date> dates = getOrganizer();
		HashMap<String, Boolean> actives = getActive();

		ArrayList<MonitorData> v = new ArrayList<MonitorData>();

		for (String s : params) {
			MonitorData md = new MonitorData();
			md.setParam(s);
			if (dates.containsKey(s))
				md.setLastUpdate(dates.get(s));
			else
				md.setLastUpdate(new Date(0));

			if (actives.containsKey(s))
				md.setActive(actives.get(s));
			else
				md.setActive(true);

			v.add(md);
		}

		ArrayList<MonitorData> monitorDataList = v; // displayData.fill(params,
													// dates, actives);
		return Response.ok(gson.toJson(monitorDataList)).build();
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
	
	private String moduleName = "NotSet";

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void init(ServletConfig config) throws ServletException {
	
		if (!initialized) {
			logger.debug("{"+ moduleName +"} Init servlet");

			Properties prop = new Properties();
			try {
				logger.debug("{"+ moduleName +"} Try to read data property file <" + CONFIG_FILE
						+ ">");
				prop.load(AMonitorResource.class.getClassLoader()
						.getResourceAsStream(CONFIG_FILE));
				logger.info("Success reading data property file <"
						+ CONFIG_FILE + ">");

				logger.debug("{"+ moduleName +"} Create the job config executor");
				jobExecutorConfig = new JobExecutorConfig();
				jobExecutorConfig.setCacheDirectoryPath(prop
						.getProperty("CACHE_DIR"));
				jobExecutorConfig.setConfigurationDirectoryPath(prop
						.getProperty("CONFIG_DIR"));
				jobExecutorConfig.setJsonDirectoryPath(prop
						.getProperty("JSON_DIR"));
				jobExecutorConfig.setLogDirectoryPath(prop
						.getProperty("LOG_DIR"));
				jobExecutorConfig.setOrganiserFileName(prop
						.getProperty("LAST_NEWS_FILE"));
				jobExecutorConfig.setActiveFileName(prop
						.getProperty("ACTIVE_FILE"));

				organiserFile = new File(
						jobExecutorConfig.getOrganiserFilePath());
				activeFile = new File(jobExecutorConfig.getActiveFilePath());

				logger.debug("{"+ moduleName +"} Create the job executor");
				JobExecutor jobExecutor = new JobExecutor(job,
						jobExecutorConfig);

				jobExecutor.setModuleName(moduleName);
				
				monitorList = new MonitorList(
						jobExecutorConfig.getConfigurationDirectoryPath());

				logger.debug("{"+ moduleName +"} Start the job executor with params");
				// jobExecutor.doProc(monitorList.getParams());
				logger.info("{"+ moduleName +"} Success starting Job executor");

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
		logger.debug("{"+ moduleName +"} Destroy the servlet");
	}

	public JobExecutorConfig getJobExecutorConfig() {
		return jobExecutorConfig;
	}

	public MonitorList getMonitorList() {
		return monitorList;
	}

	
	private static String withoutSpecial(String s){
		if(s.matches("^https?://.*"))
			return s.replace("é","%C3%A9").
					replace("è","%C3%A8").
					replace("ë","%C3%AB").
					replace("ê","%C3%AA").
					replace("à","%C3%A0").
					replace("ä","%C3%A4").
					replace("â","%C3%A2").
					replace("ö","%C3%B6").
					replace("ô","%C3%B4").
					replace("ü","%C3%BC").
					replace("û","%C3%BB").
					replace("ï","%C3%AF").
					replace("î","%C3%AE").
					replace("ç","%C3%A7");
		else
			return s.replace("é","e").
					replace("è","e").
					replace("ë","e").
					replace("ê","e").
					replace("à","a").
					replace("ä","a").
					replace("â","a").
					replace("ö","o").
					replace("ô","o").
					replace("ü","u").
					replace("û","u").
					replace("ï","i").
					replace("î","i").
					replace("ç","c");
	}
}
