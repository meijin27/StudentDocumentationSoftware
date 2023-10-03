<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「面接証明書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「面接証明書」作成</h1><br>
    </div>			  
	<form action="InterviewCertificate.action" method="post" autocomplete="off">
        <div class="row">
  	            <!-- 求人職種 -->
            <div class="col-md-4 mb-5">
                <label class="form-label" for="jobSearch">求人職種</label>
                <span class="required-label">必須</span>
                <input class="form-control" type="text" id="jobSearch" name="jobSearch" placeholder="製造業" value="<c:out value='${jobSearch}'/>" required>
            </div>
            <div class="col-md-8 mb-3"></div>   
            <!-- 面接年月日 -->
            <div class="col-md-4 mb-3">
                <label class="form-label" for="year">面接年月日</label>
                <select id="year" name="year" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.year}'/>">
                    <option value="" selected>-- 年 --</option>
                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
                        i++){ %>
                        <option value="<%= i %>">
                            令和<%= i %>年（<%= i+2018 %>年）
                        </option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-4 mb-3">
                <label class="form-label invisible-text" for="month">月</label>
                <select id="month" name="month" class="form-control select-center  auto-select" data-selected-value="<c:out value='${param.month}'/>">
                    <option value="" selected>-- 月 --</option>
                    <% for(int i=1; i <=12; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>月
                        </option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-4 mb-3">
                <label class="form-label invisible-text" for="day">日</label>
                <select id="day" name="day" class="form-control select-center  auto-select" data-selected-value="<c:out value='${param.day}'/>">
                    <option value="" selected>-- 日 --</option>
                    <% for(int i=1; i <=31; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>日
                        </option>
                    <% } %>
                </select>
            </div>	        
 				<!-- 面接開始時刻 -->
            <div class="col-md-4 mb-3">
                <label class="form-label" for="startHour">面接開始時刻</label>
                <select id="startHour" name="startHour" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.startHour}'/>">
                    <option value="" selected>-- 時 --</option>
                    <% for(int i=0; i <=23; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>時
                        </option>
                    <% } %>
                </select>
            </div>	
            <div class="col-md-8 mb-3"></div>

 				<!-- 面接終了時刻 -->
            <div class="col-md-4 mb-5">
                <label class="form-label" for="endHour">面接終了時刻</label>
                <select id="endHour" name="endHour" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.endHour}'/>">
                    <option value="" selected>-- 時 --</option>
                    <% for(int i=0; i <= 23; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>時
                        </option>
                    <% } %>
                </select>
            </div>	
        </div>  
           <div class="col-md-8 mb-5"></div>
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
	    <!-- トークンの格納  -->
		    <input type="hidden" name="csrfToken" value="${csrfToken}">			
		<!-- 作成ボタン -->
		<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
	</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
