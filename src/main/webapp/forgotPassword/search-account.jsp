<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-forgotPassword.jsp" />
<c:import url="/token/token.jsp" />

<!-- パスワード忘却時アカウント検索用JSP  -->
<main class="form-forgotPassword w-100 m-auto flex-shrink-0">
    <div class="container">
	    <form action="SearchAccount.action" method="post">
	        <!-- アカウント名 -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="account">パスワードを再発行するアカウント名を入力してください</label>
                <span class="required-label">必須</span>
		        <input type="text" class="form-control mb-5" id="account" name="account" required>
		    </div>
		    <!-- エラー表示  -->
		    <c:if test="${not empty accountError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
		            ${accountError}
		        </div>
		    </c:if>      
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">		    
		    <!-- 次へボタン  -->
		    <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
        </form>
    </div>
</main>

<c:import url="/footer/footer.jsp" />
