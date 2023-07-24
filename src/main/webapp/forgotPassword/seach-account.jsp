<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header/header-forgotPassword.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<main class="form-forgot w-100 m-auto flex-shrink-0">
	<div class="container">
		<form action="SeachAccount.action" method="post">
	        <p>アカウント名を入力してください<P>
		    <div class="form-floating">
		      <input type="text" class="form-control mb-5" id="account" name="account" placeholder="Accont">
		      <label for="account">Account</label>
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

<%@include file="../footer.jsp" %> 