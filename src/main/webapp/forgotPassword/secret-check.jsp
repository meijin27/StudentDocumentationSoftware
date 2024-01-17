<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-forgotPassword.jsp" />
<c:import url="/token/token.jsp" />

<!-- パスワード忘却時秘密の質問の答えと生年月日確認用JSP  -->
<main class="form-forgotPassword w-100 m-auto flex-shrink-0">
	<div class="container">
	<h2 class="p-1">秘密の質問:</h2>
	<h2 class="pt-1 pb-5 pl-5 pr-5 red"><strong>${secretQuestion}</strong></h2>
        <form action="SecretCheck.action" method="post" autocomplete="off">
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
		        <c:set var="errorMsg" value="${requestScope['secretError']}" />
		        <c:if test="${not empty errorMsg}">
					<div class="alert alert-danger text-center input-field" role="alert">
		                <STRONG><c:out value="${errorMsg}" /></STRONG>
		            </div>
		        </c:if>     
				<c:if test="${hasError and empty errorMsg and empty innerErrorMsg}">
                    <c:import url="/errorMessage/error-message.jsp" />
	            </c:if>
            </div>    
            <div class="row">
                <!-- 秘密の質問の答え -->
                <div class="col-md-12 mb-3">
                    <label class="form-label" for="secretAnswer">秘密の質問の答えを入力してください</label>
                    <span class="required-label">必須</span>
                    <input class="form-control ${not empty requestScope['secretAnswerError'] ? 'error-input' : ''}" type="text" id="secretAnswer" name="secretAnswer" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['secretAnswerError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>                    
                </div>
                <!-- 生年月日 -->
                <div class="col-md-4 mb-0">
	                <label class="form-label" for="birthYear">生年月日</label>
                    <span class="required-label">必須</span>                    
				    <select id="birthYear"  name="birthYear" class="form-control ${not empty requestScope['birthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.birthYear}'/>" required>
				        <option value="" disabled selected class="display_none">--- 年 ---</option>
				        <% 
				        int currentYear = java.time.Year.now().getValue();
				        for(int i=currentYear-60; i <= currentYear - 14; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %>年</option>
				        <% 
				        } 
				        %>
				    </select>
				</div>
                <div class="col-md-4 mb-0">				
  	                <label class="form-label invisible-text" for="birthMonth">月</label>
				    <select id="birthMonth"  name="birthMonth" class="form-control ${not empty requestScope['birthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.birthMonth}'/>" required>
				        <option value="" disabled selected class="display_none">--- 月 ---</option>
				        <% 
				        for(int i=1; i <= 12; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %>月</option>
				        <% 
				        } 
				        %>
				    </select>
				</div>
                <div class="col-md-4 mb-0">
                    <label class="form-label invisible-text" for="birthDay">日</label>
				    <select id="birthDay" name="birthDay" class="form-control ${not empty requestScope['birthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.birthDay}'/>" required>
				        <option value="" disabled selected class="display_none">--- 日 ---</option>
				        <% 
				        for(int i=1; i <= 31; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %>日</option>
				        <% 
				        } 
				        %>
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
		    <!-- 次へボタン  -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
        </form>
    </main>

<c:import url="/footer/footer.jsp" />


