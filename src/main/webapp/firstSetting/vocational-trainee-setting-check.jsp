<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-firstSetting.jsp" />

<main class="form-firstSetting w-100 m-auto flex-shrink-0">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	    <h2 class="p-5">入力内容確認</h2><br>
	    <form action="VocationalTraineeSettingCheck.action" method="post">
	
			<div class="data-row">
		        <div class="data-label"><p>公共職業安定所: </p></div>
		        <div class="data-value"><p>${namePESO}</p></div>
		    </div>
		    <input type="hidden" name="namePESO" value="${namePESO}">

		    <div class="data-row">
		        <div class="data-label"><p>支給番号:</p></div>
		        <div class="data-value"><p>${supplyNumber}</p></div>
		    </div>
		    <input type="hidden" name="supplyNumber" value="${supplyNumber}">

		    <div class="data-row">
				<div class="data-label"><p>出席番号:</p></div>
				<div class="data-value"><p>${attendanceNumber}</p></div>
		    </div>
		    <input type="hidden" name="attendanceNumber" value="${attendanceNumber}">
		    
		    <div class="data-row">
				<div class="data-label"><p>雇用保険の有無:</p></div>
				<div class="data-value"><p>${employmentInsurance}</p></div>
		    </div>
		    <input type="hidden" name="employmentInsurance" value="${employmentInsurance}">

            <!-- 登録ボタン -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">登録</button>
	     </form>
	    <form action="VocationalTraineeSettingCheck.action" method="post">
		    <input type="hidden" name="namePESO" value="${namePESO}">
		    <input type="hidden" name="supplyNumber" value="${supplyNumber}">
		    <input type="hidden" name="attendanceNumber" value="${attendanceNumber}">
		    <input type="hidden" name="employmentInsurance" value="${employmentInsurance}">
		    <input type="hidden" name="goBack" value="true">
		    <input type="submit" class="w-100 btn btn-lg btn-secondary mb-3" value="戻る">
		</form>      
	</div>     
</main>

<c:import url="/footer/footer.jsp" />

