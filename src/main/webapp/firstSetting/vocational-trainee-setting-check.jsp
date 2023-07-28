<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-firstSetting.jsp" />

<main class="form-firstSetting w-100 m-auto flex-shrink-0">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	    <h2 class="p-5">入力内容確認</h2><br>
	    <form action="VocationalTraineeSettingCheck.action" method="post">
	
			<div class="data-row">
		        <div class="data-label"><p>公共職業安定所: </p></div>
		        <div class="data-value"><p>${sessionScope.namePESO}</p></div>
		    </div>
		
		    <div class="data-row">
		        <div class="data-label"><p>支給番号:</p></div>
		        <div class="data-value"><p>${sessionScope.supplyNumber}</p></div>
		    </div>
		
		    <div class="data-row">
				<div class="data-label"><p>出席番号:</p></div>
				<div class="data-value"><p>${sessionScope.attendanceNumber}</p></div>
		    </div>
		    <div class="data-row">
				<div class="data-label"><p>雇用保険の有無:</p></div>
				<div class="data-value"><p>${sessionScope.employmentInsurance}</p></div>
		    </div>

            <!-- 登録ボタン -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">登録</button>
			<a href="vocational-trainee-setting.jsp" class="w-100 btn btn-lg btn-secondary mb-3">戻る</a>         
	     </form>
	</div>     
</main>

<c:import url="/footer/footer.jsp" />

