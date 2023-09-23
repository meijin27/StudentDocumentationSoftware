<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-forgotPassword.jsp" />

<!-- パスワード忘却時パスワード再設定成功JSP  -->
<main class="form-forgotPassword w-100 m-auto flex-shrink-0">

	<%
	
		// セッションの削除(フィルター機能によりセッションがない場合、このｊｓｐにアクセスできない)
		request.getSession().removeAttribute("recreateSuccess");	
		
	%>
	
	<div class="container">
		<h3 class="p-5">パスワードが再作成されました</h3>
		<a href="../login/login.jsp" class="w-100 btn btn-lg btn-primary mb-3">Loginページへ</a>
	</div>
</main>

<c:import url="/footer/footer.jsp" />
