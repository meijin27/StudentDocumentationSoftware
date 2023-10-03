<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「自転車等通学許可願」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「自転車等通学許可願」作成</h1><br>
    </div>			  
		<form action="PermissionBike.action" method="post" autocomplete="off">
			<p class="text-start text-center" style="color: red;"><strong>当該書類は印刷後、氏名及び保護者欄に押印をお願いします。</strong></p>

	        <div class="row">
   	            <!-- 願出年月日 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="requestYear">願出年月日</label>
	                <span class="required-label">必須</span>
	                <select id="requestYear" name="requestYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.requestYear}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 令和　年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label invisible-text" for="requestMonth">月</label>
	                <select id="requestMonth" name="requestMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.requestMonth}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 月 --</option>
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
	                    <option value="" disabled selected style="display:none;">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        
   	            <!-- 保護者 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="patron">保護者</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="patron" name="patron" placeholder="範馬　勇次郎" value="<c:out value='${patron}'/>" required>
	            </div>
	            <div class="col-md-6 mb-3"></div>
	            <!-- 保護者電話番号 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="patronTel">保護者電話番号</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="patronTel" name="patronTel" placeholder="08011112222"value="<c:out value='${patronTel}'/>" required>
	            </div>
	            <div class="col-md-8 mb-3"></div>
	            <!-- 種別 -->
				<div class="col-md-4 mb-3">
	                <label class="form-label" for="classification">種別</label>
				    <span class="required-label">必須</span>
				    <div class="d-flex flex-column align-items-start"> 
				        <div class="form-check mb-2"> 
				            <input class="form-check-input" type="radio" name="classification" id="bicycle" value="自転車" 
				                   <% if ("自転車".equals(request.getParameter("classification"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="bicycle">
				                自転車
				            </label>
				        </div>
				        <div class="form-check"> 
				            <input class="form-check-input" type="radio" name="classification" id="motorizedBicycle" value="原動機付自転車" 
									<% if ("原動機付自転車".equals(request.getParameter("classification"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="motorizedBicycle">
				                原動機付自転車
				            </label>
				        </div>
				    </div>
				</div>
	            <div class="col-md-8 mb-3"></div>    
 	            <!-- 登録番号 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="registrationNumber">登録番号（ナンバー or 防犯登録番号）</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="registrationNumber" name="registrationNumber" placeholder="東京　あ　1234" value="<c:out value='${registrationNumber}'/>" required>
	            </div>
	            <div class="col-md-6 mb-3"></div>
	            <!-- 車種・色 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="modelAndColor">車種・色</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="modelAndColor" name="modelAndColor" placeholder="スーパーカブ50・白" value="<c:out value='${modelAndColor}'/>" required>
	            </div>   
	            <div class="col-md-6 mb-5"></div>	            
       			<p class="border-bottom"></p>
  	            <!-- 期間年月日（自） -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="startYear">期間年月日（自）</label>
	                <span class="required-label">必須</span>
	                <select id="startYear" name="startYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.startYear}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 令和　年 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2017;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label invisible-text" for="startMonth">月</label>
	                <select id="startMonth" name="startMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.startMonth}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 月 --</option>
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
	                    <option value="" disabled selected style="display:none;">-- 日 --</option>
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
	                    <option value="" disabled selected style="display:none;">-- 令和　年 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2017;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="endMonth">月</label>
	                <select id="endMonth" name="endMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.endMonth}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 月 --</option>
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
	                    <option value="" disabled selected style="display:none;">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	    
       			<p class="border-bottom"></p> 
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
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
