<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="container">
		<form action="ChangePassword.action" method="post">
			<p>現在のパスワードを入力してください<span class="text-danger">*</span></p>
			
			<div class="form-floating mb-5">
				<input type="password" class="form-control" id="password" name="password" placeholder="Password" required> 
				<label	for="password">Password</label>
			</div>			
			
			<p class="text-start" style="color: red;"><strong>パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください</strong></p>
			<p>新パスワードを入力してください<span class="text-danger">*</span></p>
			
			<div class="form-floating mb-3">
				<input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="New Password" required> 
				<label	for="newPassword">New Password</label>
			</div>
			<p>もう一度同じパスワードを入力してください<span class="text-danger">*</span></p>
			<div class="form-floating mb-5">
				<input type="password" class="form-control" id="passwordCheck"	name="passwordCheck" placeholder="Retype New Password" required> 
				<label for="passwordCheck">Retype Password</label>
			</div>
			<c:if test="${not empty passwordError}">
				<div class="alert alert-danger" role="alert">${passwordError}
				</div>
			</c:if>
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

