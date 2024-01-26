<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「親族続柄申立書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「親族続柄申立書」作成</h1><br>
    </div>			  
	<form action="PetitionForRelatives.action" method="post" autocomplete="off">
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

		<p class="text-start text-center red"><strong>当該書類は印刷後に手書きで親族該当箇所に〇を付けてください</strong></p>

        <div class="row">
  	            <!-- 親族氏名 -->
            <div class="col-md-6 mb-3">
                <label class="form-label" for="relativeName">親族氏名</label>
                <span class="required-label">必須</span>
                <input class="form-control ${not empty requestScope['relativeNameError'] ? 'error-input' : ''}" type="text" id="relativeName" name="relativeName" placeholder="鈴木　一郎" value="<c:out value='${relativeName}'/>" required>
	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['relativeNameError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>   	
            </div>
            <div class="col-md-6 mb-3"></div>	            
            <!-- 親族生年月日-->
            <div class="col-md-4 mb-0">
                <label class="form-label" for="birthYear">親族生年月日</label>
                <span class="required-label">必須</span>
                <select id="birthYear" name="birthYear" class="form-control ${not empty requestScope['birthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.birthYear}'/>" required>
                    <option value="" disabled selected class="display_none">-- 年 --</option>
                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-110; i <=currentYear;
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
            <div class="col-md-12 mb-3">			        	
		        <c:set var="errorMsg" value="${requestScope['birthError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>
            </div>    
            <!-- 親族住所 -->
            <div class="col-md-12 mb-3">
                <label class="form-label" for="relativeAddress">親族住所</label>
                <span class="required-label">必須</span>
                <input class="form-control ${not empty requestScope['relativeAddressError'] ? 'error-input' : ''}" type="text" id="relativeAddress" name="relativeAddress" placeholder="秋田県秋田市飯島南字田尻堰越" value="<c:out value='${relativeAddress}'/>" required>
	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['relativeAddressError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>  
            </div>
            <!-- 申請年月日 -->
            <div class="col-md-4 mb-0">
                <label class="form-label" for="requestYear">申請年月日</label>
                <span class="required-label">必須</span>
                <select id="requestYear" name="requestYear" class="form-control ${not empty requestScope['requestError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.requestYear}'/>" required>
                    <option value="" disabled selected class="display_none">-- 年 --</option>
                    <% for(int i=currentYear-1; i <=currentYear;
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
        </div> 
	    <!-- トークンの格納  -->
		    <input type="hidden" name="csrfToken" value="${csrfToken}">			
		<!-- 作成ボタン -->
		<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
	</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
