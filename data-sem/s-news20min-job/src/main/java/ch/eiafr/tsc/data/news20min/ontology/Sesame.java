package ch.eiafr.tsc.data.news20min.ontology;



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

import ch.eiafr.tsc.data.lib.io.FileManager;
import ch.eiafr.tsc.data.lib.io.Update;
import ch.eiafr.tsc.data.lib.stats.Stats;
import ch.eiafr.tsc.data.news20min.model.Article;
import ch.eiafr.tsc.data.news20min.model.ArticleSummary;
import ch.eiafr.tsc.data.news20min.model.KeywordSummary;

public class Sesame {
	public static final int LOCAL = 0;
	public static final int REMOTE = 1;
	
	private long lastUpdate = 0;
	
	private String APIurlFR = "http://semantic.ilab-research.ch:8080/c-news20min-rest-fr/webapi";
	private String APIurlDE = "http://semantic.ilab-research.ch:8080/c-news20min-rest-de/webapi";
	public static String serviceName = "news20min";
	
	private Repository rep;
	private RepositoryConnection conn;
	private ValueFactory f;
	private String namespace;
	
	private final String SUMMARY = "subject/Summary";
	private final String ARTICLE_20MIN = "subject/News20min";
	private final String PEOPLE = "subject/People";
	
	private final String DATE = "predicate/hasDate";
	private final String TITLE = "predicate/hasTitle";
	private final String SUBTITLE = "predicate/hasSubtitle";
	private final String CATEGORY = "predicate/hasCategory";
	private final String CONTENT = "predicate/hasContent";
	private final String LANG = "predicate/hasLang";
	private final String LINK = "predicate/hasLink";
	private final String KEYWORD = "predicate/hasKeyword";
	private final String IS_QUOTED = "predicate/isQuoted";
	
	private final String FIRST_NAME = "predicate/hasFirstName";
	private final String LAST_NAME = "predicate/hasLastName";

	private final URI summary;
	private final URI news;
	private final URI people;
	
	private final URI hasDate;	
	private final URI hasTitle;
	private final URI hasSubtitle;
	private final URI hasCategory;
	private final URI hasContent;
	private final URI hasLang;
	private final URI hasLink;
	private final URI hasKeyword;
	private final URI isQuoted;
	
	private final URI hasFirstName;
	private final URI hasLastName;
	
	public static void main(String[] args) throws RepositoryException, RDFHandlerException, RepositoryConfigException {
		//Sesame s = new Sesame(Sesame.LOCAL);
		Sesame s = new Sesame(Sesame.REMOTE);	
		
		Update u = new Update();
		System.out.println("Retrieving LastUpdate from local file");
		s.setLastUpdate(u.getLastUpdateValue(serviceName));
		
		Stats.reset();
		
		System.out.println("NEWS20MIN ONTOLOGY START");
		s.run();
		System.out.println("NEWS20MIN ONTOLOGY END");
		
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
			rep = manager.getRepository("prod-news20min");
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
		news = f.createURI(namespace, ARTICLE_20MIN);
		people = f.createURI(namespace, PEOPLE);
		
		hasDate = f.createURI(namespace, DATE);
		hasTitle = f.createURI(namespace, TITLE);
		hasSubtitle = f.createURI(namespace, SUBTITLE);
		hasCategory = f.createURI(namespace, CATEGORY);
		hasContent = f.createURI(namespace, CONTENT);
		hasLang = f.createURI(namespace, LANG);
		hasLink = f.createURI(namespace, LINK);
		hasKeyword = f.createURI(namespace, KEYWORD);
		isQuoted = f.createURI(namespace, IS_QUOTED);
		
		hasFirstName = f.createURI(namespace, FIRST_NAME);
		hasLastName = f.createURI(namespace, LAST_NAME);
	}
	
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public void run() throws RepositoryException, RDFHandlerException {
		// BEGIN A TRANSACTION
		conn.begin();
		
		System.out.println("NEWS20MINUTES ==> FRENCH");
		runInSpecificLang("FR");
		System.out.println("NEWS20MINUTEN ==> GERMAN");
		runInSpecificLang("DE");
		
		System.out.println("DONE : All datas are added to the transaction. Waiting for commit...");
		
		// CLOSE A TRANSACTION
		conn.commit();
		
		System.out.println("DONE : The transaction is commited");

		//showResult();
		
		// close connection
		conn.close();
	}
	
	private void runInSpecificLang(String lang) throws RepositoryException {
		
		FileManager<KeywordSummary[]> fmks;
		FileManager<ArticleSummary[]> fmas;
		FileManager<Article> fma;
		
		if (lang.equals("FR")) {
			fmks = new FileManager<KeywordSummary[]>(KeywordSummary[].class, APIurlFR);
			fmas = new FileManager<ArticleSummary[]>(ArticleSummary[].class, APIurlFR);
			fma = new FileManager<Article>(Article.class, APIurlFR);
		} else if (lang.equals("DE")) {
			fmks = new FileManager<KeywordSummary[]>(KeywordSummary[].class, APIurlDE);
			fmas = new FileManager<ArticleSummary[]>(ArticleSummary[].class, APIurlDE);
			fma = new FileManager<Article>(Article.class, APIurlDE);
		} else {
			System.out.println("ERROR : The language is not well determined !");
			return;
		}
		
		// fetch RSS list
		KeywordSummary[] keywordSummaryArray = fmks.getFromWebService("/list");
		if (keywordSummaryArray == null) {
			System.out.println("Warning : keywordSummaryArray is null, there is nothing on this webservice (or TomCat is down again).");
		} else {
		
			System.out.println("Keyword Summary loaded");
			
			for (KeywordSummary keyword : keywordSummaryArray) {
				if (keyword.getLastUpdate() == null || keyword.getLastUpdate().getTime() > lastUpdate) {
					// fetch Article Summaries datas
					ArticleSummary[] articleSummaryArray = fmas.getFromWebService(keyword.getLink());
					if (articleSummaryArray == null) {
						System.out.println("Warning : articleSummaryArray is null, link " + keyword.getLink() + ".");
					} else {
						
						for (ArticleSummary articleSummary : articleSummaryArray) {
							// fetch Article datas
							Article article = fma.getFromWebService(articleSummary.getLinkRest());
							if (article == null) {
								System.out.println("Warning : article is null, link " + articleSummary.getLinkRest() + ".");
							} else {
								System.out.print("\tCreation of Article " + articleSummary.getLinkRest() + "...");
								createArticle(article, keyword.getParam());
								System.out.println("\tDone.");
							}
						}
					}
					Stats.addProcessedPage();
				} else {
					Stats.addPageAlreadyOk();
				}
			}
		}
	}
	
	private void createArticle(Article art, String keyword20min) throws RepositoryException {
		// Generate id with the url
		String id = art.getUrl().toString().replace("://", ".").replace("/",  ".");
		String peopleId = keyword20min.replace(" ", ".");
		// Create specific URI
		URI article = f.createURI(namespace, "news/" + id);
		URI quotedPerson = f.createURI(namespace, "people/" + peopleId);
		
		conn.add(article, RDF.TYPE, summary);
		conn.add(article, RDF.TYPE, news);
		
		conn.add(quotedPerson, RDF.TYPE, people);
		
		// Summary items
		if (art.getDate() != null) conn.add(article, hasDate, f.createLiteral(art.getDate()));
		if (art.getTitle() != null) conn.add(article, hasTitle, f.createLiteral(art.getTitle(), XMLSchema.STRING));
		if (art.getUrl() != null) conn.add(article, hasLink, f.createLiteral(art.getUrl().toString(), XMLSchema.STRING));
		// News20min items
		if (art.getSubtitle() != null) conn.add(article, hasSubtitle, f.createLiteral(art.getSubtitle(), XMLSchema.STRING));
		if (art.getCategory() != null) conn.add(article, hasCategory, f.createLiteral(art.getCategory(), XMLSchema.STRING));
		if (art.getContent() != null) conn.add(article, hasContent, f.createLiteral(art.getContent(), XMLSchema.STRING));
		if (art.getLang() != null) conn.add(article, hasLang, f.createLiteral(art.getLang(), XMLSchema.STRING));
		if (keyword20min != null) conn.add(article, hasKeyword, f.createLiteral(keyword20min, XMLSchema.STRING));
		
		conn.add(article, isQuoted, quotedPerson);
		
		// People
		String[] name = keyword20min.split(" ");
		if (name.length == 2) {
			conn.add(quotedPerson, hasFirstName, f.createLiteral(name[1], XMLSchema.STRING));
			conn.add(quotedPerson, hasLastName, f.createLiteral(name[0], XMLSchema.STRING));
		} else if (name.length == 3) {
			conn.add(quotedPerson, hasFirstName, f.createLiteral(name[2], XMLSchema.STRING));
			conn.add(quotedPerson, hasLastName, f.createLiteral(name[0] + " " + name[1], XMLSchema.STRING));
		} else {
			System.out.println("WARNING : Can't save the name of the quoted person !");
			conn.add(quotedPerson, hasLastName, f.createLiteral(keyword20min, XMLSchema.STRING));
		}
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
//		//Rio.write(model, System.out, RDFFormat.TURTLE);
//		try {
//			Rio.write(model, new FileOutputStream("C:\\Users\\jacky\\Documents\\Ontology\\data\\news20min-db"), RDFFormat.RDFXML);
//		} catch (FileNotFoundException e) {
//			System.out.println("WARNING : CANNOT WRITE RESULT TO FILE");
//			e.printStackTrace();
//		}
//	}
}
