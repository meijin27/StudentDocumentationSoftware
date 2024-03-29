<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「就労証明書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「就労証明書」作成</h1><br>
    </div>			
		<form action="CertificateOfEmployment.action" method="post" autocomplete="off">
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
				
		<p class="text-start text-center red"><strong>当該書類は印刷後、就労先にて太枠内の記入をお願いします。</strong></p>

        <div class="row">
            <div class="col-md-4 mb-3">
                <label class="form-label" for="firstMonth">就労した月</label>
                <span class="required-label">必須</span>
                <select id="firstMonth" name="firstMonth" class="form-control ${not empty requestScope['firstMonthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.firstMonth}'/>" required>
                    <option value="" disabled selected class="display_none">-- 月 --</option>
                    <% for(int i=1; i <=12; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>月
                        </option>
                    <% } %>
                </select>
	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['firstMonthError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if> 
            </div>	     
            <div class="col-md-8 mb-3"></div>   
 		    <p>就労日を選択してください</p>
			<% for (int i = 1; i <= 31; i++) { %>
	            <!-- エラー表示用の変数設定 -->  
				<%
					String paramFirstMonthDay = "firstMonthDay"+ i;
				    request.setAttribute("paramFirstMonthDay", paramFirstMonthDay);				    
					String paramFirstMonthDayError = "firstMonthDay" + i + "Error";
				    request.setAttribute("paramFirstMonthDayError", paramFirstMonthDayError);
				%>

			    <div class="col-md-2 mb-1 d-flex align-items-center">
			        <label class="me-2 mb-0 width-40" for="firstMonthDay<%= i %>"><%= i %>日:</label>
			        <input class="form-check-input mt-0 mb-0  ${not empty requestScope[paramFirstMonthDayError] ? 'error-input' : ''}" type="checkbox" id="firstMonthDay<%= i %>" name="firstMonthDay<%= i %>" value="〇" <% if ("〇".equals(request.getParameter(paramFirstMonthDay))) { %> checked <% } %> >
			    </div>
			<% } %>
        	<!-- エラー表示  -->
        	<div class="col-md-12 mb-3">
		        <c:set var="errorMsg" value="${requestScope['firstMonthDayError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if> 				
			</div>
		</div>
		<div class="row">
 		    	<p class="text-start text-center red margin-top-50"><strong>一か月分を作成する場合は下記は選択しないでください</strong></p>
            <div class="col-md-4 mb-3">
                <label class="form-label" for="secondMonth">就労した月（二か月目）</label>
                <select id="secondMonth" name="secondMonth" class="form-control ${not empty requestScope['secondMonthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.secondMonth}'/>">
                    <option value="">-- 月 --</option>
                    <% for(int i=1; i <=12; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>月
                        </option>
                    <% } %>
                </select>
	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['secondMonthError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if> 
            </div>	        
            <div class="col-md-8 mb-3"></div>   
 		    	<p>就労日を選択してください（二か月目）</p>
			<% for (int i = 1; i <= 31; i++) { %>
	            <!-- エラー表示用の変数設定 -->  
				<%
					String paramSecondMonthDay = "secondMonthDay"+ i;
				    request.setAttribute("paramSecondMonthDay", paramSecondMonthDay);				    
					String paramSecondMonthDayError = "secondMonthDay" + i + "Error";
				    request.setAttribute("paramSecondMonthDayError", paramSecondMonthDayError);
				%>
			    <div class="col-md-2 mb-1 d-flex align-items-center">
			        <label class="me-2 mb-0 width-40" for="secondMonthDay<%= i %>"><%= i %>日:</label>
			        <input class="form-check-input mt-0 mb-0  ${not empty requestScope[paramSecondMonthDayError] ? 'error-input' : ''}" type="checkbox" id="secondMonthDay<%= i %>" name="secondMonthDay<%= i %>" value="〇" <% if ("〇".equals(request.getParameter(paramSecondMonthDay))) { %> checked <% } %> >
			    </div>
			<% } %>
        	<!-- エラー表示  -->
        	<div class="col-md-12 mb-5">
		        <c:set var="errorMsg" value="${requestScope['secondMonthDayError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if> 		
		    </div>  
        </div>
	    <!-- トークンの格納  -->
		    <input type="hidden" name="csrfToken" value="${csrfToken}">			
		<!-- 作成ボタン -->
		<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
	</form>
</main>

<c:import url="/footer/footer-main-menu.jsp" />