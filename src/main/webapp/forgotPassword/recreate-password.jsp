<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-forgotPassword.jsp" />
<c:import url="/token/token.jsp" />

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
			    <div class="password-helper lbls">
		            <p class="pb10">パスワードチェック項目</p>
		            <small class="req_min8 popup-small"><span data-feather="check"></span>最小８文字</small>
		            <small class="req_max32 popup-small"><span  data-feather="check"></span>最大３２文字</small>
		            <small class="req_uppercase popup-small"><span data-feather="check"></span>英大文字含む</small>
		            <small class="req_lowercase popup-small"><span data-feather="check"></span>英子文字含む</small>
		            <small class="req_number popup-small"><span data-feather="check"></span>数字含む</small>
			    </div>
			</div>
						
			<!-- パスワード再入力 -->			
            <div class="col-md-12 mb-5">
                <label class="form-label" for="passwordCheck">もう一度同じパスワードを入力してください</label>
                <span class="required-label">必須</span>
				<input type="password" class="form-control" id="passwordCheck"	name="passwordCheck" required> 
			</div>	

			<!-- エラー表示  -->
			<c:if test="${not empty passwordError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
					${passwordError}
				</div>
			</c:if>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">		    
			<!-- 再登録ボタン  -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">再登録</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer.jsp" />

