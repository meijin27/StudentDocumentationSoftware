<%@page contentType="text/html; charset=UTF-8" %>

<%
String contextPath = request.getContextPath();
response.sendRedirect(contextPath + "/login/login-in.jsp");
%>