<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「公共職業訓練等受講証明書」作成</h1><br>
    </div>			
		<form action="CertificateVocationalTraining.action" method="post">
	        <div class="row">
  		    	<label class="form-label">日付ごとに該当する印を選択してください。</label>
   		    	<label class="form-label left-align">①公共職業訓練等を受けた日　無印</label>
  		    	<label class="form-label left-align">②公共職業訓練等が行われなかった日（日・祝日等）　＝印</label>
  		    	<label class="form-label left-align">③病気又は負傷により公共職業訓練等を受けなかった日　〇印</label>
  		    	<label class="form-label left-align">④やむを得ない理由により公共職業訓練等を受けなかった日　△印</label>
   		    	<label class="form-label left-align">⑤やむを得ない理由がなく公共職業訓練等を受けなかった日　✕印</label>
   		    	<label class="form-label left-align">⑥入校前、修了後、対校日翌日以降、カレンダー上存在しない日　／印</label>
        
			<% for (int i = 1; i <= 31; i++) { %>
			    <div class="col-md-2 mb-3">
			        <label><%= i %>日: </label>
					<% String dayAttribute = request.getParameter("day"+i) != null ? request.getParameter("day"+i) : ""; %>
			        <select class="form-control select-center" id="day<%= i %>" name="day<%= i %>">
			            <option value="" <% if (dayAttribute.isEmpty()) { %> selected <% } %>>--無--</option>
			            <option value="＝" <% if ("＝".equals(dayAttribute)) { %> selected <% } %>>＝</option>
			            <option value="〇" <% if ("〇".equals(dayAttribute)) { %> selected <% } %>>〇</option>
			            <option value="△" <% if ("△".equals(dayAttribute)) { %> selected <% } %>>△</option>
			            <option value="✕" <% if ("✕".equals(dayAttribute)) { %> selected <% } %>>✕</option>
			            <option value="／" <% if ("／".equals(dayAttribute)) { %> selected <% } %>>／</option>
			        </select>
			    </div>
			<% } %>
			</div>
	        <div class="row">				
	            <!-- 証明書対象期間 -->
   	            <p>証明書対象期間（令和　年　月）</p>
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="subjectYear">令和　年</label>
	                <span class="text-danger">*</span>
	                <select name="subjectYear" class="form-control select-center" required>
	                    <option value="">-- 令和　年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear - 2019; i <= currentYear - 2017;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="subjectMonth">月</label>
	                <span class="text-danger">*</span>
	                <select name="subjectMonth" class="form-control select-center" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
				<!-- 就労・内職 -->
				<div class="col-md-12 mb-5">
				    <label class="form-label">証明書対象期間中に就職、就労、内職又は手伝いをしましたか</label>
				    <span class="text-danger">*</span>
					<div class="d-flex align-items-center justify-content-center">
				    <div class="form-check form-check-inline mr-lg-5">
				        <input class="form-check-input" type="radio" name="problems" id="problemsYes" value="した" 
				               <% if ("した".equals(request.getParameter("problems"))) { %> checked <% } %> required>
				        <label class="form-check-label" for="problemsYes">
				            した
				        </label>
				    </div>
				    <div class="form-check form-check-inline ml-lg-5">
				        <input class="form-check-input" type="radio" name="problems" id="problemsNo" value="しない"
				               <% if ("しない".equals(request.getParameter("problems")) || request.getParameter("problems") == null) { %> checked <% } %> required>
				        <label class="form-check-label" for="problemsNo">
				            しない
				        </label>
				    </div>
				</div>
				</div>
				<!-- 収入 -->
				<div class="col-md-12 mb-5">
				    <label class="form-label">証明書対象期間中に内職又は手伝いをして収入を得ましたか</label>
				    <span class="text-danger">*</span>
				    <div class="d-flex align-items-center justify-content-center">
				        <div class="form-check form-check-inline mr-lg-5">
				            <input class="form-check-input" type="radio" name="income" id="incomeYes" value="得た" 
				                   <% if ("得た".equals(request.getParameter("income"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="incomeYes">
				                得た
				            </label>
				        </div>
				        <div class="form-check form-check-inline ml-lg-5">
				            <input class="form-check-input" type="radio" name="income" id="incomeNo" value="得ない"
				                   <% if ("得ない".equals(request.getParameter("income")) || request.getParameter("income") == null) { %> checked <% } %> required>
				            <label class="form-check-label" for="incomeNo">
				                得ない
				            </label>
				        </div>
				    </div>
				</div>
	        </div>
  			<c:if test="${not empty nullMessage}">
				<div class="alert alert-danger" role="alert">${nullMessage}
				</div>
			</c:if>
			<c:if test="${not empty innerError}">
				<div class="alert alert-danger" role="alert">${innerError}
				</div>
			</c:if>
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
