<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>学生種類・学籍番号・クラス・学年・組の変更</h1>
    </div>
    <div class="container">
		<form action="ChangeStudentInfo.action" method="post">
	        <div class="row">
	             <!-- 学生の種類 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="">学生の種類</label>
	                <span class="text-danger">*</span>
	                <select name="studentType" class="form-control" required>
	                    <option value="">-- 学生の種類 --</option>
	                    <option value="一般学生">一般学生</option>
	                    <option value="留学生">留学生</option>
	                    <option value="職業訓練生">職業訓練生</option>
	                </select>
	            </div>
	            <!-- 学籍番号 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="">学籍番号</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="studentNumber" placeholder="240001"value="${studentNumber}" required>
	            </div>
	            <!-- クラス名 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="">クラス名</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="className" placeholder="IT・ゲームソフト科"value="${className}" required>
	            </div>
	            <!-- 学年 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="">学年</label>
	                <span class="text-danger">*</span>
	                <select name="schoolYear" class="form-control" required>
	                    <option value="">-- 学年 --</option>
	                    <% for(int i=1; i <=2; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="">組</label>
	                <span class="text-danger">*</span>
	                <select name="classNumber" class="form-control" required>
	                    <option value="">-- 組 --</option>
	                    <% for(int i=1; i <=4; i++){ %>
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
	        <c:if test="${not empty studentNumberError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${studentNumberError}
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

