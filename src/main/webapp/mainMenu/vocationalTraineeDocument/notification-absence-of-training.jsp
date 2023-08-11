<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「委託訓練欠席（遅刻・早退）届」作成</h1><br>
    </div>			  
	<form action="NotificationAbsenceOfTtraining.action" method="post">
        <div class="row">
  	            <!-- 対象年月 -->
            <div class="col-md-6 mb-5">
                <label class="form-label" for="subjectYear">対象年</label>
                <span class="text-danger">*</span>
                <select name="subjectYear" class="form-control select-center" required>
                    <option value="">-- 年 --</option>
                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear - 1; i <= currentYear;
                        i++){ %>
                        <option value="<%= i %>">
                            <%= i %>年
                        </option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-6 mb-5">
                <label class="form-label" for="subjectMonth">対象月</label>
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
        </div>
        <% for(int set = 1; set <= 10; set++){ %>
		    <div class="row set <% if (set != 1) { %> additional-set hidden <% } %>" data-set="<%= set %>">
               <h3 class="border-bottom"><%= set %>行目</h3>
               <!-- 休業開始日 -->
		       <div class="col-md-3 mb-5">
		            <label class="form-label" for="restedDayStart<%= set %>">休業開始日</label>
		            <span class="text-danger">*</span>
 		            <select name="restedDayStart<%= set %>" class="form-control select-center" <%= (set == 1) ? "required" : "" %>>
		                <option value="">-- 日 --</option>
		                <% for(int i = 1; i <= 31; i++){ %>
		                    <option value="<%= i %>">
		                        <%= i %>日
		                    </option>
		                <% } %>
		            </select>
		        </div>
	 	            <!-- 休業終了日 -->
	            <div class="col-md-3 mb-5">
	                <label class="form-label" for="restedDayEnd<%= set %>">休業終了日</label>
	                <span class="text-danger">*</span>
	                <select name="restedDayEnd<%= set %>" class="form-control select-center" <%= (set == 1) ? "required" : "" %>>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	         
	  	            <!-- 理由 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="">理由</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="reason<%= set %>" placeholder="腹痛のため（自宅療養）" value="${reason}" <%= (set == 1) ? "required" : "" %>>
	            </div>
	            <!-- 終日休業有無 -->
				<div class="col-md-12 mb-5">
				    <label class="form-label">終日休みましたか？</label>
				    <span class="text-danger">*</span>
				    <div class="d-flex align-items-center justify-content-center">
				        <div class="form-check radio-spacing">
				            <input class="form-check-input" type="radio" name="allDayOff<%= set %>" id="allDayOffYes<%= set %>" value="はい" 
				                   <% if ("はい".equals(request.getParameter("allDayOff" + set))) { %> checked <% } %> <%= (set == 1) ? "required" : "" %>>
				            <label class="form-check-label" for="allDayOffYes<%= set %>">
				                はい
				            </label>
				        </div>
				        <div class="form-check"> 
				            <input class="form-check-input" type="radio" name="allDayOff<%= set %>" id="allDayOffNo<%= set %>" value="いいえ" 
				                   <% if ("いいえ".equals(request.getParameter("allDayOff" + set))) { %> checked <% } %> <%= (set == 1) ? "required" : "" %>>
				            <label class="form-check-label" for="allDayOffNo<%= set %>">
				                いいえ
				            </label>
				        </div>
				    </div>
				</div>
				<p>終日休んだ場合は欠席期間時限数を入力してください</p>
				<p>終日休んでない場合は遅刻時限数か早退時限数を入力してください</p>
	            <!-- 欠席期間時限数 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="deadTime<%= set %>">欠席期間時限数</label>
	                <select name="deadTime<%= set %>" class="form-control select-center">
	                    <option value="">-- 時限数 --</option>
	                    <% for(int i=1; i <=110; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>時限
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <!-- 遅刻時限数 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="latenessTime<%= set %>">遅刻時限数</label>
	                <select name="latenessTime<%= set %>" class="form-control select-center">
	                    <option value="">-- 時限数 --</option>
	                    <% for(int i=1; i <=8; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>時限
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <!-- 早退時限数 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="leaveEarlyTime<%= set %>">早退時限数</label>
	                <select name="leaveEarlyTime<%= set %>" class="form-control select-center">
	                    <option value="">-- 時限数 --</option>
	                    <% for(int i=1; i <=8; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>時限
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <!-- 証明添付有無 -->
				<div class="col-md-12 mb-3">
				    <label class="form-label">証明添付有無</label>
				    <span class="text-danger">*</span>
				    <div class="d-flex align-items-center justify-content-center">
				        <div class="form-check radio-spacing">
				            <input class="form-check-input" type="radio" name="AttachmentOfCertificate<%= set %>" id="AttachmentOfCertificateYes<%= set %>" value="有" 
				                   <% if ("有".equals(request.getParameter("AttachmentOfCertificate" + set))) { %> checked <% } %> <%= (set == 1) ? "required" : "" %>>
				            <label class="form-check-label" for="AttachmentOfCertificateYes<%= set %>">
				                有
				            </label>
				        </div>
				        <div class="form-check"> 
				            <input class="form-check-input" type="radio" name="AttachmentOfCertificate<%= set %>" id="AttachmentOfCertificateNo<%= set %>" value="無" 
				                   <% if ("無".equals(request.getParameter("AttachmentOfCertificate" + set))) { %> checked <% } %> <%= (set == 1) ? "required" : "" %>>
				            <label class="form-check-label" for="AttachmentOfCertificateNo<%= set %>">
				                無
				            </label>
				        </div>
				    </div>
				</div>
				<% if (set != 1) { %>
		            <!-- 削除ボタン -->
		            <div class="col-md-12 mb-5">
		                <button type="button" class="w-100 btn btn-lg btn-danger removeSetBtn border-bottom"><%= set %>行目の削除</button>
		            </div>
	       		<% } %>
	        </div>  
        <% } %>
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
        <c:if test="${not empty logicalError}">
            <div class="alert alert-danger text-center input-field" role="alert">
                ${logicalError}
            </div>
        </c:if>	   	        
		<c:if test="${not empty innerError}">
			<div class="alert alert-danger" role="alert">${innerError}
			</div>
		</c:if>
		<button type="button" id="addSetBtn" class="w-100 btn btn-lg btn-success mb-3">行の追加（休業日が複数ある場合）</button>
		<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
	</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
