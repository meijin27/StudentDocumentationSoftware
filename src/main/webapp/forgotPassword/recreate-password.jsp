<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-forgotPassword.jsp" />

<main class="form-forgot w-100 m-auto flex-shrink-0">
	<div class="container">
		<form action="RecreatePassword.action" method="post">
			<p class="text-start" style="color: red;"><strong>パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください</strong></p>
			<p>パスワードを入力してください</p>
			
			<div class="form-floating mb-3">
				<input type="password" class="form-control" id="password" name="password" placeholder="Password"> 
				<label	for="password">Password</label>
			</div>
			<p>もう一度同じパスワードを入力してください</p>
			<div class="form-floating mb-5">
				<input type="password" class="form-control" id="passwordCheck"	name="passwordCheck" placeholder="Retype Password"> 
				<label for="passwordCheck">Retype Password</label>
			</div>
			<c:if test="${not empty passwordError}">
				<div class="alert alert-danger" role="alert">${passwordError}
				</div>
			</c:if>
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">再登録</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer.jsp" />

