<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<%@ page import="java.util.Base64" %>

<!-- 行為成功表示用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">

	<%
		// セッションから作成した書類名を取得
		String document = (String) session.getAttribute("document");
	
		// 変更箇所をリクエストに格納
		request.setAttribute("document", document); // リクエストスコープにセット

		// 変更箇所のセッションからの削除(フィルター機能によりセッションに変更箇所がない場合、このｊｓｐにアクセスできない)
		request.getSession().removeAttribute("document");	

	    byte[] pdfData = (byte[]) session.getAttribute("pdfData");
	    String pdfFilename = (String) session.getAttribute("pdfFilename");

	    // Convert to Base64
	    String base64Pdf = Base64.getEncoder().encodeToString(pdfData);
		
	%>
	
	<!-- PDFダウンロード用のjs -->
	<div id="pdfDataContainer" data-base64pdf="<%= base64Pdf %>" data-pdffilename="<%= pdfFilename %>" class="display_none"></div>
    <script src="<%=request.getContextPath()%>/js/PDFdownload.js"></script>
    
	<div class="container">
		<h3 class="pt-1 pb-5 pl-5 pr-5"><strong><c:out value="${document}を作成しました。"/></strong></h3>
		
		<a href="<%=request.getContextPath()%>/mainMenu/main-menu.jsp" class="w-100 btn btn-lg btn-primary mb-3">HOME</a>
	</div>
</main>


<c:import url="/footer/footer-main-menu.jsp" />

<%
    // Remove the PDF data from the session after providing the download link
    session.removeAttribute("pdfData");
    session.removeAttribute("pdfFilename");
%>