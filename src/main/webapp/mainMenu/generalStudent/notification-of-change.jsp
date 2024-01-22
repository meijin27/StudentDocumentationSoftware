<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「変更届」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「変更届」作成</h1><br>
    </div>			  
		<form action="NotificationOfChange.action" method="post" autocomplete="off">
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
		        <c:set var="errorMsg" value="${requestScope['changeError']}" />
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
   	            <p class="left-align">・変更した項目を入力してください。</p>
   	            <p class="left-align">・氏名変更した場合は姓・名を全て入力してください。</p>
   	            <p class="left-align">・住所変更した場合は郵便番号と住所を入力してください。</p>
   	            <!-- 届出年月日 -->
	            <div class="col-md-4 mb-0">
	                <label class="form-label" for="requestYear">届出年月日</label>
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
	            <div class="col-md-12 mb-5">			        	
			        <c:set var="errorMsg" value="${requestScope['requestError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>                   
       			<p class="border-bottom"></p>	            
	            <!-- 変更対象者 -->  	        
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="changeSubject">変更対象者</label>
	                <span class="required-label">必須</span>
	                <select id="changeSubject" name="changeSubject" class="form-control ${not empty requestScope['changeSubjectError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.changeSubject}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 変更対象者 --</option>
	                    <option value="本人">本人</option>
	                    <option value="保護者">保護者</option>
	                    <option value="保証人">保証人</option>
	                </select>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['changeSubjectError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>  	                
	            </div>
	            <div class="col-md-8 mb-3"></div>
      			<p class="border-bottom"></p>	            
  	            <!-- 郵便番号 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="postCode">郵便番号</label>
	                <input class="form-control ${not empty requestScope['postCodeError'] ? 'error-input' : ''}" type="text" id="postCode" name="postCode" placeholder="2310017" value="<c:out value='${postCode}'/>">
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['postCodeError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>  
	            </div>
   	            <!-- 住所 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="address">住所</label>
	                <input class="form-control ${not empty requestScope['addressError'] ? 'error-input' : ''}" type="text" id="address" name="address" placeholder="秋田県秋田市飯島南字田尻堰越" value="<c:out value='${address}'/>">
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['addressError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>  
	            </div>
      			<p class="border-bottom"></p>
   	            <!-- 電話番号 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="tel">電話番号</label>
	                <input class="form-control ${not empty requestScope['telError'] ? 'error-input' : ''}" type="text" id="tel" name="tel" placeholder="08011112222"value="<c:out value='${tel}'/>">
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['telError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>  
	            </div>  
       			<p class="border-bottom"></p>
   	            <!-- 在留カード -->
	            <div class="col-md-4 mb-3">
	            	<p>在留カード</p>
	                <label class="form-label" for="residentCard">記号・番号</label>
	                <input class="form-control ${not empty requestScope['residentCardError'] ? 'error-input' : ''}" type="text" id="residentCard" name="residentCard" placeholder="AB12345678CD" value="<c:out value='${residentCard}'/>">
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['residentCardError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>  
	            </div>
	            <div class="col-md-8 mb-3"></div>
  	            <!-- 期間満了年月日 -->
	            <div class="col-md-4 mb-0">
	                <label class="form-label" for="endYear">期間満了年月日</label>
	                <select id="endYear" name="endYear" class="form-control ${not empty requestScope['endError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.endYear}'/>">
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear; i <=currentYear+2;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="endMonth">月</label>
	                <select id="endMonth" name="endMonth" class="form-control ${not empty requestScope['endError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.endMonth}'/>">
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="endDay">日</label>
	                <select id="endDay" name="endDay" class="form-control ${not empty requestScope['endError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.endDay}'/>">
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	        	<!-- エラー表示  -->
	            <div class="col-md-12 mb-5">			        	
			        <c:set var="errorMsg" value="${requestScope['endError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>    
       			<p class="border-bottom"></p>	            	     	            
 	            <!-- 姓 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="lastName">名前</label>
	                <input class="form-control ${not empty requestScope['lastNameError'] ? 'error-input' : ''}" type="text" id="lastName" name="lastName" placeholder="田中" value="<c:out value='${lastName}'/>">
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
	            <div class="col-md-6 mb-5">
	                <label class="form-label invisible-text" for="firstName">名</label>
	                <input class="form-control ${not empty requestScope['firstNameError'] ? 'error-input' : ''}" type="text" id="firstName" name="firstName" placeholder="太郎" value="<c:out value='${firstName}'/>">
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
	        </div>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">			
			<!-- 作成ボタン -->  
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
