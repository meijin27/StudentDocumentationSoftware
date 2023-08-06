<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>氏名・生年月日の変更</h1>
    </div>
    <div class="container">
		<form action="ChangeNameDateofBirth.action" method="post">
	        <div class="row">
	            <!-- 姓 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">姓</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="lastName" placeholder="田中" value="${lastName}" required>
	            </div>
	            <!-- 名 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">名</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="firstName" placeholder="太郎" value="${firstName}" required>
	            </div>
   	            <!-- 姓（ふりがな） -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">姓（ふりがな）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="lastNameRuby" placeholder="たなか" value="${lastNameRuby}" required>
	            </div>
	            <!-- 名（ふりがな） -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">名（ふりがな）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="firstNameRuby" placeholder="たろう" value="${firstNameRuby}" required>
	            </div>
	        </div>
	        <div class="row">	        
	            <!-- 名（ふりがな） -->
	            <p>生年月日</p>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="birthYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="birthYear" class="form-control" required>
	                    <option value="">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-60; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="birthMonth">月</label>
	                <span class="text-danger">*</span>
	                <select name="birthMonth" class="form-control" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="birthDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="birthDay" class="form-control" required>
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
   	        <c:if test="${not empty  rubyError}">
	            <div class="alert alert-danger" role="alert">
	                ${rubyError}
	            </div>
	        </c:if>
	        <c:if test="${not empty  valueLongError}">
	            <div class="alert alert-danger" role="alert">
	                ${valueLongError}
	            </div>
	        </c:if>
	        <c:if test="${not empty dayError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${birthDayError}
	            </div>
	        </c:if>
	        <c:if test="${not empty innerError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${innerError}
	            </div>
	        </c:if>
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

