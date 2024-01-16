<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-firstSetting.jsp" />
<c:import url="/token/token.jsp" />

<!-- 初期登録用JSP  -->
<main class="form-firstSetting w-100 m-auto flex-shrink-0">

	<%
		// 初期設定未確認セッションの削除(フィルター機能によりセッションに初期設定未確認情報がない場合、初期設定確認ｊｓｐにアクセスできない)
		request.getSession().removeAttribute("firstSettingCheck");	
	%>

    <h2 class="p-5 text-center">初期設定</h2>
    <div class="content">
	    <form action="FirstSetting.action" method="post" autocomplete="off">
            <!-- 入力エラーがある場合のみエラーメッセージを表示 -->
           <div class="col-md-12 mb-5">
				<c:set var="hasError" value="false" />
	            <c:forEach var="attr" items="${pageContext.request.attributeNames}">
	                <c:if test="${fn:endsWith(attr, 'Error')}">
	                    <c:set var="hasError" value="true" />
	                </c:if>
	            </c:forEach>
	
	            <c:if test="${hasError}">
                    <c:import url="/errorMessage/error-message.jsp" />
	            </c:if>        
            </div>       
	        <div class="row">
       			<p class="border-bottom"></p>
	            <!-- 姓 -->
				<div class="col-md-6 mb-3">
				    <label class="form-label" for="lastName">名前</label>
				    <span class="required-label">必須</span>
				    <input class="form-control ${not empty requestScope['lastNameError'] ? 'error-input' : ''}" id="lastName" type="text" name="lastName" placeholder="田中" value="<c:out value='${sessionScope.lastName}' />" required>
    				<div class="d-flex align-items-center">
	    				<small class="text-muted">姓</small>
			        	<!-- エラー表示  -->
				        <c:set var="errorMsg" value="${requestScope['lastNameError']}" />
				        <c:if test="${not empty errorMsg}">
				            <div class="small-font red input-field margin-left-10" role="alert">
				                <c:out value="${errorMsg}" />
				            </div>
				        </c:if>
			        </div>
				</div>

	            <!-- 名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label invisible-text" for="firstName">名</label>
	                <input class="form-control ${not empty requestScope['firstNameError'] ? 'error-input' : ''}" type="text"id="firstName" name="firstName" placeholder="太郎" value="<c:out value='${sessionScope.firstName}' />" required>
    				<div class="d-flex align-items-center">
	    				<small class="text-muted">名</small>
			        	<!-- エラー表示  -->
				        <c:set var="errorMsg" value="${requestScope['firstNameError']}" />
				        <c:if test="${not empty errorMsg}">
				            <div class="small-font red input-field margin-left-10" role="alert">
				                <c:out value="${errorMsg}" />
				            </div>
				        </c:if>
			        </div>
	            </div>
   	            <!-- 姓（ふりがな） -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="lastNameRuby">ふりがな</label>
				    <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['lastNameRubyError'] ? 'error-input' : ''}" type="text" id="lastNameRuby" name="lastNameRuby" placeholder="たなか" value="<c:out value='${sessionScope.lastNameRuby}' />" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['lastNameRubyError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
	            <!-- 名（ふりがな） -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label invisible-text" for="firstNameRuby">名（ふりがな）</label>
	                <input class="form-control ${not empty requestScope['firstNameRubyError'] ? 'error-input' : ''}" type="text" id="firstNameRuby" name="firstNameRuby" placeholder="たろう" value="<c:out value='${sessionScope.firstNameRuby}' />" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['firstNameRubyError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
       			<p class="border-bottom"></p>
	            <!-- 生年月日 -->
	            <div class="col-md-4 mb-0">
	                <label class="form-label" for="birthYear">生年月日</label>
	                <span class="required-label">必須</span>
	                <select id="birthYear" name="birthYear" class="form-control ${not empty requestScope['birthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${sessionScope.birthYear != null ? sessionScope.birthYear : param.birthYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-60; i <= currentYear - 14;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="birthMonth">月</label>
	                <select id="birthMonth" name="birthMonth" class="form-control ${not empty requestScope['birthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${sessionScope.birthMonth != null ? sessionScope.birthMonth : param.birthMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="birthDay">日</label>
	                <select id="birthDay" name="birthDay" class="form-control ${not empty requestScope['birthError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${sessionScope.birthDay != null ? sessionScope.birthDay : param.birthDay}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	        	<!-- エラー表示  -->
	            <div class="col-md-12 mb-5">			        	
			        <c:set var="errorMsg" value="${requestScope['birthError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
       			<p class="border-bottom"></p>
	            <!-- 郵便番号 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="postCode">郵便番号</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['postCodeError'] ? 'error-input' : ''}" type="text" id="postCode" name="postCode" placeholder="2310017" value="<c:out value='${sessionScope.postCode}' />" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['postCodeError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
	            <!-- 住所 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="address">住所</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['addressError'] ? 'error-input' : ''}" type="text" id="address" name="address" placeholder="神奈川県横浜市中区港町１丁目１ 横浜スタジアム" value="<c:out value='${sessionScope.address}' />" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['addressError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
	            
       			<p class="border-bottom"></p>
  	            <!-- 電話番号 -->
	            <div class="col-md-3 mb-5">
	                <label class="form-label" for="tel">電話番号</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['telError'] ? 'error-input' : ''}" type="text" id="tel" name="tel" placeholder="08011112222" value="<c:out value='${sessionScope.tel}' />" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['telError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
       			<p class="border-bottom"></p>
  	            <!-- 入校年月日 -->
	            <div class="col-md-4 mb-0">
	                <label class="form-label" for="admissionYear">入学日</label>
	                <span class="required-label">必須</span>
	                <select id="admissionYear" name="admissionYear" class="form-control ${not empty requestScope['admissionError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${sessionScope.admissionYear != null ? sessionScope.admissionYear : param.admissionYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 年 --</option>
	                    <% for(int i=currentYear-2; i <= currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="admissionMonth">月</label>
	                <select id="admissionMonth" name="admissionMonth" class="form-control ${not empty requestScope['admissionError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${sessionScope.admissionMonth != null ? sessionScope.admissionMonth : param.admissionMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="admissionDay">日</label>
	                <select id="admissionDay" name="admissionDay" class="form-control ${not empty requestScope['admissionError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${sessionScope.admissionDay != null ? sessionScope.admissionDay : param.admissionDay}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	        	<!-- エラー表示  -->
	            <div class="col-md-12 mb-3">			        	
			        <c:set var="errorMsg" value="${requestScope['admissionError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
	            <!-- 学生の種類 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="studentType">学生の種類</label>
	                <span class="required-label">必須</span>
	                <select id="studentType"  name="studentType" class="form-control ${not empty requestScope['studentTypeError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${sessionScope.studentType != null ? sessionScope.studentType : param.studentType}'/>" required>
	                    <option value="" disabled selected class="display_none">- 学生の種類 -</option>
	                    <option value="一般学生">一般学生</option>
	                    <option value="留学生">留学生</option>
	                    <option value="職業訓練生">職業訓練生</option>
	                </select>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['studentTypeError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
   	            <div class="col-md-8 mb-3"></div>
	            <!-- 学籍番号 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="studentNumber">学籍番号</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['studentNumberError'] ? 'error-input' : ''}" type="text" id="studentNumber"  name="studentNumber" placeholder="240001" value="<c:out value='${sessionScope.studentNumber}' />" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['studentNumberError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
  	            <div class="col-md-8 mb-3"></div>
	            <!-- クラス名 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="className">クラス名</label>
	                <span class="required-label">必須</span>
	                <select id="className" name="className" class="form-control ${not empty requestScope['classNameError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${sessionScope.className != null ? sessionScope.className : param.className}'/>" required>
	                    <option value="" disabled selected class="display_none">-- クラス名 --</option>
	                    <option value="ＩＴ・ゲームソフト科">ＩＴ・ゲームソフト科</option>
	                    <option value="ＡＩ・データサイエンス科">ＡＩ・データサイエンス科</option>
	                    <option value="デジタルビジネスデザイン科">デジタルビジネスデザイン科</option>
   	                    <option value="グローバルＩＴシステム科">グローバルＩＴシステム科</option>
	                    <option value="グローバルＩＴビジネス科">グローバルＩＴビジネス科</option>
	                    <option value="ロボット・ＩｏＴソフト科">ロボット・ＩｏＴソフト科</option>
   	                    <option value="ＩＴライセンス科（通信制）">ＩＴライセンス科（通信制）</option>
	                </select>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['classNameError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
	            <!-- 学年 -->
	            <div class="col-md-3 mb-5">
	                <label class="form-label" for="schoolYear">学年</label>
	                <span class="required-label">必須</span>
	                <select id="schoolYear" name="schoolYear" class="form-control ${not empty requestScope['schoolYearError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${sessionScope.schoolYear != null ? sessionScope.schoolYear : param.schoolYear}'/>" required>
	                    <option value="" disabled selected class="display_none">- 学年 -</option>
	                    <% for(int i=1; i <=2; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['schoolYearError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
	            <!-- 組 -->
		        <div class="col-md-3 mb-5">
	                <label class="form-label" for="classNumber">組</label>
	                <span class="required-label">必須</span>
	                <select id="classNumber"  name="classNumber" class="form-control ${not empty requestScope['classNumberError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${sessionScope.classNumber != null ? sessionScope.classNumber : param.classNumber}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 組 --</option>
	                    <% for(int i=1; i <=3; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>組
	                        </option>
	                    <% } %>
	                </select>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['classNumberError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
       			<p class="border-bottom"></p>
	
	        </div>
	        <!-- 利用規約確認ボタン  -->
	        <button type="button" class="w-100 btn btn-custom  btn-secondary mb-3" data-bs-toggle="modal"
	            data-bs-target="#termsModal" id="termsLink">
	            利用規約を確認する
	        </button>
	
	        <div class="modal fade" id="termsModal" tabindex="-1" aria-labelledby="termsModalLabel" aria-hidden="true">
	            <div class="modal-dialog modal-dialog-scrollable">
	                <div class="modal-content">
	                    <div class="modal-header">
	                        <h5 class="modal-title" id="termsModalLabel">利用規約</h5>
	                        <button type="button" class="btn-close" data-bs-dismiss="modal"
	                            aria-label="Close"></button>
	                    </div>
	                    <div class="modal-body" data-terms-url="<%=request.getContextPath()%>/txt/terms_of_use.txt">
	                    </div>
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <!-- プライバシーポリシー確認ボタン  -->
	        <button type="button" class="w-100 btn btn-custom  btn-secondary mb-3" data-bs-toggle="modal"
	            data-bs-target="#privacyModal" id="privacyLink">
	            プライバシーポリシーを確認する
	        </button>
	
	        <div class="modal fade" id="privacyModal" tabindex="-1" aria-labelledby="privacyModalLabel"
	            aria-hidden="true">
	            <div class="modal-dialog modal-dialog-scrollable">
	                <div class="modal-content">
	                    <div class="modal-header">
	                        <h5 class="modal-title" id="privacyModalLabel">プライバシーポリシー</h5>
	                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	                    </div>
	                    <div class="modal-body"
	                        data-terms-url="<%=request.getContextPath()%>/txt/privacy_policy.txt">
	                    </div>
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
	                    </div>
	                </div>
	            </div>
	        </div>
			<!-- 同意チェックボックス  -->
			<div class="mb-5">
			    <input class="form-check-input" id="agreeCheckbox" type="checkbox" name="agree" value="1">
			    <label class="form-check-label" for="agreeCheckbox">利用規約及びプライバシーポリシーに同意する</label>
	        	<!-- エラー表示  -->
		        <c:set var="errorMsg" value="${requestScope['agreeError']}" />
		        <c:if test="${not empty errorMsg}">
		            <div class="small-font red input-field" role="alert">
		                <c:out value="${errorMsg}" />
		            </div>
		        </c:if>
			</div>
		    <!-- トークンの格納  -->
		    <input type="hidden" name="csrfToken" value="${csrfToken}">
			<!-- 次へボタン  -->
		    <button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">次へ</button>
	    </form>
    </div>
</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.0-beta3/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>//js/modal.js"></script>
<script src="<%=request.getContextPath()%>/js/first_setting.js"></script>

<c:import url="/footer/footer.jsp" />


