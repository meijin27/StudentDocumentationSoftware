<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- パスワード変更用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>パスワードの変更</h1>
    </div>
    <div class="container">
		<form action="ChangePassword.action" method="post" autocomplete="off">
            <!-- 入力エラーがある場合のみエラーメッセージを表示 -->
            <div class="col-md-12 mb-5">
				<c:set var="hasError" value="false" />
	            <c:forEach var="attr" items="${pageContext.request.attributeNames}">
	                <c:if test="${fn:endsWith(attr, 'Error')}">
	                    <c:set var="hasError" value="true" />
	                </c:if>
	            </c:forEach>
		        <c:set var="innerErrorMsg" value="${requestScope['innerError']}" />
		        <c:if test="${not empty innerErrorMsg}">
					<div class="alert alert-danger text-center input-field" role="alert">
		                <STRONG><c:out value="${innerErrorMsg}" /></STRONG>
		            </div>
		        </c:if>        			          				
				<c:if test="${hasError and empty innerErrorMsg}">
                    <c:import url="/errorMessage/error-message.jsp" />
	            </c:if>
            </div>   

			<!-- パスワード -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="oldPassword">現在のパスワードを入力してください</label>
                <span class="required-label">必須</span>
				<input type="password" class="form-control" id="oldPassword" name="oldPassword" required> 
   	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['oldPasswordError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>							
			</div>			

			<p class="text-start mb-5 red"><strong>パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください</strong></p>

			<!-- 新パスワード -->
			<div class="col-md-12 mb-3">
			    <label class="form-label" for="password">新パスワードを入力してください</label>
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
   	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['passwordError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>		
			</div>
			<!-- パスワード再入力 -->			
            <div class="col-md-12 mb-5">
                <label class="form-label" for="passwordCheck">もう一度同じパスワードを入力してください</label>
                <span class="required-label">必須</span>
				<input type="password" class="form-control" id="passwordCheck"	name="passwordCheck" required> 
			</div>	
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">
			<!-- 変更ボタン  -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

