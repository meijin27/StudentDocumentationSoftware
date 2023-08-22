<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-forgotPassword.jsp" />

<!-- パスワード忘却時アカウント検索用JSP  -->
<main class="form-forgotPassword w-100 m-auto flex-shrink-0">
    <div class="container">
	    <form action="SeachAccount.action" method="post">
	        <p>アカウント名を入力してください<span class="text-danger">*</span><P>
		    <div class="form-floating">
		        <input type="text" class="form-control mb-5" id="account" name="account" placeholder="Accont" required>
		        <label for="account">Account</label>
		    </div>
		    <!-- エラー表示  -->
		    <c:if test="${not empty accountError}">
		        <div class="alert alert-danger" role="alert">
		            ${accountError}
		        </div>
		    </c:if>      
		    <!-- サブミットボタン  -->
		    <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
        </form>
    </div>
</main>

<c:import url="/footer/footer.jsp" />
