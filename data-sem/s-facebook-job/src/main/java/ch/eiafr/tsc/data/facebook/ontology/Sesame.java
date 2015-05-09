package ch.eiafr.tsc.data.facebook.ontology;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

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

import ch.eiafr.tsc.data.facebook.model.Comment;
import ch.eiafr.tsc.data.facebook.model.From;
import ch.eiafr.tsc.data.facebook.model.MessageTag;
import ch.eiafr.tsc.data.facebook.model.PageListData;
import ch.eiafr.tsc.data.facebook.model.PageToShow;
import ch.eiafr.tsc.data.facebook.model.PostToShow;
import ch.eiafr.tsc.data.facebook.model.PostsData;
import ch.eiafr.tsc.data.lib.io.FileManager;
import ch.eiafr.tsc.data.lib.io.Update;
import ch.eiafr.tsc.data.lib.stats.Stats;

public class Sesame {
	
	public static final int LOCAL = 0;
	public static final int REMOTE = 1;
	
	private long lastUpdate = 0;
	
	private String APIurl = "http://semantic.ilab-research.ch:8080/c-facebook-rest/webapi";
	public static String serviceName = "facebook";
	
	private Repository rep;
	private RepositoryConnection conn;
	private ValueFactory f;
	private String namespace;
	
	private final String PROFILE = "subject/Profile";
	private final String FACEBOOK_PAGE = "subject/FacebookPage";
	private final String SUMMARY = "subject/Summary";
	private final String FACEBOOK_POST = "subject/FacebookPost";
	private final String FACEBOOK_COMMENT = "subject/FacebookComment";
	
	private final String TITLE = "predicate/hasTitle";
	private final String LINK = "predicate/hasLink";
	private final String DATE = "predicate/hasDate";
	
	private final String ID = "predicate/hasId";
	private final String DESCRIPTION = "predicate/hasDescription";
	private final String CATEGORY = "predicate/hasCategory";
	private final String LIKES_QUANTITY = "predicate/hasLikesQuantity";
	private final String NAME = "predicate/hasName";
	private final String USERNAME = "predicate/hasUsername";
	private final String WEBSITE = "predicate/hasWebsite";
	private final String TALKING_ABOUT_QUANTITY = "predicate/hasTalkingAboutQuantity";
	private final String WERE_HERE_QUANTITY = "predicate/hasWereHereQuantity";
	private final String MEDIA_LINK = "predicate/hasMediaLink";
	private final String CONTENT = "predicate/hasContent";
	private final String UPDATED_TIME = "predicate/hasUpdateTime";
	
	private final String WROTE = "predicate/wrote";
	private final String LIKED_BY = "predicate/isLikedBy";
	private final String WRITTEN_BY = "predicate/isWrittenBy";
	private final String COMMENT = "predicate/hasComment";
	private final String TAG = "predicate/hasTag";
	private final String TYPE = "predicate/hasType";
	
	private final URI profile;
	private final URI facebookPage;
	private final URI summary;
	private final URI facebookPost;
	private final URI facebookComment;
	
	private final URI hasTitle;
	private final URI hasLink;
	private final URI hasDate;

	private final URI hasId;
	private final URI hasDescription;
	private final URI hasCategory;
	private final URI hasLikesQuantity;
	private final URI hasName;
	private final URI hasUsername;
	private final URI hasWebsite;
	private final URI hasTalkingAboutQuantity;
	private final URI hasWereHereQuantity;
	private final URI hasMediaLink;
	private final URI hasContent;
	private final URI hasUpdateTime;
	
	private final URI wrote;
	private final URI isLikedBy;
	private final URI isWrittenBy;
	private final URI hasComment;
	private final URI hasTag;
	private final URI hasType;
	
	public static void main(String[] args) throws RepositoryException, RDFHandlerException, RepositoryConfigException {
		
		//Sesame s = new Sesame(Sesame.LOCAL);
		Sesame s = new Sesame(Sesame.REMOTE);	
		
		Update u = new Update();
		System.out.println("Retrieving LastUpdate from local file");
		s.setLastUpdate(u.getLastUpdateValue(serviceName));
		
		Stats.reset();
		
		System.out.println("FACEBOOK ONTOLOGY START");
		s.run();
		System.out.println("FACEBOOK ONTOLOGY END");
		
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
			rep = manager.getRepository("prod-facebook");
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
		
		profile = f.createURI(namespace, PROFILE);
		facebookPage = f.createURI(namespace, FACEBOOK_PAGE);
		summary = f.createURI(namespace, SUMMARY);
		facebookPost = f.createURI(namespace, FACEBOOK_POST);
		facebookComment = f.createURI(namespace, FACEBOOK_COMMENT);
		
		hasTitle = f.createURI(namespace, TITLE);
		hasLink = f.createURI(namespace, LINK);
		hasDate = f.createURI(namespace, DATE);
		
		hasId = f.createURI(namespace, ID);
		hasDescription = f.createURI(namespace, DESCRIPTION);
		hasCategory = f.createURI(namespace, CATEGORY);
		hasLikesQuantity = f.createURI(namespace, LIKES_QUANTITY);
		hasName = f.createURI(namespace, NAME);
		hasUsername = f.createURI(namespace, USERNAME);
		hasWebsite = f.createURI(namespace, WEBSITE);
		hasTalkingAboutQuantity = f.createURI(namespace, TALKING_ABOUT_QUANTITY);
		hasWereHereQuantity = f.createURI(namespace, WERE_HERE_QUANTITY);
		hasMediaLink = f.createURI(namespace, MEDIA_LINK);
		hasContent = f.createURI(namespace, CONTENT);
		hasUpdateTime = f.createURI(namespace, UPDATED_TIME);
		
		wrote = f.createURI(namespace, WROTE);
		isLikedBy = f.createURI(namespace, LIKED_BY);
		isWrittenBy = f.createURI(namespace, WRITTEN_BY);
		hasComment = f.createURI(namespace, COMMENT);
		hasTag = f.createURI(namespace, TAG);
		hasType = f.createURI(namespace, TYPE);
	}
	
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public void run() throws RepositoryException, RDFHandlerException {
		// BEGIN A TRANSACTION
		conn.begin();
		
		FileManager<PageListData[]> fmpld = new FileManager<PageListData[]>(PageListData[].class, APIurl);
		FileManager<PageToShow> fmpts = new FileManager<PageToShow>(PageToShow.class, APIurl);
		FileManager<PostsData[]> fmpd = new FileManager<PostsData[]>(PostsData[].class, APIurl);
		FileManager<PostToShow> fmpsts = new FileManager<PostToShow>(PostToShow.class, APIurl);
		FileManager<From[]> fmpf = new FileManager<From[]>(From[].class, APIurl);
		FileManager<Comment[]> fmpc = new FileManager<Comment[]>(Comment[].class, APIurl);
		
		// fetch facebook pages list
		PageListData[] pageListDataArray = fmpld.getFromWebService("/page/active"); 	// only fetch active fb pages
		//PageListData[] pageListDataArray = fmpld.getFromWebService("/page"); 			// fetch all fb pages
		
		if (pageListDataArray == null) {
			System.out.println("Warning : pageListDataArray is null, there is nothing on this webservice (or TomCat is down again).");
		} else {
		
			System.out.println("Pages loaded");

			for (PageListData pageListData : pageListDataArray) {
				if (pageListData.getLastUpdate() > lastUpdate) {
					// fetch facebook page datas
					PageToShow pageToShow = fmpts.getFromWebService(pageListData.getLink());
					if (pageToShow == null) {
						System.out.println("Warning : a pageToShow is null, page " + pageListData.getId() + ".");
					} else {
						System.out.println("Creation of pageToShow of page " + pageListData.getId() + "...");
						createPageToShow(pageToShow);
						
						// fetch posts list of a facebook page
						PostsData[] postDataArray = fmpd.getFromWebService(pageListData.getLink() + "posts");
						if (postDataArray == null) {
							System.out.println("Warning : there is no posts list for the page " + pageListData.getId() + ".");
						} else {
							System.out.print("\tCreation of postToShows for page " + pageListData.getId() + "...");
							for (PostsData postsData : postDataArray) {
								if (postsData == null) {
									System.out.println("Warning : a postsData is null");
								} else {
									// fetch post datas
									PostToShow postToShow = fmpsts.getFromWebService(postsData.getLink());
									if (pageToShow == null) {
										System.out.println("Warning : a postToShow is null, post " + postsData.getId() + ".");
									} else {
										createPostToShow(pageToShow, postToShow);
										
										// fetch post likes
										From[] likes = fmpf.getFromWebService(postsData.getLink() + "likes");
										if (likes == null) {
											System.out.println("Warning : a likes list is null, post " + postsData.getId() + ".");
										} else {
											createLikes(likes, postToShow.getId());
										}
	
										// fetch post comments
										Comment[] comments = fmpc.getFromWebService(postsData.getLink() + "comments");
										if (comments == null) {
											System.out.println("Warning : a comments list is null, post " + postsData.getId() + ".");
										} else {
											createComments(comments, postToShow.getId());
										}
									}
								}
							}
							System.out.println("\tDone.");
						}
					}
					Stats.addProcessedPage();
				} else {
					Stats.addPageAlreadyOk();
				}
				// CLOSE A TRANSACTION BETWEEN EACH FACEBOOK PAGE (decomment the next line if needed)
				//conn.commit();
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
	
	private void createPageToShow(PageToShow pageData) throws RepositoryException {
		// Create specific URI
		URI page = f.createURI(namespace, "page/" + pageData.getId());
		
		conn.add(page, RDF.TYPE, profile);
		conn.add(page, RDF.TYPE, facebookPage);
		
		// Profile items
		if (pageData.getId() != null) conn.add(page, hasId, f.createLiteral(pageData.getId(), XMLSchema.STRING));
		if (pageData.getName() != null) conn.add(page, hasName, f.createLiteral(pageData.getName(), XMLSchema.STRING));
		if (pageData.getAbout() != null) conn.add(page, hasDescription, f.createLiteral(pageData.getAbout(), XMLSchema.STRING));
		// FacebookPage items
		if (pageData.getCategory() != null) conn.add(page, hasCategory, f.createLiteral(pageData.getCategory(), XMLSchema.STRING));
		conn.add(page, hasLikesQuantity, f.createLiteral(pageData.getLikes()));
		if (pageData.getLink() != null) conn.add(page, hasLink, f.createLiteral(pageData.getLink(), XMLSchema.STRING));
		if (pageData.getUsername() != null) conn.add(page, hasUsername, f.createLiteral(pageData.getUsername(), XMLSchema.STRING));
		if (pageData.getWebsite() != null) conn.add(page, hasWebsite, f.createLiteral(pageData.getWebsite(), XMLSchema.STRING));
		conn.add(page, hasTalkingAboutQuantity, f.createLiteral(pageData.getTalkingAboutCount()));
		conn.add(page, hasWereHereQuantity, f.createLiteral(pageData.getWereHereCount()));
	}
	
	private void createPostToShow(PageToShow pageData, PostToShow postData) throws RepositoryException {
		// Create specific URI
		URI post = f.createURI(namespace, "post/" + postData.getId());
		URI page = f.createURI(namespace, "page/" + pageData.getId());
		
		conn.add(post, RDF.TYPE, summary);
		conn.add(post, RDF.TYPE, facebookPost);
		conn.add(page, wrote, post);
		
		// Summary items
		if (postData.getName() != null) conn.add(post, hasTitle, f.createLiteral(postData.getName(), XMLSchema.STRING));
		if (postData.getId() != null && pageData.getLink() != null) {
			// generate link
			String[] parts = postData.getId().split("_");
			String postId = parts[1];
			String url = pageData.getLink() + "/posts/" + postId;
			conn.add(post, hasLink, f.createLiteral(url, XMLSchema.STRING));
		}
		conn.add(post, hasDate, f.createLiteral(new Date(postData.getCreatedTime())));
		
		// FacebookPost items
		if (postData.getLink() != null) conn.add(post, hasMediaLink, f.createLiteral(postData.getLink(), XMLSchema.STRING));
		if (postData.getMessage() != null) conn.add(post, hasContent, f.createLiteral(postData.getMessage(), XMLSchema.STRING));
		conn.add(post, hasUpdateTime, f.createLiteral(postData.getUpdatedTime()));
	}
	
	private void createLikes(From[] likesArray, String postId) throws RepositoryException {
		for (From like : likesArray) {
			// Create specific URI
			URI post = f.createURI(namespace, "post/" + postId);
			URI facebookProfile = f.createURI(namespace, "profile/" + like.getId());
			
			conn.add(facebookProfile, RDF.TYPE, profile); // can be a facebookPage or facebookProfile
			
			conn.add(post, isLikedBy, facebookProfile);
			
			if (like.getId() != null) conn.add(facebookProfile, hasId, f.createLiteral(like.getId(), XMLSchema.STRING));
			if (like.getName() != null) conn.add(facebookProfile, hasName, f.createLiteral(like.getName(), XMLSchema.STRING));
		}
	}
	
	private void createComments(Comment[] commentsArray, String postId) throws RepositoryException {
		for (Comment comment : commentsArray) {
			// Create specific URI
			URI post = f.createURI(namespace, "post/" + postId);
			URI postComment = f.createURI(namespace, "comment/" + comment.getId());
			URI facebookProfile = f.createURI(namespace, "profile/" + comment.getFrom().getId());
			
			conn.add(postComment, RDF.TYPE, facebookComment);
			conn.add(facebookProfile, RDF.TYPE, profile); // can be a facebookPage or facebookProfile
			
			conn.add(postComment, isWrittenBy, facebookProfile);
			conn.add(post, hasComment, postComment);
			
			// Comment items
			if (comment.getId() != null) conn.add(postComment, hasId, f.createLiteral(comment.getId(), XMLSchema.STRING));
			if (comment.getMessage() != null) conn.add(postComment, hasContent, f.createLiteral(comment.getMessage(), XMLSchema.STRING));
			if (comment.getCreated_time() != null) conn.add(postComment, hasDate, f.createLiteral(comment.getCreated_time()));
			conn.add(postComment, hasLikesQuantity, f.createLiteral(comment.getLike_count()));
			// Profile items
			if (comment.getFrom().getId() != null) conn.add(facebookProfile, hasId, f.createLiteral(comment.getFrom().getId(), XMLSchema.STRING));
			if (comment.getFrom().getName() != null) conn.add(facebookProfile, hasName, f.createLiteral(comment.getFrom().getName(), XMLSchema.STRING));
			
			if (comment.getMessage_tags() != null) {
				for (MessageTag messageTag : comment.getMessage_tags()) {
					
					URI facebookProfileTagged = f.createURI(namespace, "profile/" + messageTag.getId());
					
					conn.add(facebookProfileTagged, RDF.TYPE, profile); // can be a facebookPage or facebookProfile
					
					conn.add(postComment, hasTag, facebookProfileTagged);
					// Profile items
					if (messageTag.getId() != null) conn.add(facebookProfileTagged, hasId, f.createLiteral(messageTag.getId(), XMLSchema.STRING));
					if (messageTag.getName() != null) conn.add(facebookProfileTagged, hasName, f.createLiteral(messageTag.getName(), XMLSchema.STRING));
					if (messageTag.getType() != null) conn.add(facebookProfileTagged, hasType, f.createLiteral(messageTag.getType(), XMLSchema.STRING));
				}
			}
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
		//Rio.write(model, System.out, RDFFormat.TURTLE);
		//Rio.write(model, System.out, RDFFormat.N3);
		try {
			Rio.write(model, new FileOutputStream("C:\\Users\\jacky\\Documents\\Ontology\\data\\facebook-db"), RDFFormat.N3);
		} catch (FileNotFoundException e) {
			System.out.println("WARNING : CANNOT WRITE RESULT TO FILE");
			e.printStackTrace();
		}
	}
}
