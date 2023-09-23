<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.UUID" %>

<!-- トークンの取得  -->
<%
	// セッションからトークンを取得
	String csrfToken = (String) session.getAttribute("csrfToken");
	
	// セッションにトークンがない場合は新たに生成してセッションに格納
	if (csrfToken == null) {
	    csrfToken = UUID.randomUUID().toString();
	    session.setAttribute("csrfToken", csrfToken);
	}
%>