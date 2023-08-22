<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>

<!-- 「補習受講申請書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「補習受講申請書」作成</h1><br>
    </div>			  
		<form action="SupplementaryLessons.action" method="post">
	        <div class="row">
   	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="requestYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="requestYear" class="form-control select-center" required>
	                    <option value="">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-1; i <=currentYear;
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
   	            <!-- 補習受講年度 -->
	            <div class="col-md-5 mb-5">
	                <label class="form-label" for="fiscalYear">補習受講年度</label>
	                <span class="text-danger">*</span>
	                <select name="fiscalYear" class="form-control select-center" required>
	                    <option value="">-- 令和　年度 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年度（<%= i+2018 %>年度）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
  	            <!-- 補習受講学期 -->
	            <div class="col-md-3 mb-5">
	                <label class="form-label" for="semester">補習受講学期</label>
	                <span class="text-danger">*</span>
	                <select name="semester" class="form-control select-center" required>
	                    <option value="">-- 学期 --</option>
	                    <% for(int i=1; i <=4; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>学期
	                        </option>
	                    <% } %>
	                </select>
	            </div>
  	            <!-- 担当教員名 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="teacher">担当教員名</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="teacher" placeholder="田中　角栄" value="${teacher}" required>
	            </div>
  	            <!-- 教科名 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="subjectName">教科名</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="subjectName" placeholder="AIアルゴリズム" value="${subjectName}" required>
	            </div>
  	            <!-- 受講事由 -->
	            <div class="col-md-8 mb-5">
	                <label class="form-label" for="reason">受講事由</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="reason" placeholder="新型コロナに罹患し、長期療養を行っていたため" value="${reason}" required>
	            </div>
	        </div>	    
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
			<c:if test="${not empty innerError}">
				<div class="alert alert-danger" role="alert">${innerError}
				</div>
			</c:if>
			<!-- サブミットボタン -->  
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
