<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「氏名・住所等変更届」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「氏名・住所等変更届」作成</h1><br>
    </div>			  
		<form action="NotificationOfChange.action" method="post">
	        <div class="row">
   	            <p class="left-align">・変更した項目を入力してください。</p>
   	            <p class="left-align">・氏名変更した場合は姓・名を全て入力してください。</p>
   	            <p class="left-align">・住所変更した場合は郵便番号と住所を入力してください。</p>
   	            <p class="left-align margin-bottom-50">・変更項目（旧）には現在登録されている情報を記載します。</p>
   	            <!-- 変更年月日 -->
	            <div class="col-md-4 mb-0">
	                <label class="form-label" for="changeYear">変更年月日</label>
	                <span class="required-label">必須</span>
	                <select name="changeYear" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 令和　年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="changeMonth">月</label>
	                <select name="changeMonth" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="changeDay">日</label>
	                <select name="changeDay" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	      
   				<small class="text-muted mb-3">氏名変更は変更した日。住所変更は転居した日付。この届け出を提出した日ではありません。</small>	   

	            <!-- 姓 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="">名前</label>
	                <input class="form-control" type="text" name="lastName" placeholder="田中" value="<c:out value='${lastName}'/>">
    				<small class="text-muted">姓</small>	                
	            </div>
	            <!-- 名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label invisible-text" for="">名</label>
	                <input class="form-control" type="text" name="firstName" placeholder="太郎" value="<c:out value='${firstName}'/>">
    				<small class="text-muted">名</small>	                
	            </div>
  	            <!-- 郵便番号 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="">郵便番号</label>
	                <input class="form-control" type="text" name="postCode" placeholder="2310017" value="<c:out value='${postCode}'/>">
	            </div>
	            <div class="col-md-8 mb-3"></div>
   	            <!-- 住所 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="">住所</label>
	                <input class="form-control" type="text" name="address" placeholder="秋田県秋田市飯島南字田尻堰越" value="<c:out value='${address}'/>">
	            </div>
   	            <!-- 電話番号 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="">電話番号</label>
	                <input class="form-control" type="text" name="tel" placeholder="08011112222"value="<c:out value='${tel}'/>">
	            </div>  
  	            <div class="col-md-8 mb-3"></div>
  	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestYear">申請年月日</label>
	                <span class="required-label">必須</span>
	                <select name="requestYear" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 令和　年 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="requestMonth">月</label>
	                <select name="requestMonth" class="form-control select-center" required>
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
	                <select name="requestDay" class="form-control select-center" required>
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
   	        <c:if test="${not empty  inputError}">
	            <div class="alert alert-danger" role="alert">
	                ${inputError}
	            </div>
	        </c:if>	        
   	        <c:if test="${not empty  nameError}">
	            <div class="alert alert-danger" role="alert">
	                ${nameError}
	            </div>
	        </c:if>
  	        <c:if test="${not empty  rubyError}">
	            <div class="alert alert-danger" role="alert">
	                ${rubyError}
	            </div>
	        </c:if>
	        <c:if test="${not empty addressError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${addressError}
	            </div>
	        </c:if>	        
	        <c:if test="${not empty  valueLongError}">
	            <div class="alert alert-danger" role="alert">
	                ${valueLongError}
	            </div>
	        </c:if>
	        <c:if test="${not empty dayError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${dayError}
	            </div>
	        </c:if>	        
	        <c:if test="${not empty telError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${telError}
	            </div>
	        </c:if>
   	        <c:if test="${not empty postCodeError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${postCodeError}
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
			<button class="w-100 btn btn-lg btn-primary mb-5" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
