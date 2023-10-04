<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-createAccount.jsp" />
<c:import url="/token/token.jsp" />

<!-- 新規アカウント用パスワード作成用JSP  -->
<main class="form-createAccount w-100 m-auto flex-shrink-0">
	<div class="container">
		<form action="CreatePassword.action" method="post" autocomplete="off">
			<p class="text-start mb-5 red"><strong>パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください</strong></p>
			
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
			<c:forEach var="attr" items="${pageContext.request.attributeNames}">
			    <c:set var="attrName" value="${attr}" />
			    <c:if test="${fn:endsWith(attrName, 'Error')}">
			        <c:set var="errorMsg" value="${requestScope[attrName]}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="alert alert-danger text-center input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
			    </c:if>
			</c:forEach>    
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
