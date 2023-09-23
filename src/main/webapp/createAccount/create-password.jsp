<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-createAccount.jsp" />
<c:import url="/token/token.jsp" />

<!-- 新規アカウント用パスワード作成用JSP  -->
<main class="form-createAccount w-100 m-auto flex-shrink-0">
	<div class="container">
		<form action="CreatePassword.action" method="post">
			<p class="text-start mb-5" style="color: red;"><strong>パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください</strong></p>
			
			<!-- パスワード -->
            <div class="col-md-12 mb-3">
                <label class="form-label" for="password">パスワードを入力してください</label>
                <span class="required-label">必須</span>
				<input type="password" class="form-control" id="password" name="password" required> 
			</div>
			<!-- パスワード再入力 -->			
            <div class="col-md-12 mb-5">
                <label class="form-label" for="passwordCheck">もう一度同じパスワードを入力してください</label>
                <span class="required-label">必須</span>
				<input type="password" class="form-control" id="passwordCheck"	name="passwordCheck" required> 
			</div>			
			<c:if test="${not empty passwordError}">
				<div class="alert alert-danger" role="alert">${passwordError}
				</div>
			</c:if>
		    <!-- トークンの格納  -->
		    <input type="hidden" name="csrfToken" value="${csrfToken}">
		    <!-- 登録ボタン  -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">登録</button>
		    <!-- 戻るボタン  -->		
			<a href="create-account.jsp" class="w-100 btn btn-lg btn-secondary mb-3">戻る</a>
		</form>
	</div>
</main>

<c:import url="/footer/footer.jsp" />
