<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-forgotPassword.jsp" />

<main class="form-forgot w-100 m-auto flex-shrink-0">
	<div class="container">
	<h2 class="p-1">秘密の質問:</h2>
	<h2 class="pt-1 pb-5 pl-5 pr-5" style="color: red;"><strong>${sessionScope.secretQuestion}</strong></h2>
        <form action="SecretCheck.action" method="post">
            <div class="row">
                <!-- 秘密の質問の答え -->
                <div class="col-md-12 mb-3">
                    <label class="form-label" for="">秘密の質問の答えを入力してください</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="text" name="secretAnswer" required>
                </div>
                <!-- 生年月日 -->
                <div class="col-md-4 mb-5">
                    生年月日
                    <span class="text-danger">*</span>                    
				    <select name="birthYear" class="form-control" required>
				        <option value="">--- 年 ---</option>
				        <% 
				        int currentYear = java.time.Year.now().getValue();
				        for(int i=1960; i <= currentYear - 14; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %></option>
				        <% 
				        } 
				        %>
				    </select>
				</div>
                <div class="col-md-4 mb-5">				
                    <span class="text-danger">*</span>                        
				    <select name="birthMonth" class="form-control" required>
				        <option value="">--- 月 ---</option>
				        <% 
				        for(int i=1; i <= 12; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %></option>
				        <% 
				        } 
				        %>
				    </select>
				</div>
                <div class="col-md-4 mb-5">
                    <span class="text-danger">*</span>                 
				    <select name="birthDay" class="form-control" required>
				        <option value="">--- 日 ---</option>
				        <% 
				        for(int i=1; i <= 31; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %></option>
				        <% 
				        } 
				        %>
				    </select>
                </div>     
            </div>               
            <c:if test="${not empty secretError}">
              <div class="alert alert-danger text-center input-field" role="alert">
                  ${secretError}
              </div>
            </c:if>
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
        </form>
    </main>

<c:import url="/footer/footer.jsp" />

