<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「補習受講申請書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「補習受講申請書」作成</h1><br>
    </div>			  
		<form action="SupplementaryLessons.action" method="post" autocomplete="off">
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
		        <c:set var="errorMsg" value="${requestScope['inputError']}" />
		        <c:if test="${not empty errorMsg and empty innerErrorMsg}">
					<div class="alert alert-danger text-center input-field" role="alert">
		                <STRONG><c:out value="${errorMsg}" /></STRONG>
		            </div>
		        </c:if>     
				<c:if test="${hasError and empty innerErrorMsg}">
                    <c:import url="/errorMessage/error-message.jsp" />
	            </c:if>
            </div>    	
					 		
	        <div class="row">
   	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-0">
	                <label class="form-label" for="requestYear">申請年月日</label>
	                <span class="required-label">必須</span>
	                <select id="requestYear" name="requestYear" class="form-control ${not empty requestScope['requestError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.requestYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-1; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="requestMonth">月</label>
	                <select id="requestMonth" name="requestMonth" class="form-control ${not empty requestScope['requestError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.requestMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="requestDay">日</label>
	                <select id="requestDay" name="requestDay" class="form-control ${not empty requestScope['requestError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.requestDay}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
	        	<!-- エラー表示  -->
	            <div class="col-md-12 mb-3">			        	
			        <c:set var="errorMsg" value="${requestScope['requestError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>    	    	            
   	            <!-- 補習受講年度 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="fiscalYear">補習受講年度</label>
	                <span class="required-label">必須</span>
	                <select id="fiscalYear" name="fiscalYear" class="form-control ${not empty requestScope['fiscalYearError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.fiscalYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 令和　年度 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年度（<%= i+2018 %>年度）
	                        </option>
	                    <% } %>
	                </select>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['fiscalYearError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>  
	            </div>
  	            <!-- 補習受講学期 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="semester">補習受講学期</label>
	                <span class="required-label">必須</span>
	                <select id="semester" name="semester" class="form-control ${not empty requestScope['semesterError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.semester}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 学期 --</option>
	                    <% for(int i=1; i <=4; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>学期
	                        </option>
	                    <% } %>
	                </select>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['semesterError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>  
	            </div>
  	            <!-- 担当教員名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="teacher">担当教員名</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['teacherError'] ? 'error-input' : ''}" type="text" id="teacher" name="teacher" placeholder="田中　角栄" value="<c:out value='${teacher}'/>" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['teacherError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>  
	            </div>
	            <div class="col-md-6 mb-3"></div>
  	            <!-- 教科名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="subjectName">教科名</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['subjectNameError'] ? 'error-input' : ''}" type="text" id="subjectName" name="subjectName" placeholder="AIアルゴリズム" value="<c:out value='${subjectName}'/>" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['subjectNameError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>  
	            </div>
	            <div class="col-md-6 mb-3"></div>
  	            <!-- 受講事由 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="reason">受講事由</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['reasonError'] ? 'error-input' : ''}" type="text" id="reason" name="reason" placeholder="新型コロナに罹患し、長期療養を行っていたため" value="<c:out value='${reason}'/>" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['reasonError']}" />
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
