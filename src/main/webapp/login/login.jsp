<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-login.jsp" />
<c:import url="/token/token.jsp" />

<!-- ログイン用JSP  -->
<main class="form-login w-100 m-auto">
    <form action="Login.action" method="post" autocomplete="off">
        <img class="mb-4 mt-3" src="<%=request.getContextPath()%>/img/docu.png" alt="" width="72" height="57">
        <h5 class="mb-1">提出書類作成ソフト</h5>
        <h1 class="h3 mb-3 big-font">ドキュメントクラフト</h1>
        <!-- エラー表示  -->
		<c:forEach var="attr" items="${pageContext.request.attributeNames}">
		    <c:set var="attrName" value="${attr}" />
		    <c:if test="${fn:endsWith(attrName, 'Error')}">
		        <c:set var="errorMsg" value="${requestScope[attrName]}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="alert alert-danger text-center input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>
		    </c:if>
		</c:forEach>  
	    <c:if test="${not empty otherError}">
	        <div class="alert alert-danger text-center input-field" role="alert">
	            ${otherError}
	            <% session.removeAttribute("otherError"); %>
	        </div>
	    </c:if>
	    
        <div class="form-floating">
            <input type="text" class="form-control" id="account" name="account" placeholder="Accont" required>
            <label for="account">Account</label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
            <label for="password">Password</label>
        </div>
	    <!-- トークンの格納  -->
	    <input type="hidden" name="csrfToken" value="${csrfToken}">	    
	    <!-- Loginボタン  -->
        <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">Login</button>
		<!-- 新規アカウント作成用JSPへのリンク  -->
	    <a href="<%=request.getContextPath()%>/createAccount/create-account.jsp" class="d-block mb-3">新規登録はこちら</a>
		<!-- パスワード忘却時パスワード再設定用JSPへのリンク  -->
        <a href="<%=request.getContextPath()%>/forgotPassword/search-account.jsp">パスワードを忘れた方はこちら</a>
    </form>
</main>

<c:import url="/footer/footer.jsp" />
