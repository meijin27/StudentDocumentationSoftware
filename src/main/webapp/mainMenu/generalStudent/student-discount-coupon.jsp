<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「学割証発行願」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「学割証発行願」作成</h1><br>
    </div>			  
	<form action="StudentDiscountCoupon.action" method="post" autocomplete="off">
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
			<c:set var="errorMsg" value="${requestScope['totalPaymentError']}" />
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
            <!-- 申請年月日 -->
            <div class="col-md-4 mb-0">
                <label class="form-label" for="requestYear">申請年月日</label>
                <span class="required-label">必須</span>
                <select id="requestYear" name="requestYear" class="form-control ${not empty requestScope['requestError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.requestYear}'/>"  required>
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
		</div>
	    <% for(int set = 1; set <= 2; set++){ %>
		    <div class="row set <% if (set != 1) { %> additional-set hidden <% } %>" data-set="<%= set %>">
                <h3 class="border-bottom"><%= set %>枚目</h3>

	            <!-- エラー表示用の変数設定 -->  
				<%
					String paramSheetsRequired = "sheetsRequired"+ set;
				    request.setAttribute("paramSheetsRequired", paramSheetsRequired);				    
					String paramSheetsRequiredError = "sheetsRequired" + set + "Error";
				    request.setAttribute("paramSheetsRequiredError", paramSheetsRequiredError);
				    
					String paramStartingStation = "startingStation"+ set;
				    request.setAttribute("paramStartingStation", paramStartingStation);		
				    String paramStartingStationError = "startingStation" + set + "Error";
				    request.setAttribute("paramStartingStationError", paramStartingStationError);
		    
					String paramArrivalStation = "arrivalStation"+ set;
				    request.setAttribute("paramArrivalStation", paramArrivalStation);		
				    String paramArrivalStationError = "arrivalStation" + set + "Error";
				    request.setAttribute("paramArrivalStationError", paramArrivalStationError);				    
				    
					String paramIntendedUse = "intendedUse"+ set;
				    request.setAttribute("paramIntendedUse", paramIntendedUse);		
				    String paramIntendedUseError = "intendedUse" + set + "Error";
				    request.setAttribute("paramIntendedUseError", paramIntendedUseError);								    
				    
					String paramReason = "reason"+ set;
				    request.setAttribute("paramReason", paramReason);		
				    String paramReasonError = "reason" + set + "Error";
				    request.setAttribute("paramReasonError", paramReasonError);		
				%>
	            <!-- 必要枚数 -->           
				<div class="col-md-4 mb-3 text-center">
				    <label class="form-label" for="sheetsRequiredOne<%= set %>">必要枚数</label>
				    <span class="required-label">必須</span>
					<div class="d-flex align-items-center justify-content-center">
					    <div class="form-check form-check-inline mr-lg-5">
				            <input class="form-check-input" type="radio" name="sheetsRequired<%= set %>" id="sheetsRequiredOne<%= set %>" value="1" 
				                   <% if ("1".equals(request.getParameter("sheetsRequired" + set))) { %> checked <% } %>  <%= (set == 1) ? "required" : "" %> data-required="true">
				            <label class="form-check-label" for="sheetsRequiredOne<%= set %>">
				                １枚
				            </label>
				        </div>
					    <div class="form-check form-check-inline ml-lg-5">
				            <input class="form-check-input" type="radio" name="sheetsRequired<%= set %>" id="sheetsRequiredTwo<%= set %>" value="2" 
									<% if ("2".equals(request.getParameter("sheetsRequired" + set))) { %> checked <% } %>  <%= (set == 1) ? "required" : "" %> data-required="true">
				            <label class="form-check-label" for="sheetsRequiredTwo<%= set %>">
				                ２枚
				            </label>
				        </div>
				    </div>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope[paramSheetsRequiredError]}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 
				</div>
	            <div class="col-md-8 mb-3"></div>
	            <!-- 出発駅 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="startingStation<%= set %>">出発駅</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['paramStartingStationError'] ? 'error-input' : ''}" type="text" id="startingStation<%= set %>" name="startingStation<%= set %>" value="<c:out value='${requestScope[paramStartingStation]}'/>" placeholder="希望ヶ丘" <%= (set == 1) ? "required" : "" %> data-required="true">
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope[paramStartingStationError]}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 
	            </div>     	          	        
	            <!-- 到着駅 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="arrivalStation<%= set %>">到着駅</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['paramArrivalStationError'] ? 'error-input' : ''}" type="text" id="arrivalStation<%= set %>" name="arrivalStation<%= set %>" value="<c:out value='${requestScope[paramArrivalStation]}'/>" placeholder="大阪" <%= (set == 1) ? "required" : "" %> data-required="true">
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope[paramArrivalStationError]}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 
	            </div>     	   	        
	            <!-- 使用目的 -->  	        
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="intendedUse<%= set %>">使用目的</label>
	                <span class="required-label">必須</span>
	                <select id="intendedUse<%= set %>" name="intendedUse<%= set %>" class="form-control ${not empty requestScope['paramIntendedUseError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${requestScope[paramIntendedUse]}'/>"  <%= (set == 1) ? "required" : "" %> data-required="true">
	                    <option value="" disabled selected class="display_none">-- 使用目的 --</option>
	                    <option value="帰省">帰省</option>
	                    <option value="見学">見学</option>
	                    <option value="その他">その他</option>
	                </select>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope[paramIntendedUseError]}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 
	            </div>
	            <!-- その他の理由 -->
	            <div class="col-md-8 mb-5">
	                <label class="form-label" for="reason<%= set %>">使用目的がその他の場合は理由を記載</label>
	                <input class="form-control ${not empty requestScope['paramReasonError'] ? 'error-input' : ''}" type="text" id="reason<%= set %>" name="reason<%= set %>" value="<c:out value='${requestScope[paramReason]}'/>" placeholder="就職活動のため">
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope[paramReasonError]}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 
	            </div>     	 
 				<% if (set != 1) { %>
		            <!-- 削除ボタン -->
		            <div class="col-md-12 mb-5">
		                <button type="button" class="w-100 btn btn-lg btn-danger removeSetBtn border-bottom"><%= set %>枚目の削除</button>
		            </div>
	       		<% } %>
	        </div>  
        <% } %>    
		<!-- 追加ボタン -->
		<button type="button" id="addSetBtn" class="w-100 btn btn-lg btn-success mb-3">２枚目の追加（行先が複数ある場合）</button>
	    <!-- トークンの格納  -->
	    <input type="hidden" name="csrfToken" value="${csrfToken}">
		<!-- 作成ボタン -->
		<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
	</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
