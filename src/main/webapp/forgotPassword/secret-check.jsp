<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="forgot-menu.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<main class="container">
	<h2 class="text-center p-5">秘密の質問：${sessionScope.secretQuestion}</h2>

        <form action="SecretCheck.action" method="post">
            <div class="row">
                <!-- 秘密の質問の答え -->
                <div class="col-md-12 mb-3">
                    <label class="form-label" for="">秘密の質問の答えを入力してください</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="text" name="secretAnswer" placeholder="RX-93-ν2 Hi-νガンダム" required>
                </div>
                                <!-- 生年月日 -->
                <div class="col-md-4 mb-3">
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
                <div class="col-md-4 mb-3">				
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
                <div class="col-md-4 mb-3">
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
			<div class="form-group text-center input-field">
			    <a href="seach-account.jsp" class="btn btn-secondary md-5 mr-5">戻る</a>
			    <input type="submit" value="次へ" class="btn btn-primary md-5"/> <!-- Margin Right added -->
			</div>      
            <c:if test="${not empty secretError}">
              <div class="alert alert-danger text-center input-field" role="alert">
                  ${secretError}
              </div>
            </c:if>        
        </form>
    </main>

<%@include file="../footer.html" %>

