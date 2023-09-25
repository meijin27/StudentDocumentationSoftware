<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-forgotPassword.jsp" />
<c:import url="/token/token.jsp" />

<!-- パスワード忘却時秘密の質問の答えと生年月日確認用JSP  -->
<main class="form-forgotPassword w-100 m-auto flex-shrink-0">
	<div class="container">
	<h2 class="p-1">秘密の質問:</h2>
	<h2 class="pt-1 pb-5 pl-5 pr-5" style="color: red;"><strong>${secretQuestion}</strong></h2>
        <form action="SecretCheck.action" method="post">
            <div class="row">
                <!-- 秘密の質問の答え -->
                <div class="col-md-12 mb-3">
                    <label class="form-label" for="">秘密の質問の答えを入力してください</label>
                    <span class="required-label">必須</span>
                    <input class="form-control" type="text" name="secretAnswer" required>
                </div>
                <!-- 生年月日 -->
                <div class="col-md-4 mb-5">
	                <label class="form-label" for="birthYear">生年月日</label>
                    <span class="required-label">必須</span>                    
				    <select name="birthYear" class="form-control select-center" required>
				        <option value="" disabled selected style="display:none;">--- 年 ---</option>
				        <% 
				        int currentYear = java.time.Year.now().getValue();
				        for(int i=currentYear-60; i <= currentYear - 14; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %>年</option>
				        <% 
				        } 
				        %>
				    </select>
				</div>
                <div class="col-md-4 mb-5">				
  	                <label class="form-label invisible-text" for="birthMonth">月</label>
				    <select name="birthMonth" class="form-control select-center" required>
				        <option value="" disabled selected style="display:none;">--- 月 ---</option>
				        <% 
				        for(int i=1; i <= 12; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %>月</option>
				        <% 
				        } 
				        %>
				    </select>
				</div>
                <div class="col-md-4 mb-5">
                    <label class="form-label invisible-text" for="birthDay">日</label>
				    <select name="birthDay" class="form-control select-center" required>
				        <option value="" disabled selected style="display:none;">--- 日 ---</option>
				        <% 
				        for(int i=1; i <= 31; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %>日</option>
				        <% 
				        } 
				        %>
				    </select>
                </div>     
            </div>     
            <!-- エラー表示  -->
            <c:if test="${not empty secretError}">
              <div class="alert alert-danger text-center input-field" role="alert">
                  ${secretError}
              </div>
            </c:if>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">		    
		    <!-- 次へボタン  -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
        </form>
    </main>

<c:import url="/footer/footer.jsp" />


