package ch.eiafr.tsc.data.bing.ontology;


import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.XMLSchema;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.manager.RemoteRepositoryManager;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.sail.memory.MemoryStore;

import ch.eiafr.tsc.data.bing.model.BingResult;
import ch.eiafr.tsc.data.bing.model.BingResultModel;
import ch.eiafr.tsc.data.bing.model.KeywordResume;
import ch.eiafr.tsc.data.lib.io.FileManager;
import ch.eiafr.tsc.data.lib.io.Update;
import ch.eiafr.tsc.data.lib.stats.Stats;

public class Sesame {
	public static final int LOCAL = 0;
	public static final int REMOTE = 1;
	
	private long lastUpdate = 0;
	
	private String APIurl = "http://semantic.ilab-research.ch:8080/c-bing-rest/webapi";
	public static String serviceName = "bing";
	
	private Repository rep;
	private RepositoryConnection conn;
	private ValueFactory f;
	private String namespace;
	
	private final String SUMMARY = "subject/Summary";
	private final String BING_NEWS = "subject/BingNews";
	
	private final String TITLE = "predicate/hasTitle";
	private final String DATE = "predicate/hasDate";
	private final String LINK = "predicate/hasLink";
	
	private final String DESCRIPTION = "predicate/hasDescription";
	private final String KEYWORD = "predicate/hasKeyword";
	
	private final URI summary;
	private final URI news;
	
	private final URI hasTitle;
	private final URI hasDate;
	private final URI hasLink;
	
	private final URI hasDescription;
	private final URI hasKeyword;
	
	public static void main(String[] args) throws RepositoryException, RDFHandlerException, RepositoryConfigException {
		//Sesame s = new Sesame(Sesame.LOCAL);
		Sesame s = new Sesame(Sesame.REMOTE);	
		
		Update u = new Update();
		System.out.println("Retrieving LastUpdate from local file");
		s.setLastUpdate(u.getLastUpdateValue(serviceName));
		
		Stats.reset();
		
		System.out.println("BING ONTOLOGY START");
		s.run();
		System.out.println("BING ONTOLOGY END");
		
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
			rep = manager.getRepository("prod-bing");
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
		news = f.createURI(namespace, BING_NEWS);
		
		hasTitle = f.createURI(namespace, TITLE);
		hasDate = f.createURI(namespace, DATE);
		hasLink = f.createURI(namespace, LINK);
		
		hasDescription = f.createURI(namespace, DESCRIPTION);
		hasKeyword = f.createURI(namespace, KEYWORD);
	}
	
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public void run() throws RepositoryException, RDFHandlerException {
		// BEGIN A TRANSACTION
		conn.begin();
		
		FileManager<KeywordResume[]> fmkr = new FileManager<KeywordResume[]>(KeywordResume[].class, APIurl);
		FileManager<BingResultModel> fmbrm = new FileManager<BingResultModel>(BingResultModel.class, APIurl);
		FileManager<BingResult> fmbr = new FileManager<BingResult>(BingResult.class, APIurl);
		
		// fetch google news articles list
		KeywordResume[] keywordResume = fmkr.getFromWebService("/list");
		if (keywordResume == null) {
			System.out.println("Warning : keywordResume is null, there is nothing on this webservice (or TomCat is down again).");
		} else {
		
			System.out.println("Keyword Resume loaded");
			
			for (KeywordResume keyword : keywordResume) {
				if (keyword.getLastUpdate().getTime() > lastUpdate) {
					// fetch Bing articles datas
					BingResultModel bingNews = fmbrm.getFromWebService(keyword.getLink());
					if (bingNews == null) {
						System.out.println("Warning : bingResultModel is null, page " + keyword.getLink() + ".");
					} else {
						System.out.print("\tCreation of keyword " + keyword.getLink() + "...");
						
						for (int i = 0; i < bingNews.getIDs().size(); i++) {
							BingResult bingNew = fmbr.getFromWebService(bingNews.getIDs().get(i));
							if (bingNew == null) {
								System.out.println("Warning : bingResult is null, page " + bingNews.getIDs().get(i) + ".");
							} else {
								createBingNews(bingNew, bingNews.getKeyword());
							}
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

			//showResult();
		}
		// close connection
		conn.close();
	}
	
	private void createBingNews(BingResult bn, String bingKeyword) throws RepositoryException {
		// Create specific URI
		URI bingNews = f.createURI(namespace, "news/" + bn.getId());
		
		conn.add(bingNews, RDF.TYPE, summary);
		conn.add(bingNews, RDF.TYPE, news);
		
		// Summary items
		if (bn.getTitle() != null) conn.add(bingNews, hasTitle, f.createLiteral(bn.getTitle(), XMLSchema.STRING));
		conn.add(bingNews, hasDate, f.createLiteral(bn.getDate()));
		if (bn.getUrl() != null) conn.add(bingNews, hasLink, f.createLiteral(bn.getUrl().toString(), XMLSchema.STRING));
		
		// BingNews items
		if (bn.getDescription() != null) conn.add(bingNews, hasDescription, f.createLiteral(bn.getDescription(), XMLSchema.STRING));
		if (bingKeyword != null) conn.add(bingNews, hasKeyword, f.createLiteral(bingKeyword, XMLSchema.STRING));
	}
	
//	private void showResult() throws RepositoryException, RDFHandlerException {
//		// get datas from repo to print them on the console
//		
//		// get the statements to print the content of the repo
//		RepositoryResult<Statement> statements = conn.getStatements(null, null, null, true);
//		
//		Model model = Iterations.addAll(statements, new LinkedHashModel());
//		// just to beautify the output
//		//model.setNamespace("rdf", RDF.NAMESPACE);
//		//model.setNamespace("rdfs", RDFS.NAMESPACE);
//		//model.setNamespace("xsd", XMLSchema.NAMESPACE);
//		//model.setNamespace("foaf", FOAF.NAMESPACE);
//		//model.setNamespace("ex", namespace);
//		
//		// "RDF I/O"
//		Rio.write(model, System.out, RDFFormat.TURTLE);
//	}
}
