<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>

<!-- 職業訓練生登録情報変更用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>職業訓練生登録情報変更</h1>
    </div>
    <div class="container">
		<form action="ChangeVocationalTrainee.action" method="post">
	        <div class="row">
	            <!-- 公共職業安定所名 Public Employment Security Office-->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="">公共職業安定所名</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="namePESO" placeholder="厚木" value="${namePESO}" required>
	            </div>

	            <!-- 支給番号 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="">支給番号</label>
	                <input class="form-control" type="text" name="supplyNumber" placeholder="22-000001-5"${supplyNumber}">
	            </div>	           

	             <!-- 出席番号 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="">出席番号</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="attendanceNumber" placeholder="16"value="${attendanceNumber}" required>
	            </div>

				<!-- 雇用保険 -->
				<div class="col-md-6 mb-5">
				    <label class="form-label">雇用保険の有無</label>
				    <span class="text-danger">*</span>
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
				</div>

	        </div>
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
	        <c:if test="${not empty  attendanceNumberError}">
	            <div class="alert alert-danger" role="alert">
	                ${attendanceNumberError}
	            </div>
	        </c:if>
	        <c:if test="${not empty  innerError}">
	            <div class="alert alert-danger" role="alert">
	                ${innerError}
	            </div>
	        </c:if>
	        </div>
			<!-- サブミットボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

