<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header/header-login.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<main class="form-login w-100 m-auto">
  <form action="Login.action" method="post">
    <img class="mb-4 mt-3" src="<%=request.getContextPath()%>/img/docu.png" alt="" width="72" height="57">
    <h1 class="h3 mb-3 fw-normal">提出書類作成ソフト</h1>

    <div class="form-floating">
      <input type="text" class="form-control" id="account" name="account" placeholder="Accont">
      <label for="floatingInput">Account</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="password" name="password" placeholder="Password">
      <label for="floatingPassword">Password</label>
    </div>
	<c:if test="${not empty loginError}">
	    <div class="alert alert-danger" role="alert">
	        ${loginError }
	    </div>
	</c:if>
	<c:if test="${not empty sessionScope.otherError}">
	    <div class="alert alert-danger" role="alert">
	        ${sessionScope.otherError}
	        <% session.removeAttribute("otherError"); %>
	    </div>
	</c:if>
    <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">Login</button>

    <a href="../createAccount/create-account.jsp" class="d-block mb-3">新規登録はこちら</a>
    <a href="../forgotPassword/seach-account.jsp">パスワードを忘れた方はこちら</a>
    
</main>

<%@include file="../footer.jsp" %> 
