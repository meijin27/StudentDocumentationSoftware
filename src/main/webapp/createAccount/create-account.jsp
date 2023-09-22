<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-createAccount.jsp" />
<%@ page import="java.util.UUID" %>

<!-- 新規アカウント名作成用JSP  -->
<main class="form-createAccount w-100 m-auto flex-shrink-0">

	<%
		// セッションからトークンを取得
		String csrfToken = (String) session.getAttribute("csrfToken");
		
		// セッションにトークンがない場合は新たに生成してセッションに格納
		if (csrfToken == null) {
		    csrfToken = UUID.randomUUID().toString();
		    session.setAttribute("csrfToken", csrfToken);
		}
	%>
	
	<div class="container">
		<form action="CreateAccount.action" method="post">
			<!-- 作成するアカウント名 -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="account">新規作成するアカウント名を入力してください</label>
                <span class="required-label">必須</span>
		        <input type="text" class="form-control mb-5" id="account" name="account" required>
		    </div>

		    <c:if test="${not empty accountError}">
		       <div class="alert alert-danger" role="alert">
		           ${accountError}
		       </div>
		    </c:if>      
		    <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
		    <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer.jsp" />
