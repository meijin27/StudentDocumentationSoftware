<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-forgotPassword.jsp" />
<c:import url="/token/token.jsp" />

<!-- パスワード忘却時アカウント検索用JSP  -->
<main class="form-forgotPassword w-100 m-auto flex-shrink-0">
    <div class="container">
	    <form action="SearchAccount.action" method="post" autocomplete="off">
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
	        <!-- アカウント名 -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="account">パスワードを再発行するアカウント名を入力してください</label>
                <span class="required-label">必須</span>
		        <input type="text" class="form-control" id="account" name="account" required>
	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['accountError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>	
		    </div>

		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">		    
		    <!-- 次へボタン  -->
		    <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
        </form>
    </div>
</main>

<c:import url="/footer/footer.jsp" />
