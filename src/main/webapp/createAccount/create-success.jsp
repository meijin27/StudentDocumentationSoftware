<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-createAccount.jsp" />

<!-- 新規アカウント作成成功JSP  -->
<main class="form-createAccount w-100 m-auto text-center flex-shrink-0">

	<%
		// セッションからアカウント名を取得
		String accountName = (String) session.getAttribute("accountName");
	
		// アカウント名をリクエストに格納
		request.setAttribute("accountName", accountName); // リクエストスコープにセット

		// セッションの削除(フィルター機能によりセッションがない場合、このｊｓｐにアクセスできない)
		request.getSession().invalidate();
		
	%>

	<div class="container">
		<h3 class="p-1">アカウントが作成されました</h3>
		<h3 class="pt-1 pb-5 pl-5 pr-5">アカウント名：<strong><c:out value="${accountName}" /></strong></h3>
		
		<a href="<%=request.getContextPath()%>/login/login.jsp" class="w-100 btn btn-lg btn-primary mb-3">Loginページへ</a>
	</div>
</main>


<c:import url="/footer/footer.jsp" />
