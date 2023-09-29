<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「委託訓練欠席（遅刻・早退）届」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>「委託訓練欠席（遅刻・早退）届」作成</h1><br>
    </div>			  
	<form action="NotificationAbsenceOfTtraining.action" method="post">
        <div class="row">
  	            <!-- 対象年月 -->
            <div class="col-md-4 mb-3">
                <label class="form-label" for="subjectYear">対象年</label>
                <span class="required-label">必須</span>
                <select id="subjectYear" name="subjectYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.subjectYear}'/>" required>
                    <option value="" disabled selected style="display:none;">-- 年 --</option>
                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear - 2019; i <= currentYear-2018;
                        i++){ %>
                        <option value="<%= i %>">
                            	令和<%= i %>年（<%= i+2018 %>年）
                        </option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-4 mb-3">
                <label class="form-label" for="subjectMonth">対象月</label>
                <span class="required-label">必須</span>
                <select id="subjectMonth" name="subjectMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.subjectMonth}'/>" required>
                    <option value="" disabled selected style="display:none;">-- 月 --</option>
                    <% for(int i=1; i <=12; i++){ %>
                        <option value="<%= i %>">
                            <%= i %>月
                        </option>
                    <% } %>
                </select>
            </div>
            <div class="col-md-4 mb-3"></div>
        </div>
        <% for(int set = 1; set <= 10; set++){ %>
		    <div class="row set <% if (set != 1) { %> additional-set hidden <% } %>" data-set="<%= set %>">
               <h3 class="border-bottom"><%= set %>番目の期間</h3>
               <!-- 休業開始日 -->
		       <div class="col-md-3 mb-3">
		            <label class="form-label" for="restedDayStart<%= set %>">休業開始日</label>
		            <span class="required-label">必須</span>
 		            <select id="restedDayStart<%= set %>" name="restedDayStart<%= set %>" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.year}'/>" <%= (set == 1) ? "required" : "" %> data-required="true">
		                <option value="" disabled selected style="display:none;">-- 日 --</option>
		                <% for(int i = 1; i <= 31; i++){ %>
		                    <option value="<%= i %>">
		                        <%= i %>日
		                    </option>
		                <% } %>
		            </select>
		        </div>
	 	            <!-- 休業終了日 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="restedDayEnd<%= set %>">休業終了日</label>
	                <span class="required-label">必須</span>
	                <select id="restedDayEnd<%= set %>" name="restedDayEnd<%= set %>" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.year}'/>" <%= (set == 1) ? "required" : "" %> data-required="true">
	                    <option value="" disabled selected style="display:none;">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	         
	  	            <!-- 理由 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="reason<%= set %>">理由（22文字以下）</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="reason<%= set %>" name="reason<%= set %>" placeholder="腹痛のため（自宅療養）" <%= (set == 1) ? "required" : "" %> data-required="true">
	            </div>
	            <!-- 終日休業有無 -->
				<div class="col-md-12 mb-3 text-center">
				    <label class="form-label">終日休みましたか？</label>
				    <span class="required-label">必須</span>
				    <div class="d-flex align-items-center justify-content-center">
				        <div class="form-check radio-spacing">
				            <input class="form-check-input" type="radio" name="allDayOff<%= set %>" id="allDayOffYes<%= set %>" value="はい" 
				                   <% if ("はい".equals(request.getParameter("allDayOff" + set))) { %> checked <% } %> <%= (set == 1) ? "required" : "" %> data-required="true">
				            <label class="form-check-label" for="allDayOffYes<%= set %>">
				                はい
				            </label>
				        </div>
				        <div class="form-check"> 
				            <input class="form-check-input" type="radio" name="allDayOff<%= set %>" id="allDayOffNo<%= set %>" value="いいえ" 
				                   <% if ("いいえ".equals(request.getParameter("allDayOff" + set))) { %> checked <% } %> <%= (set == 1) ? "required" : "" %> data-required="true">
				            <label class="form-check-label" for="allDayOffNo<%= set %>">
				                いいえ
				            </label>
				        </div>
				    </div>
				</div>
				<p>終日休んだ場合は欠席期間時限数を入力してください</p>
				<p>終日休んでいない場合は遅刻時限数か早退時限数を入力してください</p>
				<p>朝と帰りは出席し、途中の授業のみ欠席する場合は早退欄に入力してください</p>
	            <!-- 欠席期間時限数 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="deadTime<%= set %>">欠席期間時限数</label>
	                <select id="deadTime<%= set %>" name="deadTime<%= set %>" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.year}'/>">
	                    <option value="">-- 時限数 --</option>
	                    <% for(int i=1; i <=110; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>時限
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <!-- 遅刻時限数 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="latenessTime<%= set %>">遅刻時限数</label>
	                <select id="latenessTime<%= set %>" name="latenessTime<%= set %>" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.year}'/>">
	                    <option value="">-- 時限数 --</option>
	                    <% for(int i=1; i <=8; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>時限
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <!-- 早退時限数 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="leaveEarlyTime<%= set %>">早退時限数</label>
	                <select id="leaveEarlyTime<%= set %>" name="leaveEarlyTime<%= set %>" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.year}'/>">
	                    <option value="">-- 時限数 --</option>
	                    <% for(int i=1; i <=8; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>時限
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <!-- 証明添付有無 -->
				<div class="col-md-12 mb-3 text-center">
				    <label class="form-label text-center">証明添付有無（雇用保険受給者、職業訓練受講給付金受給者のみ対象）</label>
				    <span class="required-label">必須</span>
				    <div class="d-flex align-items-center justify-content-center">
				        <div class="form-check radio-spacing">
				            <input class="form-check-input" type="radio" name="attachmentOfCertificate<%= set %>" id="attachmentOfCertificateYes<%= set %>" value="有" 
				                   <% if ("有".equals(request.getParameter("attachmentOfCertificate" + set))) { %> checked <% } %> <%= (set == 1) ? "required" : "" %> data-required="true">
				            <label class="form-check-label" for="attachmentOfCertificateYes<%= set %>">
				                有
				            </label>
				        </div>
				        <div class="form-check"> 
				            <input class="form-check-input" type="radio" name="attachmentOfCertificate<%= set %>" id="attachmentOfCertificateNo<%= set %>" value="無" 
				                   <% if ("無".equals(request.getParameter("attachmentOfCertificate" + set))) { %> checked <% } %> <%= (set == 1) ? "required" : "" %> data-required="true">
				            <label class="form-check-label" for="attachmentOfCertificateNo<%= set %>">
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
        <!-- エラー表示  -->
		<c:forEach var="attr" items="${pageContext.request.attributeNames}">
		    <c:set var="attrName" value="${attr}" />
		    <c:if test="${fn:endsWith(attrName, 'Error')}">
		        <c:set var="errorMsg" value="${requestScope[attrName]}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="alert alert-danger text-center input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>
		    </c:if>
		</c:forEach>     
		<!-- 追加ボタン  -->
		<button type="button" id="addSetBtn" class="w-100 btn btn-lg btn-success mb-3">行の追加（休業期間が複数ある場合）</button>
	    <!-- トークンの格納  -->
	    <input type="hidden" name="csrfToken" value="${csrfToken}">
		<!-- 作成ボタン -->
		<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
	</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
