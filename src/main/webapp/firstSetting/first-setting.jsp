<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header/header-firstSetting.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<main class="form-first w-100 m-auto flex-shrink-0">
        <h2 class="p-5">初期設定</h2>

        <form action="FirstSetting.action" method="post">
            <div class="row">
                <!-- 姓 -->
                <div class="col-md-3 mb-3">
                    <label class="form-label" for="">姓</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="text" name="lastName" placeholder="田中" value="${lastName}" required>
                </div>
                <!-- 名 -->
                <div class="col-md-3 mb-3">
                    <label class="form-label" for="">名</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="text" name="firstName" placeholder="太郎" value="${firstName}" required>
                </div>
                <!-- 生年月日 -->
                <div class="col-md-2 mb-3">
                    <label class="form-label" for="birthYear">生年月日</label>
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
                <div class="col-md-2 mb-3">				
	  				<label class="form-label invisible-text" for="birthManth">月</label>
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
                <div class="col-md-2 mb-3">
	  				<label class="form-label invisible-text" for="birthDay">日</label>
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
                <!-- 学生の種類 -->
                <div class="col-md-6 mb-3">
                    <label class="form-label" for="">学生の種類</label>
                     <span class="text-danger">*</span> 
                    <select name="studentType" class="form-control" required>
                        <option value="">--- 学生の種類 ---</option>
			            <option value="一般学生">一般学生</option>
			            <option value="留学生">留学生</option>
			            <option value="職業訓練生">職業訓練生</option>
        			</select>
                </div> 
                <!-- クラス名 -->
                <div class="col-md-6 mb-3">
                    <label class="form-label" for="">クラス名</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="text" name="className" placeholder="IT・ゲームソフト科" value="${className}"required>
                </div>
                <!-- 学籍番号 -->
                <div class="col-md-6 mb-3">
                    <label class="form-label" for="">学籍番号</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="studentNumber" name="studentNumber" placeholder="240001" value="${studentNumber}"required>
                </div>                                                  			
                <!-- 学年 -->
                <div class="col-md-2 mb-3">
                    <label class="form-label" for="">学年</label>
                    <span class="text-danger">*</span>                    
				    <select name="schoolYear" class="form-control" required>
				        <option value="">--- 学年 ---</option>
				        <% 
				        for(int i=1; i <= 2; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %></option>
				        <% 
				        } 
				        %>
				    </select>
				</div>
                <div class="col-md-2 mb-3">	
                    <label class="form-label" for="">組</label>
                    <span class="text-danger">*</span>                        
				    <select name="classNumber" class="form-control" required>
				        <option value="">--- 組 ---</option>
				        <% 
				        for(int i=1; i <= 4; i++){ 
				        %>
				            <option value="<%= i %>"><%= i %></option>
				        <% 
				        } 
				        %>
				    </select>
				</div>            

        
             
             </div>   
            <c:if test="${not empty agreeError}">
               <div class="alert alert-danger text-center input-field" role="alert">
                   ${agreeError}
               </div>
           </c:if>       
           <c:if test="${not empty studentNumberError}">
              <div class="alert alert-danger text-center input-field" role="alert">
                  ${studentNumberError}
              </div>
           </c:if>            
           <c:if test="${not empty birthDayError}">
              <div class="alert alert-danger text-center input-field" role="alert">
                  ${birthDayError}
              </div>
           </c:if>                
            <div class="alert alert-info">
                ユーザ登録サービス利用規約・個人情報の取り扱いについて
            </div>

            <div class="form-first  mb-3">
            	<input class="form-check-input" id="agreeCheckbox" type="checkbox" name="agree" value="1">
                <label class="form-check-label" for="">同意する</label>
            </div>

			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">次へ</button>
        </form>
    </main>


<script src="../js/app.js"></script>   

<%@include file="../footer.jsp"%>


