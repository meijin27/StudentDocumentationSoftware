<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「面接証明書」作成</h1><br>
    </div>			  
		<form action="InterviewCertificate.action" method="post">
	        <div class="row">
   	            <!-- 求人職種 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="">求人職種</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="jobSearch" placeholder="製造業" value="${jobSearch}" required>
	            </div>
	            <!-- 面接年月日（自） -->
	            <p>面接年月日（自）</p>
	            <div class="col-md-2 mb-3">
	                <label class="form-label" for="startYear">令和　年</label>
	                <span class="text-danger">*</span>
	                <select name="startYear" class="form-control" required>
	                    <option value="">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-3">
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
	            <div class="col-md-2 mb-3">
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
  				<!-- 午前・午後 -->
				<div class="col-md-4 mb-5">
				    <label class="form-label">午前・午後</label>
				    <span class="text-danger">*</span>
				    <div class="d-flex align-items-center justify-content-center">
				        <div class="form-check form-check-inline mr-lg-5">
				            <input class="form-check-input" type="radio" name="startForenoonOrMidday" id="startForenoon" value="午前" 
				                   <% if ("午前".equals(session.getAttribute("startForenoonOrMidday"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="startForenoon">
				                午前
				            </label>
				        </div>
				        <div class="form-check form-check-inline ml-lg-5">
				            <input class="form-check-input" type="radio" name="startForenoonOrMidday" id="startMidday" value="午後"
				                   <% if ("午後".equals(session.getAttribute("startForenoonOrMidday"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="startMidday">
				                午後
				            </label>
				        </div>
				    </div>
				</div>
	            <div class="col-md-2 mb-3">
	                <label class="form-label" for="startHour">時</label>
	                <span class="text-danger">*</span>
	                <select name="startHour" class="form-control" required>
	                    <option value="">-- 時 --</option>
	                    <% for(int i=0; i <=11; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
  	            <!-- 面接年月日（至） -->
	            <p>面接年月日（至）</p>
	            <div class="col-md-2 mb-3">
	                <label class="form-label" for="endYear">令和　年</label>
	                <span class="text-danger">*</span>
	                <select name="endYear" class="form-control" required>
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-3">
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
	            <div class="col-md-2 mb-3">
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
  				<!-- 午前・午後 -->
				<div class="col-md-4 mb-5">
				    <label class="form-label">午前・午後</label>
				    <span class="text-danger">*</span>
				    <div class="d-flex align-items-center justify-content-center">
				        <div class="form-check form-check-inline mr-lg-5">
				            <input class="form-check-input" type="radio" name="endForenoonOrMidday" id="endForenoon" value="午前" 
				                   <% if ("午前".equals(session.getAttribute("endForenoonOrMidday"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="endForenoon">
				                午前
				            </label>
				        </div>
				        <div class="form-check form-check-inline ml-lg-5">
				            <input class="form-check-input" type="radio" name="endForenoonOrMidday" id="endMidday" value="午後"
				                   <% if ("午後".equals(session.getAttribute("endForenoonOrMidday"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="endMidday">
				                午後
				            </label>
				        </div>
				    </div>
				</div>
	            <div class="col-md-2 mb-3">
	                <label class="form-label" for="endHour">時</label>
	                <span class="text-danger">*</span>
	                <select name="endHour" class="form-control" required>
	                    <option value="">-- 時 --</option>
	                    <% for(int i=0; i <=11; i++){ %>
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
