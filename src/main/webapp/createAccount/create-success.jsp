<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-createAccount.jsp" />


<main class="form-createAccount w-100 m-auto text-center flex-shrink-0">
	<div class="container">
		<h3 class="p-1">アカウントが作成されました</h3>
		<h3 class="pt-1 pb-5 pl-5 pr-5">アカウント名：<strong>${accountName}</strong></h3>
		
		<a href="<%=request.getContextPath()%>/login/login.jsp" class="w-100 btn btn-lg btn-primary mb-3">Loginページへ</a>
	</div>
</main>


<c:import url="/footer/footer.jsp" />
