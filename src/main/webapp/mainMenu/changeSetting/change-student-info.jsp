<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 入学年月日・学生種類・学籍番号・クラス・学年・組の設定変更用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>入学年月日・学生種類・学籍番号・クラス・学年・組の変更</h1>
    </div>
    <div class="container">
		<form action="ChangeStudentInfo.action" method="post" autocomplete="off">
            <!-- 入力エラーがある場合のみエラーメッセージを表示 -->
            <div class="col-md-12 mb-5">
				<c:set var="hasError" value="false" />
	            <c:forEach var="attr" items="${pageContext.request.attributeNames}">
	                <c:if test="${fn:endsWith(attr, 'Error')}">
	                    <c:set var="hasError" value="true" />
	                </c:if>
	            </c:forEach>
		        <c:set var="innerErrorMsg" value="${requestScope['innerError']}" />
		        <c:if test="${not empty innerErrorMsg}">
					<div class="alert alert-danger text-center input-field" role="alert">
		                <STRONG><c:out value="${innerErrorMsg}" /></STRONG>
		            </div>
		        </c:if>        			          				
				<c:if test="${hasError and empty innerErrorMsg}">
                    <c:import url="/errorMessage/error-message.jsp" />
	            </c:if>
            </div>   
	        <div class="row">
  	            <!-- 入学年月日-->
	            <div class="col-md-4 mb-0">
	                <label class="form-label" for="admissionYear">入学年月日</label>
	                <span class="required-label">必須</span>
	                <select id="admissionYear" name="admissionYear" class="form-control ${not empty requestScope['admissionError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.admissionYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-0">
	                <label class="form-label invisible-text" for="admissionMonth">月</label>
	                <select id="admissionMonth" name="admissionMonth" class="form-control ${not empty requestScope['admissionError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.admissionMonth}'/>" required>
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
	                <select id="admissionDay" name="admissionDay" class="form-control ${not empty requestScope['admissionError'] ? 'error-inpsut' : ''} select-center auto-select" data-selected-value="<c:out value='${param.admissionDay}'/>" required>
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
	                <select id="studentType"  name="studentType" class="form-control ${not empty requestScope['studentTypeError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.studentType}'/>" required>
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
	                <input class="form-control ${not empty requestScope['studentNumberError'] ? 'error-input' : ''}" type="text" id="studentNumber"  name="studentNumber" placeholder="240001" value="<c:out value='${studentNumber}' />" required>
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
	                <select id="className" name="className" class="form-control ${not empty requestScope['classNameError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.className}'/>" required>
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
	                <select id="schoolYear" name="schoolYear" class="form-control ${not empty requestScope['schoolYearError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.schoolYear}'/>" required>
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
	                <select id="classNumber"  name="classNumber" class="form-control ${not empty requestScope['classNumberError'] ? 'error-input' : ''} select-center auto-select" data-selected-value="<c:out value='${param.classNumber}'/>" required>
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
 	        </div>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">
   	        <!-- 変更ボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

