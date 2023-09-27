<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-createAccount.jsp" />
<c:import url="/token/token.jsp" />

<!-- 新規アカウント名作成用JSP  -->
<main class="form-createAccount w-100 m-auto flex-shrink-0">
	<div class="container">
		<form action="CreateAccount.action" method="post">
			<!-- 作成するアカウント名 -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="account">新規作成するアカウント名を入力してください</label>
                <span class="required-label">必須</span>
		        <input type="text" class="form-control mb-5" id="account" name="account" required>
		    </div>

		    <c:if test="${not empty accountError}">
	           <div class="alert alert-danger text-center input-field" role="alert">
		           ${accountError}
		       </div>
		    </c:if>      
		    <!-- トークンの格納  -->
		    <input type="hidden" name="csrfToken" value="${csrfToken}">
             <!-- サブミットボタン  -->
		    <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer.jsp" />
