<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-login.jsp" />

<!-- ログイン用JSP  -->
<main class="form-login w-100 m-auto">
    <form action="Login.action" method="post">
        <img class="mb-4 mt-3" src="<%=request.getContextPath()%>/img/docu.png" alt="" width="72" height="57">
        <h1 class="h3 mb-3 fw-normal">提出書類作成ソフト</h1>

        <div class="form-floating">
            <input type="text" class="form-control" id="account" name="account" placeholder="Accont" required>
            <label for="floatingInput">Account</label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
            <label for="floatingPassword">Password</label>
        </div>
        <!-- エラー表示  -->
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
	    <!-- サブミットボタン  -->
        <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">Login</button>
		<!-- 新規アカウント作成用JSPへのリンク  -->
	    <a href="<%=request.getContextPath()%>/createAccount/create-account.jsp" class="d-block mb-3">新規登録はこちら</a>
		<!-- パスワード忘却時パスワード再設定用JSPへのリンク  -->
        <a href="<%=request.getContextPath()%>/forgotPassword/search-account.jsp">パスワードを忘れた方はこちら</a>
    </form>
</main>

<c:import url="/footer/footer.jsp" />
