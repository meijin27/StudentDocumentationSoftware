<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「傷病による欠席理由申立書」作成</h1><br>
    </div>			  
		<form action="AbsenceDueToInjuryOrIllness.action" method="post">
	        <div class="row">
   	            <!-- 病状 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="">病状</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="disease" placeholder="発熱・咳" value="${disease}" required>
	            </div>
		        <!-- 理由 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="">理由</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="reason" placeholder="病院を受診し、療養していた" value="${reason}" required>
	            </div>
	            <!-- 期間年月日（自） -->
	            <p>期間年月日（自）</p>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="startYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="startYear" class="form-control" required>
	                    <option value="">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-1; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="startMonth">月</label>
	                <span class="text-danger">*</span>
	                <select name="startMonth" class="form-control" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="startDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="startDay" class="form-control" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        
  	            <!-- 期間年月日（至） -->
	            <p>期間年月日（至）</p>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="endYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="endYear" class="form-control" required>
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear-1; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="endMonth">月</label>
	                <span class="text-danger">*</span>
	                <select name="endMonth" class="form-control" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="endDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="endDay" class="form-control" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        
	            <!-- 申請年月日 -->
	            <p>申請年月日</p>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="requestYear" class="form-control" required>
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear-1; i <=currentYear;
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
  	        <c:if test="${not empty logicalError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${logicalError}
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