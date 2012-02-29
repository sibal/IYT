<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% @SuppressWarnings("unchecked") 
   String token = (String)request.getAttribute("token");
   String token_secret = (String)request.getAttribute("token_secret");

%>

<script>

opener.parent.document.forms["signupform"].twit_authT.value = "<%= token %>";
opener.parent.document.forms["signupform"].twit_authTS.value = "<%= token_secret %>";


window.close();

</script>