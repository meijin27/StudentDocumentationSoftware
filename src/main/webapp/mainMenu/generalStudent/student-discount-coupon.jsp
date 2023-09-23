<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「学割証発行願」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「学割証発行願」作成</h1><br>
    </div>			  
	<form action="StudentDiscountCoupon.action" method="post">
		<div class="row">
            <!-- 申請年月日 -->
            <div class="col-md-4 mb-3">
                <label class="form-label" for="requestYear">申請年月日</label>
                <span class="required-label">必須</span>
                <select name="requestYear" class="form-control select-center"  required>
                    <option value="" disabled selected style="display:none;">-- 年 --</option>
                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-1; i <=currentYear;
                        i++){ %>
                        <option value="<%= i %>">
                            <%= i %>年
                        </option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-4 mb-3">
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
	    <% for(int set = 1; set <= 2; set++){ %>
		    <div class="row set <% if (set != 1) { %> additional-set hidden <% } %>" data-set="<%= set %>">
                <h3 class="border-bottom"><%= set %>枚目</h3>
	            <!-- 必要枚数 -->           
				<div class="col-md-4 mb-3 text-center">
				    <label class="form-label" for="sheetsRequired<%= set %>">必要枚数</label>
				    <span class="required-label">必須</span>
					<div class="d-flex align-items-center justify-content-center">
					    <div class="form-check form-check-inline mr-lg-5">
				            <input class="form-check-input" type="radio" name="sheetsRequired<%= set %>" id="sheetsRequired1f<%= set %>" value="1" 
				                   <% if ("1".equals(request.getParameter("sheetsRequired" + set))) { %> checked <% } %>  <%= (set == 1) ? "required" : "" %> data-required="true">
				            <label class="form-check-label" for="sheetsRequired<%= set %>">
				                １枚
				            </label>
				        </div>
					    <div class="form-check form-check-inline ml-lg-5">
				            <input class="form-check-input" type="radio" name="sheetsRequired<%= set %>" id="sheetsRequired2f<%= set %>" value="2" 
									<% if ("2".equals(request.getParameter("sheetsRequired" + set))) { %> checked <% } %>  <%= (set == 1) ? "required" : "" %> data-required="true">
				            <label class="form-check-label" for="sheetsRequired<%= set %>">
				                ２枚
				            </label>
				        </div>
				    </div>
				</div>
	            <div class="col-md-8 mb-3"></div>
	            <!-- 出発駅 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="startingStation<%= set %>">出発駅</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" name="startingStation<%= set %>" placeholder="希望ヶ丘" <%= (set == 1) ? "required" : "" %> data-required="true">
	            </div>     	          	        
	            <!-- 到着駅 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="arrivalStation<%= set %>">到着駅</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" name="arrivalStation<%= set %>" placeholder="大阪" <%= (set == 1) ? "required" : "" %> data-required="true">
	            </div>     	   	        
	            <!-- 使用目的 -->  	        
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="intendedUse<%= set %>">使用目的</label>
	                <span class="required-label">必須</span>
	                <select name="intendedUse<%= set %>" class="form-control select-center"  <%= (set == 1) ? "required" : "" %> data-required="true">
	                    <option value="" disabled selected style="display:none;">-- 使用目的 --</option>
	                    <option value="帰省">帰省</option>
	                    <option value="見学">見学</option>
	                    <option value="その他">その他</option>
	                </select>
	            </div>
	            <!-- その他の理由 -->
	            <div class="col-md-8 mb-5">
	                <label class="form-label" for="reason<%= set %>">使用目的がその他の場合は理由を記載</label>
	                <input class="form-control" type="text" name="reason<%= set %>" placeholder="就職活動のため">
	            </div>     	 
 				<% if (set != 1) { %>
		            <!-- 削除ボタン -->
		            <div class="col-md-12 mb-5">
		                <button type="button" class="w-100 btn btn-lg btn-danger removeSetBtn border-bottom"><%= set %>枚目の削除</button>
		            </div>
	       		<% } %>
	        </div>  
        <% } %>    
        <!-- エラー表示 -->       
        <c:if test="${not empty nullError}">
            <div class="alert alert-danger text-center input-field" role="alert">
                ${nullError}
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
		<c:if test="${not empty innerError}">
			<div class="alert alert-danger" role="alert">
				${innerError}
			</div>
		</c:if>
		<!-- 追加ボタン -->
		<button type="button" id="addSetBtn" class="w-100 btn btn-lg btn-success mb-3">２枚目の追加（行先が複数ある場合）</button>
	    <!-- トークンの格納  -->
	    <input type="hidden" name="csrfToken" value="${csrfToken}">
		<!-- 作成ボタン -->
		<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
	</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
