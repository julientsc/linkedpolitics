package ch.eiafr.tsc.data.rss.ontology;

import info.aduna.iteration.Iterations;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.manager.RemoteRepositoryManager;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.Rio;
import org.openrdf.sail.memory.MemoryStore;

import ch.eiafr.tsc.data.lib.io.FileManager;
import ch.eiafr.tsc.data.lib.io.Update;
import ch.eiafr.tsc.data.lib.stats.Stats;
import ch.eiafr.tsc.data.rss.model.RSSChannel;
import ch.eiafr.tsc.data.rss.model.RSSResume;

public class Sesame {
	public static final int LOCAL = 0;
	public static final int REMOTE = 1;
	
	private long lastUpdate = 0;
	
	private String APIurl = "http://semantic.ilab-research.ch:8080/c-rss-rest/webapi";
	public static String serviceName = "rss";
	
	private Repository rep;
	private RepositoryConnection conn;
	private ValueFactory f;
	private String namespace;
	
	private final String RSS_CHANNEL = "subject/RSSChannel";
	private final String SUMMARY = "subject/Summary";
	private final String RSS_ITEM = "subject/RSSItem";
	
	private final String ITEM = "predicate/hasItem";
	private final String TITLE = "predicate/hasTitle";
	private final String SOURCE = "predicate/hasSource";
	private final String DESCRIPTION = "predicate/hasDescription";
	private final String DATE = "predicate/hasDate";
	private final String LINK = "predicate/hasLink";
	private final String WEBSITE = "predicate/hasWebsite";
	
	private final URI rssChannel;
	private final URI summary;
	private final URI rssItem;
	
	private final URI hasItem;
	private final URI hasTitle;
	private final URI hasSource;
	private final URI hasDescription;
	private final URI hasDate;
	private final URI hasLink;
	private final URI hasWebsite;
	
	public static void main(String[] args) throws RepositoryException, RDFHandlerException, RepositoryConfigException {
		//Sesame s = new Sesame(Sesame.LOCAL);
		Sesame s = new Sesame(Sesame.REMOTE);	
		
		Update u = new Update();
		System.out.println("Retrieving LastUpdate from local file");
		s.setLastUpdate(u.getLastUpdateValue(serviceName));
		
		Stats.reset();
		
		System.out.println("RSS ONTOLOGY START");
		s.run();
		System.out.println("RSS ONTOLOGY END");
		
		u.updateFile(serviceName);
		System.out.println("LastUpdate file updated");
		
		Stats.printResult();
	}
	
	public Sesame(int mode) throws RepositoryException, RepositoryConfigException {
		if (mode == REMOTE) {
			// Remote DB connection
			String serverUrl = "http://semantic.ilab-research.ch:8080/openrdf-sesame/";
			RemoteRepositoryManager manager = new RemoteRepositoryManager(serverUrl);
			manager.initialize();
			rep = manager.getRepository("prod-rss");
			f = rep.getValueFactory();
			conn = rep.getConnection();
			
		} else {
			// Local memory storage
			rep = new SailRepository(new MemoryStore());
			rep.initialize();
			f = rep.getValueFactory();
			conn = rep.getConnection();
		}
		
		namespace = "http://eia-fr-ontology.ch/";
		
		rssChannel = f.createURI(namespace, RSS_CHANNEL);
		summary = f.createURI(namespace, SUMMARY);
		rssItem = f.createURI(namespace, RSS_ITEM);
		
		hasItem = f.createURI(namespace, ITEM);
		hasTitle = f.createURI(namespace, TITLE);
		hasSource = f.createURI(namespace, SOURCE);
		hasDescription = f.createURI(namespace, DESCRIPTION);
		hasDate = f.createURI(namespace, DATE);
		hasLink = f.createURI(namespace, LINK);
		hasWebsite = f.createURI(namespace, WEBSITE);
	}
	
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public void run() throws RepositoryException, RDFHandlerException {
		// BEGIN A TRANSACTION
		conn.begin();
		
		FileManager<RSSResume[]> fmrr = new FileManager<RSSResume[]>(RSSResume[].class, APIurl);
		FileManager<RSSChannel> fmrc = new FileManager<RSSChannel>(RSSChannel.class, APIurl);
		
		// fetch RSS list
		RSSResume[] RSSResume = fmrr.getFromWebService("/list");
		if (RSSResume == null) {
			System.out.println("Warning : RSSResume is null, there is nothing on this webservice (or TomCat is down again).");
		} else {
		
			System.out.println("RSS Resume loaded");
			
			for (RSSResume rss : RSSResume) {
				if (rss.getLastRetrival().getTime() > lastUpdate) {
					// fetch RSS datas
					RSSChannel RSSChannel = fmrc.getFromWebService(rss.getLink());
					if (RSSChannel == null) {
						System.out.println("Warning : RSSChannel is null, page " + rss.getLink() + ".");
					} else {
						System.out.print("\tCreation of RSSChannel " + rss.getLink() + "...");
						createRSS(RSSChannel, rss.getWebsite(), rss.getLink());
						System.out.println("\tDone.");
					}
					Stats.addProcessedPage();
				} else {
					Stats.addPageAlreadyOk();
				}
			}
	
			System.out.println("DONE : All datas are added to the transaction. Waiting for commit...");
			
			// CLOSE A TRANSACTION
			conn.commit();
			
			System.out.println("DONE : The transaction is commited");

			showResult();
		}
		// close connection
		conn.close();
	}
	
	private void createRSS(RSSChannel rssc, String website, String channelInternalLink) throws RepositoryException {
		// Generate id with the url
		String id = channelInternalLink.replace("/list/", "").replace("://", ".").replace("/",  ".");

		// Create specific URI
		URI channel = f.createURI(namespace, "rsschannel/" + id);
		
		conn.add(channel, RDF.TYPE, rssChannel);
		
		// RSSChannel items
		if (rssc.getTitle() != null) conn.add(channel, hasTitle, f.createLiteral(rssc.getTitle(), XMLSchema.STRING));
		if (rssc.getDescription() != null) conn.add(channel, hasDescription, f.createLiteral(rssc.getDescription(), XMLSchema.STRING));
		if (rssc.getSource() != null) conn.add(channel, hasSource, f.createLiteral(rssc.getSource(), XMLSchema.STRING));
		if (rssc.getLastBuildDate() != null) conn.add(channel, hasDate, f.createLiteral(rssc.getLastBuildDate()));
		if (website != null) conn.add(channel, hasWebsite, f.createLiteral(website, XMLSchema.STRING));
		
		for (int i = 0; i < rssc.getItems().size(); i++) {
			// Generate id with the url
			String itemId = rssc.getItems().get(i).getLink().replace("://", ".").replace("/",  ".");

			// Create specific URI
			URI item = f.createURI(namespace, "rssitem/" + itemId);
			
			conn.add(item, RDF.TYPE, summary);
			conn.add(item, RDF.TYPE, rssItem);
			conn.add(channel, hasItem, item);
			
			// Summary items
			if (rssc.getItems().get(i).getTitle() != null) conn.add(item, hasTitle, f.createLiteral(rssc.getItems().get(i).getTitle(), XMLSchema.STRING));
			if (rssc.getItems().get(i).getLink() != null) conn.add(item, hasLink, f.createLiteral(rssc.getItems().get(i).getLink(), XMLSchema.STRING));
			if (rssc.getItems().get(i).getPubDate() != null) conn.add(item, hasDate, f.createLiteral(rssc.getItems().get(i).getPubDate()));
			// RSSItem items
			if (rssc.getItems().get(i).getDescription() != null) conn.add(item, hasDescription, f.createLiteral(rssc.getItems().get(i).getDescription(), XMLSchema.STRING));
		}	
	}
	
	private void showResult() throws RepositoryException, RDFHandlerException {
		// get datas from repo to print them on the console
		
		// get the statements to print the content of the repo
		RepositoryResult<Statement> statements = conn.getStatements(null, null, null, true);
		
		Model model = Iterations.addAll(statements, new LinkedHashModel());
		// just to beautify the output
		//model.setNamespace("rdf", RDF.NAMESPACE);
		//model.setNamespace("rdfs", RDFS.NAMESPACE);
		//model.setNamespace("xsd", XMLSchema.NAMESPACE);
		//model.setNamespace("foaf", FOAF.NAMESPACE);
		//model.setNamespace("ex", namespace);
		
		// "RDF I/O"
		Rio.write(model, System.out, RDFFormat.TURTLE);
	}
}
