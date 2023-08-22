<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>

<!-- 「親族続柄申立書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「親族続柄申立書」作成</h1><br>
    </div>			  
		<form action="PetitionForRelatives.action" method="post">
			<p class="text-start text-center" style="color: red;"><strong>当該書類は印刷後に手書きで親族該当箇所に〇を付けてください</strong></p>

	        <div class="row">
   	            <!-- 親族氏名 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="">親族氏名</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="relativeName" placeholder="鈴木　一郎" value="${relativeName}" required>
	            </div>
	            <!-- 親族生年月日-->
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="birthYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="birthYear" class="form-control select-center" required>
	                    <option value="">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-110; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="birthMonth">親族生年月日</label>
	                <span class="text-danger">*</span>
	                <select name="birthMonth" class="form-control select-center" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="birthDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="birthDay" class="form-control select-center" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        
   	            <!-- 親族住所 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="">親族住所</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="relativeAddress" placeholder="秋田県秋田市飯島南字田尻堰越" value="${relativeAddress}" required>
	            </div>
  	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="requestYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="requestYear" class="form-control select-center" required>
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear-1; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
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
	        </div> 
	        <!-- エラー表示  -->     	        
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
			<!-- サブミットボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
