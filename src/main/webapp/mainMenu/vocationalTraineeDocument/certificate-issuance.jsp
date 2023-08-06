<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「証明書交付願」作成</h1><br>
    </div>			  
		<form action="CertificateIssuance.action" method="post">
	        <div class="row">
   	            <!-- 申請理由（具体的に） -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="">申請理由（具体的に）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="reason" placeholder="国民年金保険料の学生納付特例制度申請のため" value="${reason}" required>
	            </div>
  	            <!-- 提出先 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="">提出先</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="propose" placeholder="厚木年金事務所" value="${propose}" required>
	            </div>
	            <!-- 交付区分-->
	            <p>必要な書類の枚数を選択してください</p>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="proofOfStudent">在校証明書</label>
	                <span class="text-danger">*</span>
	                <select name="proofOfStudent" class="form-control">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="certificateOfCompletion">修了証明書</label>
	                <span class="text-danger">*</span>
	                <select name="certificateOfCompletion" class="form-control">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="certificateOfExpectedCompletion">修了見込証明書</label>
	                <span class="text-danger">*</span>
	                <select name="certificateOfExpectedCompletion" class="form-control">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        
  	            <!-- 申請年月日 -->
	            <p>申請年月日</p>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestYear">令和　年</label>
	                <span class="text-danger">*</span>
	                <select name="requestYear" class="form-control" required>
	                    <option value="">-- 令和　年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestMonth">月</label>
	                <span class="text-danger">*</span>
	                <select name="requestMonth" class="form-control" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="requestDay" class="form-control" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
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
			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger" role="alert">${errorMessage}
				</div>
			</c:if>
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
