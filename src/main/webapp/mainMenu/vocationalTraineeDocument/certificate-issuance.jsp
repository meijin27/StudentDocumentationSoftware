<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「証明書交付願」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「証明書交付願」作成</h1><br>
    </div>			  
		<form action="CertificateIssuance.action" method="post">
	        <div class="row">
   	            <!-- 申請理由（具体的に） -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="reason">申請理由（具体的に）</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="reason" name="reason" placeholder="国民年金保険料の学生納付特例制度申請のため" value="<c:out value='${reason}'/>" required>
	            </div>
  	            <!-- 提出先 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="propose">提出先</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="propose" name="propose" placeholder="厚木年金事務所" value="<c:out value='${propose}'/>" required>
	            </div>
	            <!-- 交付区分-->
	            <p>必要な書類の枚数を選択してください</p>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="proofOfStudent">在校証明書</label>
	                <select id="proofOfStudent" name="proofOfStudent" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.proofOfStudent}'/>">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="certificateOfCompletion">修了証明書</label>
	                <select id="certificateOfCompletion" name="certificateOfCompletion" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.certificateOfCompletion}'/>">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="certificateOfExpectedCompletion">修了見込証明書</label>
	                <select id="certificateOfExpectedCompletion" name="certificateOfExpectedCompletion" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.certificateOfExpectedCompletion}'/>">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        
  	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestYear">申請年月日</label>
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
	        <!-- エラー表示 -->     	        
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
	        <c:if test="${not empty numberError}" >
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${numberError}
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
