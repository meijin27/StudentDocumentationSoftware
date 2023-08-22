<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>

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
	            <p>変更年月日</p>
	            <p>（氏名変更は変更した日。住所変更は転居した日付。この届け出を提出した日ではありません。）</p>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="changeYear">令和　年</label>
	                <span class="text-danger">*</span>
	                <select name="changeYear" class="form-control select-center" required>
	                    <option value="">-- 令和　年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="changeMonth">月</label>
	                <span class="text-danger">*</span>
	                <select name="changeMonth" class="form-control select-center" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="changeDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="changeDay" class="form-control select-center" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	      
	            <!-- 姓 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="">姓</label>
	                <input class="form-control" type="text" name="lastName" placeholder="田中" value="${lastName}">
	            </div>
	            <!-- 名 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="">名</label>
	                <input class="form-control" type="text" name="firstName" placeholder="太郎" value="${firstName}">
	            </div>
  	            <!-- 郵便番号 -->
	            <div class="col-md-3 mb-5">
	                <label class="form-label" for="">郵便番号</label>
	                <input class="form-control" type="text" name="postCode" placeholder="2310017" value="${postCode}">
	            </div>
   	            <!-- 住所 -->
	            <div class="col-md-9 mb-5">
	                <label class="form-label" for="">住所</label>
	                <input class="form-control" type="text" name="address" placeholder="秋田県秋田市飯島南字田尻堰越" value="${address}">
	            </div>
   	            <!-- 電話番号 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="">電話番号</label>
	                <input class="form-control" type="text" name="tel" placeholder="08011112222"value="${tel}">
	            </div>  
  	            <!-- 申請年月日 -->
	            <p>申請年月日</p>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestYear">令和　年</label>
	                <span class="text-danger">*</span>
	                <select name="requestYear" class="form-control select-center" required>
	                    <option value="">-- 令和　年 --</option>
	                    <% for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestMonth">月</label>
	                <span class="text-danger">*</span>
	                <select name="requestMonth" class="form-control select-center" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="requestDay" class="form-control select-center" required>
	                    <option value="">-- 日 --</option>
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
	        <!-- サブミットボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-5" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
