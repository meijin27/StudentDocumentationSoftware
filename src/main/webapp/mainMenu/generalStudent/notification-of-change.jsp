<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>

<!-- 「変更届」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「変更届」作成</h1><br>
    </div>			  
		<form action="NotificationOfChange.action" method="post">
	        <div class="row">
   	            <p class="left-align">・変更した項目を入力してください。</p>
   	            <p class="left-align">・氏名変更した場合は姓・名を全て入力してください。</p>
   	            <p class="left-align">・住所変更した場合は郵便番号と住所を入力してください。</p>
   	            <!-- 届出年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="requestYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="requestYear" class="form-control select-center" required>
	                    <option value="">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-1; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestMonth">届出年月日</label>
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
	                <label class="form-label invisible-text" for="requestDay">日</label>
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
	            <!-- 変更対象者 -->  	        
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="ChangeSubject">変更対象者</label>
	                <span class="text-danger">*</span>
	                <select name="ChangeSubject" class="form-control select-center" required>
	                    <option value="">-- 変更対象者 --</option>
	                    <option value="本人">本人</option>
	                    <option value="保護者">保護者</option>
	                    <option value="保証人">保証人</option>
	                </select>
	            </div>
  	            <!-- 郵便番号 -->
	            <div class="col-md-3 mb-5">
	                <label class="form-label" for="postCode">郵便番号</label>
	                <input class="form-control" type="text" name="postCode" placeholder="2310017" value="<c:out value='${postCode}'/>">
	            </div>
   	            <!-- 住所 -->
	            <div class="col-md-9 mb-5">
	                <label class="form-label" for="address">住所</label>
	                <input class="form-control" type="text" name="address" placeholder="秋田県秋田市飯島南字田尻堰越" value="<c:out value='${address}'/>">
	            </div>
   	            <!-- 電話番号 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="tel">電話番号</label>
	                <input class="form-control" type="text" name="tel" placeholder="08011112222"value="<c:out value='${tel}'/>">
	            </div>  
   	            <!-- 在留カード -->
	            <div class="col-md-5 mb-5">
	                <label class="form-label" for="residentCard">在留カード</label>
	                <input class="form-control" type="text" name="residentCard" placeholder="AB12345678CD" value="<c:out value='${residentCard}'/>">
	            </div>
  	            <!-- 期間満了年月日 -->
	            <div class="col-md-3 mb-5">
	                <label class="form-label" for="endYear">期間満了年月日</label>
	                <select name="endYear" class="form-control select-center">
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear; i <=currentYear+2;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-5">
	                <label class="form-label invisible-text" for="endMonth">月</label>
	                <select name="endMonth" class="form-control select-center">
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-5">
	                <label class="form-label invisible-text" for="endDay">日</label>
	                <select name="endDay" class="form-control select-center">
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
	                <input class="form-control" type="text" name="lastName" placeholder="田中" value="<c:out value='${lastName}'/>">
	            </div>
	            <!-- 名 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="">名</label>
	                <input class="form-control" type="text" name="firstName" placeholder="太郎" value="<c:out value='${firstName}'/>">
	            </div>
	        </div>
	        <!-- エラー表示 -->     	        
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
	        <c:if test="${not empty addressError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${addressError}
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
  	        <c:if test="${not empty residentCardError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${residentCardError}
	            </div>
	        </c:if>
			<c:if test="${not empty innerError}">
				<div class="alert alert-danger" role="alert">${innerError}
				</div>
			</c:if>	      
			<!-- サブミットボタン -->  
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
