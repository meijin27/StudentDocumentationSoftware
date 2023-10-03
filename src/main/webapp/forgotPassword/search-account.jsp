<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-forgotPassword.jsp" />
<c:import url="/token/token.jsp" />

<!-- パスワード忘却時アカウント検索用JSP  -->
<main class="form-forgotPassword w-100 m-auto flex-shrink-0">
    <div class="container">
	    <form action="SearchAccount.action" method="post" autocomplete="off">
	        <!-- アカウント名 -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="account">パスワードを再発行するアカウント名を入力してください</label>
                <span class="required-label">必須</span>
		        <input type="text" class="form-control mb-5" id="account" name="account" required>
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
		    <!-- 次へボタン  -->
		    <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
        </form>
    </div>
</main>

<c:import url="/footer/footer.jsp" />
