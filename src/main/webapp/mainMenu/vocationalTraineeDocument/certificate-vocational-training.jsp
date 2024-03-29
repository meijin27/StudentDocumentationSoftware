<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「公共職業訓練等受講証明書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「公共職業訓練等受講証明書」作成</h1><br>
    </div>			
		<form action="CertificateVocationalTraining.action" method="post" autocomplete="off">
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
  		    	<label class="form-label">日付ごとに該当する印を選択してください。</label>
   		    	<label class="form-label left-align">①公共職業訓練等を受けた日　無印</label>
  		    	<label class="form-label left-align">②公共職業訓練等が行われなかった日（日・祝日等）　＝印</label>
  		    	<label class="form-label left-align">③病気又は負傷により公共職業訓練等を受けなかった日　〇印</label>
  		    	<label class="form-label left-align">④やむを得ない理由により公共職業訓練等を受けなかった日　△印</label>
   		    	<label class="form-label left-align">⑤やむを得ない理由がなく公共職業訓練等を受けなかった日　✕印</label>
   		    	<label class="form-label left-align mb-3">⑥入校前、修了後、対校日翌日以降、カレンダー上存在しない日　／印</label>
        
				<% for (int i = 1; i <= 31; i++) { %>
		            <!-- エラー表示用の変数設定 -->  
					<%
						String paramDay = "day"+ i;
					    request.setAttribute("paramDay", paramDay);				    
						String paramDayError = "day" + i + "Error";
					    request.setAttribute("paramDayError", paramDayError);
					%>
				    <div class="col-md-2 mb-3">
				        <label for="day<%= i %>"><%= i %>日: </label>
				        <select class="form-control ${not empty requestScope[paramDayError] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${requestScope[paramDay]}'/>" id="day<%= i %>" name="day<%= i %>">
				            <option value="" <% if (paramDay.isEmpty()) { %> selected <% } %>>--無--</option>
				            <option value="＝" <% if ("＝".equals(paramDay)) { %> selected <% } %>>＝</option>
				            <option value="〇" <% if ("〇".equals(paramDay)) { %> selected <% } %>>〇</option>
				            <option value="△" <% if ("△".equals(paramDay)) { %> selected <% } %>>△</option>
				            <option value="✕" <% if ("✕".equals(paramDay)) { %> selected <% } %>>✕</option>
				            <option value="／" <% if ("／".equals(paramDay)) { %> selected <% } %>>／</option>
				        </select>
				    </div>
				<% } %>
	        	<!-- エラー表示  -->
	            <div class="col-md-12 mb-5">			        	
			        <c:set var="errorMsg" value="${requestScope['dayError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 	
				</div>
	        <div class="row">				
	            <!-- 証明書対象期間 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="subjectYear">証明書対象期間（令和　年　月）</label>
	                <span class="required-label">必須</span>
	                <select id="subjectYear" name="subjectYear" class="form-control ${not empty requestScope['subjectYearError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.subjectYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 令和　年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear - 2019; i <= currentYear - 2017;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['subjectYearError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>   	
	            </div>
	            <div class="col-md-6 mb-3">
	                <label class="form-label invisible-text" for="subjectMonth">月</label>
	                <select id="subjectMonth" name="subjectMonth" class="form-control ${not empty requestScope['subjectMonthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.subjectMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['subjectMonthError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>   	
	            </div>
				<!-- 就労・内職 -->
				<div class="col-md-12 mb-3 text-center">
				    <label class="form-label text-center">証明書対象期間中に就職、就労、内職又は手伝いをしましたか</label>
				    <span class="required-label">必須</span>
					<div class="d-flex align-items-center justify-content-center">
					    <div class="form-check form-check-inline mr-lg-5">
					        <input class="form-check-input" type="radio" name="problems" id="problemsYes" value="した" 
					               <% if ("した".equals(request.getParameter("problems"))) { %> checked <% } %> required>
					        <label class="form-check-label" for="problemsYes">
					            した
					        </label>
					    </div>
					    <div class="form-check form-check-inline ml-lg-5">
					        <input class="form-check-input" type="radio" name="problems" id="problemsNo" value="しない"
					               <% if ("しない".equals(request.getParameter("problems")) || request.getParameter("problems") == null) { %> checked <% } %> required>
					        <label class="form-check-label" for="problemsNo">
					            しない
					        </label>
					    </div>
					</div>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['problemsError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>   	
				</div>
				<!-- 収入 -->
				<div class="col-md-12 mb-5 text-center">
				    <label class="form-label">証明書対象期間中に内職又は手伝いをして収入を得ましたか</label>
				    <span class="required-label">必須</span>
				    <div class="d-flex align-items-center justify-content-center">
				        <div class="form-check form-check-inline mr-lg-5">
				            <input class="form-check-input" type="radio" name="income" id="incomeYes" value="得た" 
				                   <% if ("得た".equals(request.getParameter("income"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="incomeYes">
				                得た
				            </label>
				        </div>
				        <div class="form-check form-check-inline ml-lg-5">
				            <input class="form-check-input" type="radio" name="income" id="incomeNo" value="得ない"
				                   <% if ("得ない".equals(request.getParameter("income")) || request.getParameter("income") == null) { %> checked <% } %> required>
				            <label class="form-check-label" for="incomeNo">
				                得ない
				            </label>
				        </div>
				    </div>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['incomeError']}" />
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
