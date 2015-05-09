package ch.eiafr.tsc.data.twitter.ontology;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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
import data.twitter.model.RelatedUserData;
import data.twitter.model.StatusData;
import data.twitter.model.TimelineData;
import data.twitter.model.UserInfoData;
import data.twitter.model.UserListData;
	
public class Sesame {
	
	public static final int LOCAL = 0;
	public static final int REMOTE = 1;
	
	private long lastUpdate = 0;
	
	private String APIurl = "http://semantic.ilab-research.ch:8080/c-twitter-rest/webapi";
	public static String serviceName = "twitter";
	
	private Repository rep;
	private RepositoryConnection conn;
	private ValueFactory f;
	private String namespace;
	
	private final String PROFILE = "subject/Profile";
	private final String TWITTER_PROFILE = "subject/TwitterProfile";
	private final String SUMMARY = "subject/Summary";
	private final String TWEET = "subject/Tweet";
	
	private final String ID = "predicate/hasId";
	// for UserInfoData
	private final String NAME = "predicate/hasName";
	private final String SCREEN_NAME = "predicate/hasScreenName";
	private final String LOCATION = "predicate/hasLocation";
	private final String DESCRIPTION = "predicate/hasDescription";
	private final String FOLLOWERS_QUANTITY = "predicate/hasFollowersQuantity";
	private final String FRIENDS_QUANTITY = "predicate/hasFriendsQuantity";
	private final String STATUSES_QUANTITY = "predicate/hasStatusesQuantity";
	// for StatusData
	private final String TITLE = "predicate/hasTitle";
	private final String LINK = "predicate/hasLink";
	private final String DATE = "predicate/hasDate";
	
	private final String RETWEETS_QUANTITY = "predicate/hasRetweetsQuantity";
	private final String FAVORITES_QUANTITY = "predicate/hasFavoritesQuantity";
	// for related relations
	private final String FOLLOWER = "predicate/hasFollower";
	private final String FRIEND = "predicate/hasFriend";
	private final String WROTE = "predicate/wrote";
	
	private final URI profile;
	private final URI twitterProfile;
	private final URI summary;
	private final URI tweet;
	
	private final URI hasId;
	private final URI hasName;
	private final URI hasScreenName;
	private final URI hasLocation;
	private final URI hasDescription;
	private final URI hasFollowersQuantity;
	private final URI hasFriendsQuantity;
	private final URI hasStatusesQuantity;
	
	private final URI hasTitle;
	private final URI hasLink;
	private final URI hasDate;
	
	private final URI hasRetweetsQuantity;
	private final URI hasFavoritesQuantity;
	
	private final URI hasFollower;
	private final URI hasFriend;
	private final URI wrote;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.US);
	
	public static void main(String[] args) throws RepositoryException, RDFHandlerException, RepositoryConfigException {
		//Sesame s = new Sesame(Sesame.LOCAL);
		Sesame s = new Sesame(Sesame.REMOTE);	
		
		Update u = new Update();
		System.out.println("Retrieving LastUpdate from local file");
		s.setLastUpdate(u.getLastUpdateValue(serviceName));
		
		Stats.reset();
		
		System.out.println("TWITTER ONTOLOGY START");
		s.run();
		System.out.println("TWITTER ONTOLOGY END");
		
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
			rep = manager.getRepository("prod-twitter");
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
		twitterProfile = f.createURI(namespace, TWITTER_PROFILE);
		summary = f.createURI(namespace, SUMMARY);
		tweet = f.createURI(namespace, TWEET);
		
		hasId = f.createURI(namespace, ID);
		hasName = f.createURI(namespace, NAME);
		hasScreenName = f.createURI(namespace, SCREEN_NAME);
		hasLocation = f.createURI(namespace, LOCATION);
		hasDescription = f.createURI(namespace, DESCRIPTION);
		hasFollowersQuantity = f.createURI(namespace, FOLLOWERS_QUANTITY);
		hasFriendsQuantity = f.createURI(namespace, FRIENDS_QUANTITY);
		hasStatusesQuantity = f.createURI(namespace, STATUSES_QUANTITY);
		
		hasTitle = f.createURI(namespace, TITLE);
		hasLink = f.createURI(namespace, LINK);
		hasDate = f.createURI(namespace, DATE);
		
		hasRetweetsQuantity = f.createURI(namespace, RETWEETS_QUANTITY);
		hasFavoritesQuantity = f.createURI(namespace, FAVORITES_QUANTITY);
		
		hasFollower = f.createURI(namespace, FOLLOWER);
		hasFriend = f.createURI(namespace, FRIEND);
		wrote = f.createURI(namespace, WROTE);
	}
	
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	public void run() throws RepositoryException, RDFHandlerException {
		// BEGIN A TRANSACTION
		conn.begin();

		FileManager<UserListData[]> fmuld = new FileManager<UserListData[]>(UserListData[].class, APIurl);
		FileManager<UserInfoData> fmuid = new FileManager<UserInfoData>(UserInfoData.class, APIurl);
		FileManager<TimelineData[]> fmtd = new FileManager<TimelineData[]>(TimelineData[].class, APIurl);
		FileManager<StatusData> fmsd = new FileManager<StatusData>(StatusData.class, APIurl);
		FileManager<RelatedUserData[]> fmrud = new FileManager<RelatedUserData[]>(RelatedUserData[].class, APIurl);
		
		UserListData[] userListDataArray = fmuld.getFromWebService("/profils");
		if (userListDataArray == null) {
			System.out.println("Warning : userListDataArray is null, there is nothing on this webservice (or TomCat is down again).");
		} else {
		
			System.out.println("UserListDataArray loaded");
	
			for (UserListData userListData : userListDataArray) {
				if (userListData.getLastUpdate() == null || userListData.getLastUpdate().getTime() > lastUpdate) {	
					
					UserInfoData userInfoData = fmuid.getFromWebService(userListData.getLink());
					
					if (userInfoData == null) {
						System.out.println("Warning : a userInfoDataArray is null : " + userListData.getLink());
					} else {
						System.out.println("Creation of UserInfoData " + userListData.getLink() + "...");
						
						//System.out.println("\nCreation of UserInfoData " + userListData.getLink() + "...");
					
						createUserInfoData(userInfoData);
											
						if (userInfoData.getFollowers_link() != null) {
							// fetch all user followers
							RelatedUserData[] relatedUserDataArray = fmrud.getFromWebService(userInfoData.getFollowers_link());
							//System.out.println("relateduserdataArray : " + relatedUserDataArray.length);
							if (relatedUserDataArray == null) { //|| relatedUserDataArray.length < 1) {
								System.out.println("Warning : a follower data array is null : " + userInfoData.getFollowers_link() + ".");
							} else {
								System.out.print("\t\tCreation of link to followers from link " + userInfoData.getFollowers_link() + "...");
								for (RelatedUserData relatedUserData : relatedUserDataArray) {
									createFollowersData(userInfoData, relatedUserData);
								}
								System.out.println("\t\tDone.");
							}
						}
						
						if (userInfoData.getFriends_link() != null) {
							// fetch all user friends
							RelatedUserData[] relatedUserDataArray = fmrud.getFromWebService(userInfoData.getFriends_link());
							
							if (relatedUserDataArray == null) {
								System.out.println("Warning : a friend data array is null : " + userInfoData.getFriends_link() + ".");
							} else {
								System.out.print("\t\tCreation of link to friends from link " + userInfoData.getFriends_link() + "...");
								for (RelatedUserData relatedUserData : relatedUserDataArray) {
									createFriendsData(userInfoData, relatedUserData);
								}
								System.out.println("\t\tDone.");
							}
						}
						
						if (userInfoData.getStatuses_link() != null) {
							// follow the link
							TimelineData[] timelineDataArray = fmtd.getFromWebService(userInfoData.getStatuses_link());
							if (timelineDataArray == null) {
								System.out.println("Warning : a timeline data array is null : " + userInfoData.getStatuses_link() + ".");
							} else {
								System.out.print("\t\tCreation of statusDatas for user " + userInfoData.getId() + "...");
								for (TimelineData timelineData : timelineDataArray) {
									if (timelineData == null) {
										System.out.println("Warning : a timeLineData is null");
									} else {
										StatusData statusData = fmsd.getFromWebService(timelineData.getLink());
										
										if (statusData == null) {
											System.out.println("Warning : a statusData is null");
										} else {
											createStatusData(userInfoData, statusData);
										}
									}
								}
								System.out.println("\t\tDone.");
							}
						}
						//System.out.println("\tDone.");
						
					}
					System.out.println("UserInfoData creation is done.");
	
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
	
	private void createUserInfoData(UserInfoData userData) throws RepositoryException {
		// Create specific URI
		URI user = f.createURI(namespace, "user/" + userData.getId());
		
		conn.add(user, RDF.TYPE, profile);
		conn.add(user, RDF.TYPE, twitterProfile);
		
		if (userData.getId() != null && !userData.getId().isEmpty()) conn.add(user, hasId, f.createLiteral(userData.getId(), XMLSchema.STRING));
		if (userData.getName() != null && !userData.getName().isEmpty()) conn.add(user, hasName, f.createLiteral(userData.getName(), XMLSchema.STRING));
		if (userData.getScreen_name() != null && !userData.getScreen_name().isEmpty()) {
			conn.add(user, hasScreenName, f.createLiteral(userData.getScreen_name(), XMLSchema.STRING));
			// generate link
			String url = "http://twitter.com/" + userData.getScreen_name();
			conn.add(user, hasLink, f.createLiteral(url, XMLSchema.STRING));
		}
		if (userData.getLocation() != null && !userData.getLocation().isEmpty()) conn.add(user, hasLocation, f.createLiteral(userData.getLocation(), XMLSchema.STRING));
		if (userData.getDescription() != null && !userData.getDescription().isEmpty()) conn.add(user, hasDescription, f.createLiteral(userData.getDescription(), XMLSchema.STRING));
		conn.add(user, hasFollowersQuantity, f.createLiteral(userData.getFollowers_count()));
		conn.add(user, hasFriendsQuantity, f.createLiteral(userData.getFriends_count()));
		conn.add(user, hasStatusesQuantity, f.createLiteral(userData.getStatuses_count()));
	}
	
	private void createStatusData(UserInfoData userData, StatusData statusData) throws RepositoryException {
		// Create specific URI
		URI status = f.createURI(namespace, "status/" + statusData.getId_str());
		URI user = f.createURI(namespace, "user/" + userData.getId());
		
		conn.add(status, RDF.TYPE, summary);
		conn.add(status, RDF.TYPE, tweet);
		conn.add(user, wrote, status);
		
		// Summary
		if (statusData.getText() != null) conn.add(status, hasTitle, f.createLiteral(statusData.getText(), XMLSchema.STRING));
		if (statusData.getId_str() != null) {
			// generate link
			String url = "http://twitter.com/" + userData.getScreen_name() + "/status/" + statusData.getId_str();
			conn.add(status, hasLink, f.createLiteral(url, XMLSchema.STRING));
		}
		if (statusData.getDate() != null)
			try {
				conn.add(status, hasDate, f.createLiteral(sdf.parse(statusData.getDate())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
		// Tweet
		conn.add(status, hasRetweetsQuantity, f.createLiteral(statusData.getRetweet_count()));
		conn.add(status, hasFavoritesQuantity, f.createLiteral(statusData.getFavorite_count()));
	}
	
	private void createFollowersData(UserInfoData userData, RelatedUserData relatedUserData) throws RepositoryException {
		if (relatedUserData.getId() != null) {
			URI user = f.createURI(namespace, "user/" + userData.getId());
			URI follower = f.createURI(namespace, "user/" + relatedUserData.getId());
			
			conn.add(user, hasFollower, follower);
		}
	}
	
	private void createFriendsData(UserInfoData userData, RelatedUserData relatedUserData) throws RepositoryException {
		if (relatedUserData.getId() != null) {
			URI user = f.createURI(namespace, "user/" + userData.getId());
			URI follower = f.createURI(namespace, "user/" + relatedUserData.getId());
			
			conn.add(user, hasFriend, follower);
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
//			Rio.write(model, new FileOutputStream("C:\\Users\\jacky\\Documents\\Ontology\\data\\twitter-db"), RDFFormat.N3);
//		} catch (FileNotFoundException e) {
//			System.out.println("WARNING : CANNOT WRITE RESULT TO FILE");
//			e.printStackTrace();
//		}
//	}
}
