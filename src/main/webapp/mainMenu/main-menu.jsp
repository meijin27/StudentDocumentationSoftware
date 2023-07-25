<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="side-bar-menu.jsp" %>

<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 mt-5">
  <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
    <h1 class="h2">運営からのお知らせ</h1>
  </div>
  
		<%@ page import="java.io.*" %>
		<%
		  // ファイルパス
		  String filePath = getServletContext().getRealPath("/txt/notice.txt");
		
		  BufferedReader br = null;
		
		  try {
		    br = new BufferedReader(new FileReader(filePath));
		    String line;
		
		    while ((line = br.readLine()) != null) {
		      // HTMLエスケープを行う
		      line = line.replace("&", "&amp;");
		      line = line.replace("<", "&lt;");
		      line = line.replace(">", "&gt;");
		      line = line.replace("\"", "&quot;");
		      line = line.replace("'", "&apos;");
		%>
			<div class="left-aligned-text"><%= line %></div><br/>
		<%
		    }
		  } catch (IOException e) {
		    e.printStackTrace();
		  } finally {
		    if (br != null) {
		      try {
		        br.close();
		      } catch (IOException e) {
		        e.printStackTrace();
		      }
		    }
		  }
		%>

  </div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />
