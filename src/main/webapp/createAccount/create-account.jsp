<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header/header-createAccount.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<main class="form-create w-100 m-auto text-center flex-shrink-0">
	<div class="container">
		<form action="CreateAccount.action" method="post">
	        <P>新規作成するアカウント名を入力してください</P>
		    <div class="form-floating">
		      <input type="text" class="form-control mb-3" id="account" name="account" placeholder="New Accont">
		      <label for="account">New Account</label>
		    </div>
		    <c:if test="${not empty accountError}">
		       <div class="alert alert-danger" role="alert">
		           ${accountError}
		       </div>
		    </c:if>               
		    <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
		</form>
	</div>
</main>

<%@include file="../footer/footer.jsp" %> 