<html>
<body>
    <h2>Jersey RESTful Web Application!</h2>
    <p><a href="webapi/myresource">Jersey resource</a>
    <p>Visit <a href="http://jersey.java.net">Project Jersey website</a>
    for more information on Jersey!<br/><br/>
    <table>
    	<tr>
    		<td>/webapi/all</td><td>Show list of pages for all twitter profiles (include friends and followers of monitoring accounts)</td>
    	</tr>
    	<tr>
    		<td>/webapi/all/{page}</td><td>Show page of twitter profils</td>
    	</tr>
    	<tr>
    		<td>/webapi/profils</td><td>Show all monitoring twitter profils</td>
    	</tr>
    	<tr>
    		<td>/webapi/profils/actives</td><td>Show all active monitoring twitter profils</td>
    	</tr>
    	<tr>
    		<td>/webapi/profils/{twitter_id}</td><td>Show twitter profil for a id</td>
    	</tr>
    	<tr>
    		<td>/webapi/profils/{twitter_id}/friends</td><td>Show list of friends of a twitter profil</td>
    	</tr>
    	<tr>
    		<td>/webapi/profils/{twitter_id}/followers</td><td>Show list of followers of a twitter profil</td>
    	</tr>
    	<tr>
    		<td>/webapi/profils/{twitter_id}/timeline</td><td>Show list of tweets of a twitter profil</td>
    	</tr>
    	<tr>
    		<td>/webapi/profils/{twitter_id}/timeline/{tweet_id}</td><td>Show details about a tweet</td>
    	</tr>
    </table>
</body>
</html>
