<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「就労証明書」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「就労証明書」作成</h1><br>
    </div>			
		<form action="CertificateOfEmployment.action" method="post">
			<p class="text-start text-center" style="color: red;"><strong>当該書類は印刷後、就労先にて太枠内の記入をお願いします。</strong></p>

	        <div class="row">
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="firstMonth">就労した月</label>
	                <span class="required-label">必須</span>
	                <select name="firstMonth" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>	     
	            <div class="col-md-8 mb-3"></div>   
  		    	<p>就労日を選択してください</p>
				<% for (int i = 1; i <= 31; i++) { %>
				    <div class="col-md-2 mb-1 d-flex align-items-center">
				        <label class="me-2 mb-0" style="width: 40px;"><%= i %>日:</label>
				        <% String dayAttribute = session.getAttribute("firstMonthDay"+i) != null ? session.getAttribute("firstMonthDay"+i).toString() : ""; %>
				        <input class="form-check-input mt-0 mb-0" type="checkbox" id="firstMonthDay<%= i %>" name="firstMonthDay<%= i %>" value="〇" <% if ("〇".equals(dayAttribute)) { %> checked <% } %> >
				    </div>
				<% } %>
			</div>
			<div class="row">
  		    	<p style="margin-top: 50px; color: red;">一か月分を作成する場合は下記は選択しないでください</p>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="secondMonth">就労した月（二か月目）</label>
	                <select name="secondMonth" class="form-control select-center">
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        
	            <div class="col-md-8 mb-3"></div>   
  		    	<p>就労日を選択してください（二か月目）</p>
				<% for (int i = 1; i <= 31; i++) { %>
				    <div class="col-md-2 mb-1 d-flex align-items-center">
				        <label class="me-2 mb-0" style="width: 40px;"><%= i %>日:</label>
				        <% String dayAttribute = session.getAttribute("secondMonthDay"+i) != null ? session.getAttribute("secondMonthDay"+i).toString() : ""; %>
				        <input class="form-check-input mt-0 mb-0" type="checkbox" id="secondMonthDay<%= i %>" name="secondMonthDay<%= i %>" value="〇" <% if ("〇".equals(dayAttribute)) { %> checked <% } %> >
				    </div>
				<% } %>
	        </div>
	        <!-- エラー表示  -->
  			<c:if test="${not empty nullMessage}">
				<div class="alert alert-danger" role="alert">${nullMessage}
				</div>
			</c:if>
  	        <c:if test="${not empty logicalError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${logicalError}
	            </div>
	        </c:if>	     
			<c:if test="${not empty innerError}">
				<div class="alert alert-danger" role="alert">${innerError}
				</div>
			</c:if>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">			
			<!-- 作成ボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />