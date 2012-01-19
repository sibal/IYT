<%@ page import="iyt.models.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ocpsoft.pretty.time.PrettyTime" %>

<% @SuppressWarnings("unchecked") 
	List<Article> articles = (List<Article>)request.getAttribute("articles");
   PrettyTime p = new PrettyTime();
%>

<% for(Article article : articles) { %>

<div id="articleBox" onClick="$('#sidebar1').load('/article/<%=article.getAuthor_data().getUsername()%>/<%=article.getId() %>');" style="cursor:hand; cursor: pointer;">
	<div id="profilePic"><img src="img/profile_48_12.gif" width="47" height="48" alt="profile" /></div>
	<div id="profileText">
		<span class="content_id">
		<img src="img/tw_ico.gif" alt="" width="18" height="18" align="absmiddle" /><%= article.getAuthor_data().getNick() %> 
		</span> 
		<span class="content_name"><%= article.getAuthor_data().getName() %></span>
	</div>
	<div id="timelineContent">
		<span class="timelineContent_normal"><%=article.getContent() %></span>
	</div>
	<div id="articleSubMenu">
		<span class="articleSubMenu_time"><%=p.format(article.getCreated_at()) %> / </span>
		<img src="img/tw_fav.gif" width="17" height="15" align="absmiddle" class="textmiddle" /><span class="articleSubMenu_twitter">Favorite&nbsp;<img src="img/tw_ret.gif" width="17" height="15" align="absmiddle" class="textmiddle" />Retweet&nbsp;<img src="img/tw_rep.gif" width="17" height="15" align="absmiddle" class="textmiddle" />Reply</span>
	</div>
</div>

<% } %>