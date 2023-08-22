<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>

<!-- 「学費延納願」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「学費延納願」作成</h1><br>
    </div>			  
		<form action="PetitionForDeferredPayment.action" method="post">
	        <div class="row">
   	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="requestYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="requestYear" class="form-control select-center" required>
	                    <option value="">-- 令和　年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestMonth">願出年月日</label>
	                <span class="text-danger">*</span>
	                <select name="requestMonth" class="form-control select-center" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="requestDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="requestDay" class="form-control select-center" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
  	            <!-- 納付できない理由 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="reason">納付できない理由</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="reason" placeholder="想定よりも就職活動による交通費等が発生し、学費を捻出できなかったため。" value="${reason}" required>
	            </div>
   	            <!-- 学費の捻出方法 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="howToRaiseFunds">学費の捻出方法</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="howToRaiseFunds" placeholder="アルバイトを増やす。増やした分の給料にて学費を捻出する。" value="${howToRaiseFunds}" required>
	            </div>
  	            <!-- 母国からの送金の有無（留学生のみ要選択） -->
  				<div class="col-md-12 mb-5">
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
				</div>
  	            <!-- 母国からの送金がない理由（留学生のみ要記入） -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="reasonNoRemittance">母国からの送金がない理由（留学生のみ要記入）</label>
	                <input class="form-control" type="text" name="reasonNoRemittance" placeholder="母国の家族も生計に余裕がなく、送金できる状態ではない。" value="${reasonNoRemittance}">
	            </div>
  	            <!-- 海外送金依頼書INVOICE交付申請年月日 -->
  	            <p>海外送金依頼書INVOICE交付申請年月日（留学生のみ要選択、母国からの送金がある場合に記入）</p>
	            <div class="col-md-4 mb-5">
	                <select name="invoiceYear" class="form-control select-center">
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear-1; i <=currentYear; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <select name="invoiceMonth" class="form-control select-center">
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <select name="invoiceDay" class="form-control select-center">
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
  	            <!-- 納付すべき金額（学費、教材費、積立金等） -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="amountPayable">納付すべき金額（学費、教材費、積立金等）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="amountPayable" placeholder="1000000" value="${amountPayable}" required>
	            </div>
	            <!-- 通常納期内納付学費等（教材費・積立金等を含む） -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="generalDeliveryYear">通常納期年月日</label>
	                <span class="text-danger">*</span>
	                <select name="generalDeliveryYear" class="form-control select-center" required>
	                    <option value="">-- 令和　年 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-5">
	                <label class="form-label invisible-text" for="generalDeliveryMonth">月</label>
	                <span class="text-danger">*</span>
	                <select name="generalDeliveryMonth" class="form-control select-center" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-5">
	                <label class="form-label invisible-text" for="generalDeliveryDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="generalDeliveryDay" class="form-control select-center" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>		            
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="tuitionFeePaid">通常納期内納付学費等</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="tuitionFeePaid" placeholder="250000" value="${tuitionFeePaid}">
	            </div>
	         </div>   
             <% for(int set = 1; set <= 4; set++){ %>
			    <div class="row set <% if (set != 1) { %> additional-set hidden <% } %>" data-set="<%= set %>">
	               <h3 class="border-bottom"><%= set %>個目の納期と金額</h3>
	   	            <!-- 納付期限年月日 -->
		            <div class="col-md-4 mb-5">
		                <label class="form-label" for="deliveryYear<%= set %>">納付期限</label>
		                <span class="text-danger">*</span>
		                <select name="deliveryYear<%= set %>" class="form-control select-center" <%= (set == 1) ? "required" : "" %> data-required="true">
		                    <option value="">-- 令和　年 --</option>
		                    <% for(int i=currentYear-2018; i <=currentYear-2017;
		                        i++){ %>
		                        <option value="<%= i %>">
		                            令和<%= i %>年（<%= i+2018 %>年）
		                        </option>
		                    <% } %>
		                </select>
		            </div>
		            <div class="col-md-2 mb-5">
		                <label class="form-label invisible-text" for="deliveryMonth<%= set %>">月</label>
		                <span class="text-danger">*</span>
		                <select name="deliveryMonth<%= set %>" class="form-control select-center" <%= (set == 1) ? "required" : "" %> data-required="true">
		                    <option value="">-- 月 --</option>
		                    <% for(int i=1; i <=12; i++){ %>
		                        <option value="<%= i %>">
		                            <%= i %>月
		                        </option>
		                    <% } %>
		                </select>
		            </div>
		            <div class="col-md-2 mb-5">
		                <label class="form-label invisible-text" for="deliveryDay<%= set %>">日</label>
		                <span class="text-danger">*</span>
		                <select name="deliveryDay<%= set %>" class="form-control select-center" <%= (set == 1) ? "required" : "" %> data-required="true">
		                    <option value="">-- 日 --</option>
		                    <% for(int i=1; i <=31; i++){ %>
		                        <option value="<%= i %>">
		                            <%= i %>日
		                        </option>
		                    <% } %>
		                </select>
		            </div>
	  	            <!-- 延納金額 -->
		            <div class="col-md-4 mb-5">
		                <label class="form-label" for="deferredPaymentAmount<%= set %>">延納金額</label>
		                <span class="text-danger">*</span>
		                <input class="form-control" type="text" name="deferredPaymentAmount<%= set %>" placeholder="750000" <%= (set == 1) ? "required" : "" %> data-required="true">
		            </div>	 
					<% if (set != 1) { %>
			            <!-- 削除ボタン -->
			            <div class="col-md-12 mb-5">
			                <button type="button" class="w-100 btn btn-lg btn-danger removeSetBtn border-bottom"><%= set %>個目の納期と金額の削除</button>
			            </div>
		       		<% } %>
		        </div>  
        	<% } %>		            
		    <!-- エラー表示 -->        
	        <c:if test="${not empty nullError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${nullError}
	            </div>
	        </c:if>
	        <c:if test="${not empty dayError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${dayError}
	            </div>
	        </c:if>	        
            <c:if test="${not empty  valueLongError}">
            	<div class="alert alert-danger" role="alert">
                	${valueLongError}
            	</div>
        	</c:if>
	        <c:if test="${not empty numberError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${numberError}
	            </div>
	        </c:if>	        
            <c:if test="${not empty  exchangeStudentError}">
            	<div class="alert alert-danger" role="alert">
                	${exchangeStudentError}
            	</div>
        	</c:if>        	
			<c:if test="${not empty innerError}">
				<div class="alert alert-danger" role="alert">${innerError}
				</div>
			</c:if>
			<!-- 追加ボタン -->
			<button type="button" id="addSetBtn" class="w-100 btn btn-lg btn-success mb-3">納期と金額の追加（延納金を分割して納付する場合）</button>
			<!-- サブミットボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
