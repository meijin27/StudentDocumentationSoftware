<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header_login.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 
<div class="container h-100">
    <div class="row justify-content-center align-items-center h-100">
        <div class="col-md-6 center-form">
            <form action="Login.action" method="post">
                <div class="form-group input-field">
                    <label for="acount">アカウント名</label>
                    <input type="text" id="account" name="account" class="form-control">
                </div>
                <div class="form-group input-field">
                    <label for="password">パスワード</label>
                    <input type="password" id="password" name="password" class="form-control">
                </div>
                <div class="form-group text-center input-field">
                    <input type="submit" value="ログイン" class="btn btn-primary md-3"/>
                </div>
                <c:if test="${not empty loginError}">
                    <div class="alert alert-danger text-center input-field" role="alert">
                        ${loginError }
                    </div>
                </c:if>
				<c:if test="${not empty sessionScope.otherError}">
				    <div class="alert alert-danger text-center input-field" role="alert">
				        ${sessionScope.otherError}
				        <% session.removeAttribute("otherError"); %>
				    </div>
				</c:if>                
                <div class="text-center input-field">
                    <a href="../createAccount/create-account.jsp" class="d-block mb-3">新規登録はこちら</a>
                    <a href="../forgotPassword/seach-account.jsp">パスワードを忘れた方はこちら</a>
                </div>
            </form>
        </div>
    </div>
</div>
 -->
<main class="form-signin w-100 m-auto">
  <form>
    <!-- Assuming the image is located at /docs/v1/assets/brand/bootstrap-logo.svg relative to your context path -->
    <img class="mb-4" src="<%=request.getContextPath()%>/docs/v1/assets/brand/bootstrap-logo.svg" alt="" width="72" height="57">
    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>

    <div class="form-floating">
      <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
      <label for="floatingInput">Email address</label>
    </div>
    <div class="form-floating">
      <input type="password" class="form-control" id="floatingPassword" placeholder="Password">
      <label for="floatingPassword">Password</label>
    </div>

    <div class="checkbox mb-3">
      <label>
        <input type="checkbox" value="remember-me"> Remember me
      </label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
    <!-- Using java.time.Year to get the current year -->
    <p class="mt-5 mb-3 text-muted">&copy; 2023–<%=java.time.Year.now()%></p>
  </form>
</main>


<%@include file="../footer.html" %> 
