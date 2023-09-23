<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>

<!-- 設定変更成功表示用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">

	<%
		// セッションから変更箇所を取得
		String accountName = (String) session.getAttribute("changes");
	
		// 変更箇所をリクエストに格納
		request.setAttribute("changes", changes); // リクエストスコープにセット

		// 変更箇所のセッションからの削除(フィルター機能によりセッションに変更箇所がない場合、このｊｓｐにアクセスできない)
		request.getSession().removeAttribute("changes");	
		
	%>

	<div class="container">
		<h3 class="pt-1 pb-5 pl-5 pr-5"><strong><c:out value="${changes}"/></strong></h3>
		
		<a href="<%=request.getContextPath()%>/mainMenu/main-menu.jsp" class="w-100 btn btn-lg btn-primary mb-3">HOME</a>
	</div>
</main>


<c:import url="/footer/footer-main-menu.jsp" />
