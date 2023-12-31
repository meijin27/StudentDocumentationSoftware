<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 住所と電話番号の設定変更用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>住所と電話番号の変更</h1>
    </div>
    <div class="container">
		<form action="ChangeAddressTel.action" method="post" autocomplete="off">
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
		
	        <div class="row">
	            <!-- 郵便番号 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="postCode">郵便番号</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="postCode" name="postCode" placeholder="2310017" value="<c:out value='${postCode}'/>" required>
	            </div>
	            <div class="col-md-9 mb-3"></div>	            


	            <!-- 住所 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="address">住所</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text"id="address" name="address" placeholder="神奈川県横浜市中区港町１丁目１ 横浜スタジアム"value="<c:out value='${address}'/>" required>
	            </div>
	            
	            <!-- 電話番号 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="tel">電話番号</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="tel" name="tel" placeholder="08011112222"value="<c:out value='${tel}'/>" required>
	            </div>	            
	        </div>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">
            <!-- 変更ボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

