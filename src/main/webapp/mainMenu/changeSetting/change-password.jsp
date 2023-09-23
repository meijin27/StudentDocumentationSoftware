<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- パスワード変更用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>パスワードの変更</h1>
    </div>
    <div class="container">
		<form action="ChangePassword.action" method="post">

			<!-- パスワード -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="password">現在のパスワードを入力してください</label>
                <span class="required-label">必須</span>
				<input type="password" class="form-control" id="password" name="password" required> 
			</div>			

			<p class="text-start mb-5" style="color: red;"><strong>パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください</strong></p>

			<!-- 新パスワード -->
            <div class="col-md-12 mb-3">
                <label class="form-label" for="newPassword">新パスワードを入力してください</label>
                <span class="required-label">必須</span>
				<input type="password" class="form-control" id="newPassword" name="newPassword" required> 
			</div>
			<!-- パスワード再入力 -->			
            <div class="col-md-12 mb-5">
                <label class="form-label" for="passwordCheck">もう一度同じパスワードを入力してください</label>
                <span class="required-label">必須</span>
				<input type="password" class="form-control" id="passwordCheck"	name="passwordCheck" required> 
			</div>	


			<!-- エラー表示  -->
			<c:if test="${not empty passwordError}">
				<div class="alert alert-danger" role="alert">${passwordError}
				</div>
			</c:if>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">
			<!-- 変更ボタン  -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

