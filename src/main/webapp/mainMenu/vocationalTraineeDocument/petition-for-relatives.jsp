<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「親族続柄申立書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「親族続柄申立書」作成</h1><br>
    </div>			  
		<form action="PetitionForRelatives.action" method="post">
			<p class="text-start text-center" style="color: red;"><strong>当該書類は印刷後に手書きで親族該当箇所に〇を付けてください</strong></p>

	        <div class="row">
   	            <!-- 親族氏名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="relativeName">親族氏名</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="relativeName" name="relativeName" placeholder="鈴木　一郎" value="<c:out value='${relativeName}'/>" required>
	            </div>
	            <div class="col-md-6 mb-3"></div>	            
	            <!-- 親族生年月日-->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="birthYear">親族生年月日</label>
	                <span class="required-label">必須</span>
	                <select id="birthYear" name="birthYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.birthYear}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-110; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
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
	            <div class="col-md-4 mb-3">
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
   	            <!-- 親族住所 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="relativeAddress">親族住所</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="relativeAddress" name="relativeAddress" placeholder="秋田県秋田市飯島南字田尻堰越" value="<c:out value='${relativeAddress}'/>" required>
	            </div>
  	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestYear">申請年月日</label>
	                <span class="required-label">必須</span>
	                <select id="requestYear" name="requestYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.requestYear}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 年 --</option>
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
	                    <option value="" disabled selected style="display:none;">-- 月 --</option>
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
	                    <option value="" disabled selected style="display:none;">-- 日 --</option>
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
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${valueLongError}
	            </div>
	        </c:if>
	        <c:if test="${not empty dayError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${dayError}
	            </div>
	        </c:if>	        
			<c:if test="${not empty innerError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
					${innerError}
				</div>
			</c:if>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">			
			<!-- 作成ボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
