<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「推薦書交付願」作成</h1><br>
    </div>			  
		<form action="RecommendedDelivery.action" method="post">
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
	                <label class="form-label" for="requestMonth">申請年月日</label>
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
	            <!-- 事由-->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="subject">事由</label>
	                <span class="text-danger">*</span>
	                <select name="subject" class="form-control select-center">
	                    <option value="">-- 事由 --</option>
                        <option value="就職試験">就職試験</option>
                        <option value="大学（編）入学試験">大学（編）入学試験</option>
                        <option value="その他">その他（要理由記載）</option>
	                </select>
	            </div>
   	            <!-- その他理由 -->
	            <div class="col-md-8 mb-5">
	                <label class="form-label" for="reason">事由にてその他を選択した場合の理由</label>
	                <input class="form-control" type="text" name="reason" placeholder="インターン申込" value="${reason}">
	            </div>
  	            <!-- 提出先 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="propose">提出先</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="propose" placeholder="アナハイム・エレクトロニクス" value="${propose}" required>
	            </div>
   	            <!-- 提出期限 -->
	            <div class="col-md-3 mb-5">
	                <label class="form-label invisible-text" for="deadlineYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="deadlineYear" class="form-control select-center" required>
	                    <option value="">-- 令和　年 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2018; i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-3 mb-5">
	                <label class="form-label" for="deadlineMonth">提出期限</label>
	                <span class="text-danger">*</span>
	                <select name="deadlineMonth" class="form-control select-center" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-3 mb-5">
	                <label class="form-label invisible-text" for="deadlineDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="deadlineDay" class="form-control select-center" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        

				<div class="col-md-3 mb-5">
	                <label class="form-label" for="nominationForm">推薦様式</label>
				    <span class="text-danger">*</span>
				    <div class="d-flex flex-column align-items-start"> 
				        <div class="form-check mb-2"> 
				            <input class="form-check-input" type="radio" name="nominationForm" id="schoolForm" value="本校書式" 
				                   <% if ("本校書式".equals(request.getParameter("nominationForm"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="schoolForm">
				                本校書式
				            </label>
				        </div>
				        <div class="form-check"> 
				            <input class="form-check-input" type="radio" name="nominationForm" id="submittedForm" value="提出先書式" 
									<% if ("提出先書式".equals(request.getParameter("nominationForm"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="nominationForm">
				                提出先書式
				            </label>
				        </div>
				    </div>
				</div>   
	        </div>      	        
	        <c:if test="${not empty nullError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${nullError}
	            </div>
	        </c:if>
	        <c:if test="${not empty  valueLongError}">
	            <div class="alert alert-danger" role="alert">
	                ${valueLongError}
	            </div>
	        </c:if>
	        <c:if test="${not empty dayError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${dayError}
	            </div>
	        </c:if>	        
			<c:if test="${not empty innerError}">
				<div class="alert alert-danger" role="alert">${innerError}
				</div>
			</c:if>
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
