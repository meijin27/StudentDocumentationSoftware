<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 氏名・生年月日の設定変更用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>氏名・生年月日の変更</h1>
    </div>
    <div class="container">
		<form action="ChangeNameDateofBirth.action" method="post" autocomplete="off">
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
	        <div class="row">
	            <!-- 姓 -->
				<div class="col-md-6 mb-3">
				    <label class="form-label" for="lastName">名前</label>
				    <span class="required-label">必須</span>
				    <input class="form-control ${not empty requestScope['lastNameError'] ? 'error-input' : ''}" id="lastName" type="text" name="lastName" placeholder="田中" value="<c:out value='${lastName}' />" required>
    				<div class="d-flex align-items-center">
	    				<small class="text-muted">姓</small>
			        	<!-- エラー表示  -->
				        <c:set var="errorMsg" value="${requestScope['lastNameError']}" />
				        <c:if test="${not empty errorMsg}">
				            <div class="small-font red input-field margin-left-10" role="alert">
				                <c:out value="${errorMsg}" />
				            </div>
				        </c:if>
			        </div>
				</div>

	            <!-- 名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label invisible-text" for="firstName">名</label>
	                <input class="form-control ${not empty requestScope['firstNameError'] ? 'error-input' : ''}" type="text"id="firstName" name="firstName" placeholder="太郎" value="<c:out value='${firstName}' />" required>
    				<div class="d-flex align-items-center">
	    				<small class="text-muted">名</small>
			        	<!-- エラー表示  -->
				        <c:set var="errorMsg" value="${requestScope['firstNameError']}" />
				        <c:if test="${not empty errorMsg}">
				            <div class="small-font red input-field margin-left-10" role="alert">
				                <c:out value="${errorMsg}" />
				            </div>
				        </c:if>
			        </div>
	            </div>
   	            <!-- 姓（ふりがな） -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="lastNameRuby">ふりがな</label>
				    <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['lastNameRubyError'] ? 'error-input' : ''}" type="text" id="lastNameRuby" name="lastNameRuby" placeholder="たなか" value="<c:out value='${lastNameRuby}' />" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['lastNameRubyError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
	            <!-- 名（ふりがな） -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label invisible-text" for="firstNameRuby">名（ふりがな）</label>
	                <input class="form-control ${not empty requestScope['firstNameRubyError'] ? 'error-input' : ''}" type="text" id="firstNameRuby" name="firstNameRuby" placeholder="たろう" value="<c:out value='${firstNameRuby}' />" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['firstNameRubyError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
       			<p class="border-bottom"></p>
	            <!-- 生年月日 -->
	            <div class="col-md-4 mb-0">
	                <label class="form-label" for="birthYear">生年月日</label>
	                <span class="required-label">必須</span>
	                <select id="birthYear" name="birthYear" class="form-control ${not empty requestScope['birthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.birthYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-60; i <= currentYear - 14;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="birthMonth">月</label>
	                <select id="birthMonth" name="birthMonth" class="form-control ${not empty requestScope['birthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.birthMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="birthDay">日</label>
	                <select id="birthDay" name="birthDay" class="form-control ${not empty requestScope['birthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.birthDay}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	        	<!-- エラー表示  -->
	            <div class="col-md-12 mb-5">			        	
			        <c:set var="errorMsg" value="${requestScope['birthError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
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

