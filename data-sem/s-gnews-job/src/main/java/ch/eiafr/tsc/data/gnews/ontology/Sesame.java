package ch.eiafr.tsc.data.gnews.ontology;

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
import data.gnews.model.GoogleNew;
import data.gnews.model.GoogleNewsModel;
import data.gnews.model.KeywordResume;

public class Sesame {
	public static final int LOCAL = 0;
	public static final int REMOTE = 1;
	
	private long lastUpdate = 0;
	
	private String APIurl = "http://semantic.ilab-research.ch:8080/c-gnews-rest/webapi";
	public static String serviceName = "gnews";
	
	private Repository rep;
	private RepositoryConnection conn;
	private ValueFactory f;
	private String namespace;
	
	private final String SUMMARY = "subject/Summary";
	private final String GNEWS_NEWS = "subject/GoogleNews";
	
	private final String TITLE = "predicate/hasTitle";
	private final String DATE = "predicate/hasDate";
	private final String LINK = "predicate/hasLink";
	private final String KEYWORD = "predicate/hasKeyword";
	
	private final URI summary;
	private final URI news;
	
	private final URI hasTitle;
	private final URI hasDate;
	private final URI hasLink;
	private final URI hasKeyword;
	
	public static void main(String[] args) throws RepositoryException, RDFHandlerException, RepositoryConfigException {
		//Sesame s = new Sesame(Sesame.LOCAL);
		Sesame s = new Sesame(Sesame.REMOTE);	
		
		Update u = new Update();
		System.out.println("Retrieving LastUpdate from local file");
		s.setLastUpdate(u.getLastUpdateValue(serviceName));
		
		Stats.reset();
		
		System.out.println("GOOGLE NEWS ONTOLOGY START");
		s.run();
		System.out.println("GOOGLE NEWS ONTOLOGY END");
		
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
			rep = manager.getRepository("prod-gnews");
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
		
		summary = f.createURI(namespace, SUMMARY);
		news = f.createURI(namespace, GNEWS_NEWS);
		
		hasTitle = f.createURI(namespace, TITLE);
		hasDate = f.createURI(namespace, DATE);
		hasLink = f.createURI(namespace, LINK);
		hasKeyword = f.createURI(namespace, KEYWORD);
	}
	
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public void run() throws RepositoryException, RDFHandlerException {
		// BEGIN A TRANSACTION
		conn.begin();
		
		FileManager<KeywordResume[]> fmkr = new FileManager<KeywordResume[]>(KeywordResume[].class, APIurl);
		FileManager<GoogleNewsModel> fmgn = new FileManager<GoogleNewsModel>(GoogleNewsModel.class, APIurl);
		
		// fetch google news articles list
		KeywordResume[] keywordResume = fmkr.getFromWebService("/list");
		if (keywordResume == null) {
			System.out.println("Warning : keywordResume is null, there is nothing on this webservice (or TomCat is down again).");
		} else {
		
			System.out.println("Keyword Resume loaded");
			
			for (KeywordResume keyword : keywordResume) {
				if (keyword.getLastUpdate().getTime() > lastUpdate) {
					// fetch google news datas
					GoogleNewsModel googleNews = fmgn.getFromWebService(keyword.getLink());
					if (googleNews == null) {
						System.out.println("Warning : googleNewsModel is null, page " + keyword.getLink() + ".");
					} else {
						System.out.print("\tCreation of googleNews " + keyword.getLink() + "...");
						
						for (int i = 0; i < googleNews.getNews().size(); i++) {
							createGoogleNews(googleNews.getKeyword(), googleNews.getNews().get(i));
						}
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
	
	private void createGoogleNews(String googleNewsKeyword, GoogleNew gn) throws RepositoryException {
		for (int i = 0; i < gn.getLinks().size(); i++) {
			// Generate id with the url
			String id = gn.getLinks().get(i).toString().replace("://", ".").replace("/",  ".");

			// Create specific URI
			URI gnews = f.createURI(namespace, "news/" + id);
			
			conn.add(gnews, RDF.TYPE, summary);
			conn.add(gnews, RDF.TYPE, news);
			
			// Summary items
			if (gn.getTitle() != null) conn.add(gnews, hasTitle, f.createLiteral(gn.getTitle(), XMLSchema.STRING));
			conn.add(gnews, hasDate, f.createLiteral(gn.getPubDate()));
			if (gn.getLinks().get(i) != null) conn.add(gnews, hasLink, f.createLiteral(gn.getLinks().get(i).toString(), XMLSchema.STRING));
			
			// GoogleNews
			conn.add(gnews, hasKeyword, f.createLiteral(googleNewsKeyword, XMLSchema.STRING));
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
