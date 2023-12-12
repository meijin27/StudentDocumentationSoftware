<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「再試験受験申請書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「再試験受験申請書」作成</h1><br>
    </div>			  
		<form action="TakingReTest.action" method="post" autocomplete="off">
	        <!-- エラー表示  -->
			<c:forEach var="attr" items="${pageContext.request.attributeNames}">
			    <c:set var="attrName" value="${attr}" />
			    <c:if test="${fn:endsWith(attrName, 'Error')}">
			        <c:set var="errorMsg" value="${requestScope[attrName]}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="alert alert-danger text-center input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
			    </c:if>
			</c:forEach>  		
	        <div class="row">
   	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label " for="requestYear">申請年月日</label>
	                <span class="required-label">必須</span>
	                <select id="requestYear" name="requestYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.requestYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-1; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label invisible-text" for="requestMonth">月</label>
	                <select id="requestMonth" name="requestMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.requestMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label invisible-text" for="requestDay">日</label>
	                <select id="requestDay" name="requestDay" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.requestDay}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
   	            <!-- 再試験年度 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="fiscalYear">再試験年度</label>
	                <span class="required-label">必須</span>
	                <select id="fiscalYear" name="fiscalYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.fiscalYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 令和　年度 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年度（<%= i+2018 %>年度）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
  	            <!-- 再試験学期 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="semester">再試験学期</label>
	                <span class="required-label">必須</span>
	                <select id="semester" name="semester" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.semester}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 学期 --</option>
	                    <% for(int i=1; i <=4; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>学期
	                        </option>
	                    <% } %>
	                </select>
	            </div>
  	            <!-- 担当教員名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="teacher">担当教員名</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="teacher" name="teacher" placeholder="田中　角栄" value="<c:out value='${teacher}'/>" required>
	            </div>
	            <div class="col-md-6 mb-3"></div>
  	            <!-- 教科名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="subjectName">教科名</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="subjectName" name="subjectName" placeholder="AIアルゴリズム" value="<c:out value='${subjectName}'/>" required>
	            </div>
	            <div class="col-md-6 mb-3"></div>
  	            <!-- 受験事由 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="reason">受験事由</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="reason" name="reason" placeholder="勉強不足のため" value="<c:out value='${reason}'/>" required>
	            </div>
	        </div>	
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">
			<!-- 作成ボタン -->  
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
