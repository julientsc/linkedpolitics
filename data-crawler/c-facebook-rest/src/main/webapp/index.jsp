<html>
<body>
    <h2>Jersey RESTful Web Application!</h2>
    <p><a href="webapi/myresource">Jersey resource</a>
    <p>Visit <a href="http://jersey.java.net">Project Jersey website</a>
    for more information on Jersey!
    <br/><br/>
    <table>
    	<tr>
    		<td>/webapi/page</td><td>Show list of pages for all facebook pages</td>
    	</tr>
    	<tr>
    		<td>/webapi/page/active</td><td>Show list of pages for actives facebook pages</td>
    	</tr>
    	<tr>
    		<td>/webapi/page/pageId}</td><td>Show details about a facebook page</td>
    	</tr>
    	<tr>
    		<td>/webapi/page/{pageId}/posts</td><td>Show all posts for this page</td>
    	</tr>
    	<tr>
    		<td>/webapi/page/{pageId}/posts/{postId}</td><td>Show details about a post</td>
    	</tr>
    	<tr>
    		<td>/webapi/page/{pageId}/posts/{postId}/likes</td><td>Show likes list for a post</td>
    	</tr>
    	<tr>
    		<td>/webapi/page/{pageId}/posts/{postId}/comments</td><td>Show comments list for a post</td>
    	</tr>
    </table>
</body>
</html>
