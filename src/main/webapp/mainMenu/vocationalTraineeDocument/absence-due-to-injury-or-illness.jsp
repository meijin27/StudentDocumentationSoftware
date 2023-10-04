<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「傷病による欠席理由申立書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「傷病による欠席理由申立書」作成</h1><br>
    </div>			  
		<form action="AbsenceDueToInjuryOrIllness.action" method="post" autocomplete="off">
	        <div class="row">
   	            <!-- 病状 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="disease">病状</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="disease" name="disease" placeholder="発熱・咳" value="<c:out value='${disease}'/>" required>
	            </div>
		        <!-- 理由 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="reason">理由</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="reason" name="reason" placeholder="病院を受診し、療養していた" value="<c:out value='${reason}'/>" required>
	            </div>

      			<p class="border-bottom"></p>

	            <!-- 期間年月日（自） -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="startYear">期間年月日（自）</label>
	                <span class="required-label">必須</span>
	                <select id="startYear" name="startYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.startYear}'/>" required>
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
	                <label class="form-label invisible-text" for="startMonth">月</label>
	                <select id="startMonth" name="startMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.startMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label invisible-text" for="startDay">日</label>
	                <select id="startDay" name="startDay" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.startDay}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	      
  	            <!-- 期間年月日（至） -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="endYear">期間年月日（至）</label>
	                <span class="required-label">必須</span>
	                <select id="endYear" name="endYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.endYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 年 --</option>
	                    <% for(int i=currentYear-1; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="endMonth">月</label>
	                <select id="endMonth" name="endMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.endMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="endDay">日</label>
	                <select id="endDay" name="endDay" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.endDay}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        

      			<p class="border-bottom"></p>

	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestYear">申請年月日</label>
	                <span class="required-label">必須</span>
	                <select id="requestYear" name="requestYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.requestYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 年 --</option>
	                    <% for(int i=currentYear-1; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
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
	            <div class="col-md-4 mb-5">
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
	        </div>
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
			<button class="w-100 btn btn-lg btn-primary mb-5" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
