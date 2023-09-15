<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-forgotPassword.jsp" />

<!-- パスワード忘却時パスワード再設定用JSP  -->
<main class="form-forgotPassword w-100 m-auto flex-shrink-0">
	<div class="container">
		<form action="RecreatePassword.action" method="post">
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

			<!-- エラー表示  -->
			<c:if test="${not empty passwordError}">
				<div class="alert alert-danger" role="alert">${passwordError}
				</div>
			</c:if>
		    <input type="hidden" name="encryptedId" value="<c:out value='${encryptedId}'/>">
		    <input type="hidden" name="master_key" value="<c:out value='${master_key}'/>">     
			<!-- サブミットボタン  -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">再登録</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer.jsp" />

