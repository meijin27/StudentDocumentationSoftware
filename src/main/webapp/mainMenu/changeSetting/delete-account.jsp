<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- アカウントの削除用JSP -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>アカウントの削除</h1>
    </div>
    <div class="container">
		<form action="DeleteAccount.action" method="post">
			<!-- パスワード -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="password">現在のパスワードを入力してください</label>
                <span class="required-label">必須</span>
				<input type="password" class="form-control" id="password" name="password" required> 
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
			<!-- 削除の実行ボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">削除の実行</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

