<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-createAccount.jsp" />

<!-- 新規アカウント用パスワード作成用JSP  -->
<main class="form-createAccount w-100 m-auto flex-shrink-0">
	<div class="container">
		<form action="CreatePassword.action" method="post">
			<p class="text-start" style="color: red;"><strong>パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください</strong></p>
			<p>パスワードを入力してください<span class="text-danger">*</span></p>
			
			<div class="form-floating mb-3">
				<input type="password" class="form-control" id="password" name="password" placeholder="Password" required> 
				<label	for="password">Password</label>
			</div>
			<p>もう一度同じパスワードを入力してください<span class="text-danger">*</span></p>
			<div class="form-floating mb-5">
				<input type="password" class="form-control" id="passwordCheck"	name="passwordCheck" placeholder="Retype Password" required> 
				<label for="passwordCheck">Retype Password</label>
			</div>
			<c:if test="${not empty passwordError}">
				<div class="alert alert-danger" role="alert">${passwordError}
				</div>
			</c:if>
		    <input type="hidden" name="encryptedAccount" value="${encryptedAccount}">
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">登録</button>
			<a href="create-account.jsp" class="w-100 btn btn-lg btn-secondary mb-3">戻る</a>
		</form>
	</div>
</main>

<c:import url="/footer/footer.jsp" />
