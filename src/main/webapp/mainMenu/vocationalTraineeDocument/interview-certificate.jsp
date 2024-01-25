<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「面接証明書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「面接証明書」作成</h1><br>
    </div>			  
	<form action="InterviewCertificate.action" method="post" autocomplete="off">
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
  	            <!-- 求人職種 -->
            <div class="col-md-4 mb-5">
                <label class="form-label" for="jobSearch">求人職種</label>
                <span class="required-label">必須</span>
                <input class="form-control ${not empty requestScope['jobSearchError'] ? 'error-input' : ''}" type="text" id="jobSearch" name="jobSearch" placeholder="製造業" value="<c:out value='${jobSearch}'/>" required>
	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['jobSearchError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>   	
            </div>
            <div class="col-md-8 mb-3"></div>   
            <!-- 面接年月日 -->
            <div class="col-md-4 mb-0">
                <label class="form-label" for="year">面接年月日</label>
                <select id="year" name="year" class="form-control ${not empty requestScope['dayError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.year}'/>">
                    <option value="" selected>-- 年 --</option>
                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
                        i++){ %>
                        <option value="<%= i %>">
                            令和<%= i %>年（<%= i+2018 %>年）
                        </option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-4 mb-0">
                <label class="form-label invisible-text" for="month">月</label>
                <select id="month" name="month" class="form-control ${not empty requestScope['dayError'] ? 'error-input' : ''} select-center  auto-select" data-selected-value="<c:out value='${param.month}'/>">
                    <option value="" selected>-- 月 --</option>
                    <% for(int i=1; i <=12; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>月
                        </option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-4 mb-0">
                <label class="form-label invisible-text" for="day">日</label>
                <select id="day" name="day" class="form-control ${not empty requestScope['dayError'] ? 'error-input' : ''} select-center  auto-select" data-selected-value="<c:out value='${param.day}'/>">
                    <option value="" selected>-- 日 --</option>
                    <% for(int i=1; i <=31; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>日
                        </option>
                    <% } %>
                </select>
            </div>	        
        	<!-- エラー表示  -->
            <div class="col-md-12 mb-3">			        	
		        <c:set var="errorMsg" value="${requestScope['dayError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>
            </div>      
			<!-- 面接開始時刻 -->
            <div class="col-md-4 mb-3">
                <label class="form-label" for="startHour">面接開始時刻</label>
                <select id="startHour" name="startHour" class="form-control ${not empty requestScope['startHourError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.startHour}'/>">
                    <option value="" selected>-- 時 --</option>
                    <% for(int i=0; i <=23; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>時
                        </option>
                    <% } %>
                </select>
 	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['startHourError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>   	
            </div>	
            <div class="col-md-8 mb-3"></div>

 				<!-- 面接終了時刻 -->
            <div class="col-md-4 mb-5">
                <label class="form-label" for="endHour">面接終了時刻</label>
                <select id="endHour" name="endHour" class="form-control ${not empty requestScope['endHourError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.endHour}'/>">
                    <option value="" selected>-- 時 --</option>
                    <% for(int i=0; i <= 23; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>時
                        </option>
                    <% } %>
                </select>
	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['endHourError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>   	
            </div>
           <div class="col-md-8 mb-5"></div>            	
        </div>  
	    <!-- トークンの格納  -->
		    <input type="hidden" name="csrfToken" value="${csrfToken}">			
		<!-- 作成ボタン -->
		<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
	</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
