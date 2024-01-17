<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-firstSetting.jsp" />
<c:import url="/token/token.jsp" />

<!-- 職業訓練生の初期設定用JSP  -->
<main class="form-firstSetting w-100 m-auto flex-shrink-0">

	<%
		// 職業訓練生情報未確認セッションの削除(フィルター機能によりセッションに職業訓練生情報未確認情報がない場合、職業訓練生情報確認ｊｓｐにアクセスできない)
		request.getSession().removeAttribute("vocationalSettingCheck");	
	%>

    <h2 class="p-5">職業訓練生の設定</h2>
    <div class="content">
	    <form action="VocationalTraineeSetting.action" method="post" autocomplete="off">
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
	            <!-- 公共職業安定所名 Public Employment Security Office-->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="namePESO">公共職業安定所名</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['namePESOError'] ? 'error-input' : ''}" type="text" id="namePESO" name="namePESO" placeholder="厚木" value="<c:out value='${namePESO}'/>" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['namePESOError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
	            <div class="col-md-6 mb-3"></div>
				<!-- 雇用保険 -->
				<div class="col-md-6 mb-3 text-center">
				    <label class="form-label" for="employmentInsuranceYes">雇用保険の有無</label>
				    <span class="required-label">必須</span>
				    <div class="d-flex align-items-center justify-content-center">
				        <div class="form-check form-check-inline mr-lg-5">
				            <input class="form-check-input" type="radio" name="employmentInsurance" id="employmentInsuranceYes" value="有" 
				                   <% if ("有".equals(session.getAttribute("employmentInsurance"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="employmentInsuranceYes">
				                有
				            </label>
				        </div>
				        <div class="form-check form-check-inline ml-lg-5">
				            <input class="form-check-input" type="radio" name="employmentInsurance" id="employmentInsuranceNo" value="無"
				                   <% if ("無".equals(session.getAttribute("employmentInsurance"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="employmentInsuranceNo">
				                無
				            </label>
				        </div>
				    </div>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['employmentInsuranceError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
				</div>
	            <div class="col-md-6 mb-3"></div>
	            <!-- 支給番号 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="supplyNumber">支給番号</label>
	                <input class="form-control ${not empty requestScope['supplyNumberError'] ? 'error-input' : ''}" type="text" id="supplyNumber" name="supplyNumber" placeholder="22-000001-5" value="<c:out value='${supplyNumber}'/>">
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['supplyNumberError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>	           
	            <div class="col-md-6 mb-3"></div>
	             <!-- 出席番号 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="attendanceNumber">出席番号</label>
	                <span class="required-label">必須</span>
	                <input class="form-control ${not empty requestScope['attendanceNumberError'] ? 'error-input' : ''}" type="text" id="attendanceNumber" name="attendanceNumber" placeholder="16" value="<c:out value='${attendanceNumber}'/>" required>
		        	<!-- エラー表示  -->
			        <c:set var="errorMsg" value="${requestScope['attendanceNumberError']}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="small-font red input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
	            </div>
	            <div class="col-md-6 mb-5"></div>

	        </div> 
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">	        
	        <!-- サブミットボタン  -->
	        <button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">次へ</button>

	    </form>
    </div>
</main>

<c:import url="/footer/footer.jsp" />


