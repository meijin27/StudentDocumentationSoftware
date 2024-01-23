<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「学費延納願」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>「学費延納願」作成</h1><br>
    </div>			  
		<form action="PetitionForDeferredPayment.action" method="post" autocomplete="off">
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
   	            <!-- 願出年月日 -->
	            <div class="col-md-4 mb-0">
	                <label class="form-label" for="requestYear">願出年月日</label>
	                <span class="required-label">必須</span>
	                <select id="requestYear" name="requestYear" class="form-control ${not empty requestScope['requestError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.requestYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 令和　年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
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
  	            <!-- 納付できない理由 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="reason">納付できない理由</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['reasonError'] ? 'error-input' : ''}" type="text" id="reason" name="reason" placeholder="想定よりも就職活動による交通費等が発生し、学費を捻出できなかったため。" value="<c:out value='${reason}'/>" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['reasonError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>  
	            </div>
   	            <!-- 学費の捻出方法 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="howToRaiseFunds">学費の捻出方法</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['howToRaiseFundsError'] ? 'error-input' : ''}" type="text" id="howToRaiseFunds" name="howToRaiseFunds" placeholder="アルバイトを増やす。増やした分の給料にて学費を捻出する。" value="<c:out value='${howToRaiseFunds}'/>" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['howToRaiseFundsError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 
	            </div>
  	            <!-- 母国からの送金の有無（留学生のみ要選択） -->
  				<div class="col-md-7 mb-1 text-center">
				    <label class="form-label">母国からの送金の有無（留学生のみ要選択）</label>
					<div class="d-flex align-items-center justify-content-center margin-bottom-20">
					    <div class="form-check form-check-inline mr-lg-5">
					        <input class="form-check-input" type="radio" name="remittanceFromCountry" id="remittanceFromCountryYes" value="有" 
					               <% if ("有".equals(request.getParameter("remittanceFromCountry"))) { %> checked <% } %>>
					        <label class="form-check-label" for="remittanceFromCountryYes">
					            有
					        </label>
					    </div>
					    <div class="form-check form-check-inline ml-lg-5">
					        <input class="form-check-input" type="radio" name="remittanceFromCountry" id="remittanceFromCountryNo" value="無"
					               <% if ("無".equals(request.getParameter("remittanceFromCountry"))) { %> checked <% } %> >
					        <label class="form-check-label" for="remittanceFromCountryNo">
					            無
					        </label>
					    </div>
					</div>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['remittanceFromCountryError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 
				</div>
	            <div class="col-md-5 mb-3"></div>
  	            <!-- 母国からの送金がない理由（留学生のみ要記入） -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="reasonNoRemittance">母国からの送金がない理由（留学生のみ要記入）</label>
	                <input class="form-control ${not empty requestScope['reasonNoRemittanceError'] ? 'error-input' : ''}" type="text" id="reasonNoRemittance" name="reasonNoRemittance" placeholder="母国の家族も生計に余裕がなく、送金できる状態ではない。" value="<c:out value='${reasonNoRemittance}'/>">
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['reasonNoRemittanceError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 
	            </div>
  	            <!-- 海外送金依頼書INVOICE交付申請年月日 -->
  	            <p class="form-label">海外送金依頼書INVOICE交付申請年月日（留学生のみ要選択、母国からの送金がある場合に記入）</p>
	            <div class="col-md-4 mb-0">
	                <select id="invoiceYear" name="invoiceYear" class="form-control ${not empty requestScope['invoiceError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.invoiceYear}'/>">
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear-1; i <=currentYear; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <select id="invoiceMonth" name="invoiceMonth" class="form-control ${not empty requestScope['invoiceError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.invoiceMonth}'/>">
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <select id="invoiceDay" name="invoiceDay" class="form-control ${not empty requestScope['invoiceError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.invoiceDay}'/>">
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
  	        	<!-- エラー表示  -->
	            <div class="col-md-12 mb-3">			        	
			        <c:set var="errorMsg" value="${requestScope['invoiceError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>    
  	            <!-- 納付すべき金額（学費、教材費、積立金等） -->
  	            <p class="form-label">納付すべき金額（学費、教材費、積立金等）<span class="required-label">必須</span></p>
	            <div class="col-md-4 mb-3">
	                <input class="form-control ${not empty requestScope['amountPayableError'] ? 'error-input' : ''}" type="text" id="amountPayable" name="amountPayable" placeholder="1000000" value="<c:out value='${amountPayable}'/>" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['amountPayableError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 
	            </div>
	            <div class="col-md-8 mb-3"></div>
	            <!-- 通常納期年月日 -->
	            <div class="col-md-4 mb-0">
	                <label class="form-label" for="generalDeliveryYear">通常納期年月日</label>
	                <span class="required-label">必須</span>
	                <select id="generalDeliveryYear" name="generalDeliveryYear" class="form-control ${not empty requestScope['generalDeliveryError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.generalDeliveryYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 令和　年 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="generalDeliveryMonth">月</label>
	                <select id="generalDeliveryMonth" name="generalDeliveryMonth" class="form-control ${not empty requestScope['generalDeliveryError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.generalDeliveryMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="generalDeliveryDay">日</label>
	                <select id="generalDeliveryDay" name="generalDeliveryDay" class="form-control ${not empty requestScope['generalDeliveryError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.generalDeliveryDay}'/>" required>
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
			        <c:set var="errorMsg" value="${requestScope['generalDeliveryError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>    
	            <!-- 通常納期内納付学費等（教材費・積立金等を含む） -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="tuitionFeePaid">通常納期内納付学費等</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['tuitionFeePaidError'] ? 'error-input' : ''}" type="text" id="tuitionFeePaid" name="tuitionFeePaid" placeholder="250000" value="<c:out value='${tuitionFeePaid}'/>" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['tuitionFeePaidError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if> 
	            </div>
	         </div>   
             <% for(int set = 1; set <= 4; set++){ %>
			    <div class="row set <% if (set != 1) { %> additional-set hidden <% } %>" data-set="<%= set %>">
	               <h3 class="border-bottom"><%= set %>個目の納期と金額</h3>
	   	            <!-- 納付期限年月日 -->
		            <div class="col-md-4 mb-0">
		                <label class="form-label" for="deliveryYear<%= set %>">納付期限</label>
		                <span class="required-label">必須</span>
						<%
						    String paramDeliveryError = "delivery" + set + "Error";
						    request.setAttribute("paramDeliveryError", paramDeliveryError);
							String paramDeliveryYear = "deliveryYear"+ set;
						    request.setAttribute("paramDeliveryYear", paramDeliveryYear);
							String paramDeliveryMonth = "deliveryMonth"+ set;
						    request.setAttribute("paramDeliveryMonth", paramDeliveryMonth);						    
							String paramDeliveryDay = "deliveryDay"+ set;
						    request.setAttribute("paramDeliveryDay", paramDeliveryDay);						    
						%>
		                <select id="deliveryYear<%= set %>" name="deliveryYear<%= set %>" class="form-control ${not empty requestScope[paramDeliveryError] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${requestScope[paramDeliveryYear]}'/>" <%= (set == 1) ? "required" : "" %> data-required="true">
		                    <option value="" disabled selected class="display_none">-- 令和　年 --</option>
		                    <% for(int i=currentYear-2018; i <=currentYear-2017;
		                        i++){ %>
		                        <option value="<%= i %>">
		                            令和<%= i %>年（<%= i+2018 %>年）
		                        </option>
		                    <% } %>
		                </select>
		            </div>
		            <div class="col-md-4 mb-0">
		                <label class="form-label invisible-text" for="deliveryMonth<%= set %>">月</label>
		                <select id="deliveryMonth<%= set %>" name="deliveryMonth<%= set %>" class="form-control ${not empty requestScope[paramDeliveryError] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${requestScope[paramDeliveryMonth]}'/>" <%= (set == 1) ? "required" : "" %> data-required="true">
		                    <option value="" disabled selected class="display_none">-- 月 --</option>
		                    <% for(int i=1; i <=12; i++){ %>
		                        <option value="<%= i %>">
		                            <%= i %>月
		                        </option>
		                    <% } %>
		                </select>
		            </div>
		            <div class="col-md-4 mb-0">
		                <label class="form-label invisible-text" for="deliveryDay<%= set %>">日</label>
		                <select id="deliveryDay<%= set %>" name="deliveryDay<%= set %>" class="form-control ${not empty requestScope[paramDeliveryError] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${requestScope[paramDeliveryDay]}'/>" <%= (set == 1) ? "required" : "" %> data-required="true">
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
				        <c:set var="errorMsg" value="${requestScope[paramDeliveryError]}" />
				        <c:if test="${not empty errorMsg}">
				            <div class="small-font red input-field" role="alert">
				                <c:out value="${errorMsg}" />
				            </div>
				        </c:if>
		            </div>    
	  	            <!-- 延納金額 -->
		            <div class="col-md-4 mb-3">
		                <label class="form-label" for="deferredPaymentAmount<%= set %>">延納金額</label>
		                <span class="required-label">必須</span>
						<%
						    String paramDeferredPaymentAmountError = "deferredPaymentAmount" + set + "Error";
						    request.setAttribute("paramDeferredPaymentAmountError", paramDeferredPaymentAmountError);
							String paramDeferredPaymentAmount = "deferredPaymentAmount"+ set;
						    request.setAttribute("paramDeferredPaymentAmount", paramDeferredPaymentAmount);
						%>
		                <input class="form-control ${not empty requestScope[paramDeferredPaymentAmountError] ? 'error-input' : ''}" type="text" id="deferredPaymentAmount<%= set %>" name="deferredPaymentAmount<%= set %>" placeholder="750000" value="<c:out value='${requestScope[paramDeferredPaymentAmount]}'/>" <%= (set == 1) ? "required" : "" %> data-required="true">		            
			        	<!-- エラー表示  -->
				        <c:set var="errorMsg" value="${requestScope[paramDeferredPaymentAmountError]}" />
				        <c:if test="${not empty errorMsg}">
				            <div class="small-font red input-field" role="alert">
				                <c:out value="${errorMsg}" />
				            </div>
				        </c:if> 
					</div>
					<% if (set != 1) { %>
			            <!-- 削除ボタン -->
			            <div class="col-md-12 mb-5">
			                <button type="button" class="w-100 btn btn-lg btn-danger removeSetBtn border-bottom"><%= set %>個目の納期と金額の削除</button>
			            </div>
		       		<% } %>
		        </div>  
        	<% } %>		            
			<!-- 追加ボタン -->
			<button type="button" id="addSetBtn" class="w-100 btn btn-lg btn-success mb-3">納期と金額の追加（延納金を分割して納付する場合）</button>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">
			<!-- 作成ボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
