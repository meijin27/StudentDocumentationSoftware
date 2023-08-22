<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>

<!-- 住所と電話番号の設定変更用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>住所と電話番号の変更</h1>
    </div>
    <div class="container">
		<form action="ChangeAddressTel.action" method="post">
	        <div class="row">
	            <!-- 郵便番号 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="">郵便番号</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="postCode" placeholder="2310017" value="${postCode}" required>
	            </div>
	            
	            <!-- 電話番号 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="">電話番号</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="tel" placeholder="08011112222"value="${tel}" required>
	            </div>

	            <!-- 住所 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="">住所</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="address" placeholder="神奈川県横浜市中区港町１丁目１ 横浜スタジアム"value="${address}" required>
	            </div>
	        </div>
            <!-- エラー表示 -->
	        <c:if test="${not empty nullError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${nullError}
	            </div>
	        </c:if>
	        <c:if test="${not empty  valueLongError}">
	            <div class="alert alert-danger" role="alert">
	                ${valueLongError}
	            </div>
	        </c:if>
	        <c:if test="${not empty telError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${telError}
	            </div>
	        </c:if>
   	        <c:if test="${not empty postCodeError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${postCodeError}
	            </div>
	        </c:if>
  	        <c:if test="${not empty innerError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${innerError}
	            </div>
	        </c:if>
           <!-- サブミットボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

