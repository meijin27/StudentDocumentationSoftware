<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 氏名・生年月日の設定変更用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>氏名・生年月日の変更</h1>
    </div>
    <div class="container">
		<form action="ChangeNameDateofBirth.action" method="post">
	        <div class="row">
	            <!-- 姓 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="lastName">名前</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="lastName" name="lastName" placeholder="田中" value="<c:out value='${lastName}'/>" required>
    				<small class="text-muted">姓</small>
	            </div>
	            <!-- 名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label invisible-text" for="firstName">名</label>
	                <input class="form-control" type="text" id="firstName" name="firstName" placeholder="太郎" value="<c:out value='${firstName}'/>" required>
    				<small class="text-muted">名</small>

	            </div>
   	            <!-- 姓（ふりがな） -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="lastNameRuby">ふりがな</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="lastNameRuby" name="lastNameRuby" placeholder="たなか" value="<c:out value='${lastNameRuby}'/>" required>
	            </div>
	            <!-- 名（ふりがな） -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label invisible-text" for="firstNameRuby">名（ふりがな）</label>
	                <input class="form-control" type="text" id="firstNameRuby" name="firstNameRuby" placeholder="たろう" value="<c:out value='${firstNameRuby}'/>" required>
	            </div>
       
	            <!-- 生年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="birthYear">生年月日</label>
	                <span class="required-label">必須</span>
	                <select id="birthYear" name="birthYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.birthYear}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-60; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="birthMonth">月</label>
	                <select id="birthMonth" name="birthMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.birthMonth}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="birthDay">日</label>
	                <select id="birthDay" name="birthDay" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.birthDay}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	      
	        </div>
            <!-- エラー表示 -->
	        <c:if test="${not empty nullError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${nullError}
	            </div>
	        </c:if>
   	        <c:if test="${not empty  rubyError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${rubyError}
	            </div>
	        </c:if>
	        <c:if test="${not empty  valueLongError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${valueLongError}
	            </div>
	        </c:if>
	        <c:if test="${not empty dayError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${dayError}
	            </div>
	        </c:if>
	        <c:if test="${not empty validationError}" >
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${validationError}
	            </div>
	        </c:if>	     	        
	        <c:if test="${not empty innerError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${innerError}
	            </div>
	        </c:if>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">	        
            <!-- 変更ボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

