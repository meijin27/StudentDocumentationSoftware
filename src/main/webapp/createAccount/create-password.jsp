<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../header/header-createAccount.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<main class="form-create w-100 m-auto text-center flex-shrink-0">
	<div class="container">
		<form action="CreatePassword.action" method="post">
			<p style="color: red;"><strong>パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください</strong></p>
			<p>パスワードを入力してください</p>
			
			<div class="form-floating mb-3">
				<input type="password" class="form-control" id="password" name="password" placeholder="Password"> 
				<label	for="password">Password</label>
			</div>
			<p>もう一度同じパスワードを入力してください</p>
			<div class="form-floating mb-3">
				<input type="password" class="form-control" id="passwordCheck"	name="passwordCheck" placeholder="Retype Password"> 
				<label for="passwordCheck">Retype Password</label>
			</div>
			<c:if test="${not empty passwordError}">
				<div class="alert alert-danger" role="alert">${passwordError}
				</div>
			</c:if>
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">登録</button>
			<a href="create-account.jsp" class="w-100 btn btn-lg btn-secondary mb-3">戻る</a>
		</form>
	</div>
</main>

<%@include file="../footer.jsp"%>
