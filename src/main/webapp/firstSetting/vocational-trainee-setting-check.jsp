<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-firstSetting.jsp" />
<c:import url="/token/token.jsp" />>

<!-- 職業訓練生の初期設定確認用JSP  -->
<main class="form-firstSetting col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	    <h2 class="p-5">入力内容確認</h2><br>
	    <form action="VocationalTraineeSettingCheck.action" method="post">
	
		    <div class="data-row">
		        <div class="data-label"><p>ハローワーク名:</p></div>
		        <div class="data-value"><p><c:out value='${namePESO}'/></p></div>
		    </div>

		    <div class="data-row">
		        <div class="data-label"><p>雇用保険有無:</p></div>
		        <div class="data-value"><p><c:out value='${employmentInsurance}'/></p></div>	        
		    </div>
		        
		    <div class="data-row">
				<div class="data-label"><p>支 給 番 号:</p></div>
				<div class="data-value"><p><c:out value='${supplyNumber}'/></p></div>	        
		    </div>
		    
		    <div class="data-row">			
				<div class="data-label"><p>出 席 番 号:</p></div>
				<div class="data-value"><p><c:out value='${attendanceNumber}'/></p></div>	      
		    </div>
				
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">	      
            <!-- 登録ボタン -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">登録</button>
	     </form>
	     <!-- 職業訓練生の初期登録JSPへ戻るボタン  -->
	     <form action="VocationalTraineeSettingCheck.action" method="post">
		    <input type="hidden" name="goBack" value="true">
   		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">	     
		    <!-- 戻るボタン  -->	
		    <input type="submit" class="w-100 btn btn-lg btn-secondary mb-3" value="戻る">
		 </form>      
	</div>     
</main>

<c:import url="/footer/footer.jsp" />

