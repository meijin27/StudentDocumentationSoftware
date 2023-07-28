<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-firstSetting.jsp" />

<main class="form-firstSetting w-100 m-auto flex-shrink-0">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	    <h2 class="p-5">入力内容確認</h2><br>
	    <form action="FirstSettingCheck.action" method="post">
	
			<div class="data-row">
		        <div class="data-label"><p>姓: </p></div>
		        <div class="data-value"><p>${sessionScope.lastName}</p></div>
		    </div>
		
		    <div class="data-row">
		        <div class="data-label"><p>名:</p></div>
		        <div class="data-value"><p>${sessionScope.firstName}</p></div>
		    </div>
		
		    <div class="data-row">
				<div class="data-label"><p>電話番号:</p></div>
				<div class="data-value"><p>${sessionScope.tel}</p></div>
		    </div>
		
		    <div class="data-row">
				<div class="data-label"><p>郵便番号:</p></div>
				<div class="data-value"><p>${sessionScope.postCode}</p></div>
		    </div>
		
		    <div class="data-row">
		        <div class="data-label"><p>住所:</p></div>
		        <div class="data-value"><p>${sessionScope.address}</p></div>
		    </div>
		
			<div class="data-row">
		        <div class="data-label"><p>生年:</p></div>
		        <div class="data-value"><p>${sessionScope.birthYear}</p></div>
		    </div>
		
		    <div class="data-row">
		        <div class="data-label"><p>生月:</p></div>
		        <div class="data-value"><p>${sessionScope.birthMonth}</p></div>
		    </div>
		
		    <div class="data-row">
		        <div class="data-label"><p>生日:</p></div>
		        <div class="data-value"><p>${sessionScope.birthDay}</p></div>
		    </div>
		
		    <div class="data-row">
				<div class="data-label"><p>学生種別:</p></div>
				<div class="data-value"><p>${sessionScope.studentType}</p></div>
		    </div>
		
		    <div class="data-row">
				<div class="data-label"><p>クラス名:</p></div>
				<div class="data-value"><p>${sessionScope.className}</p></div>
		    </div>
		
		    <div class="data-row">
		        <div class="data-label"><p>学生番号:</p></div>
		        <div class="data-value"><p>${sessionScope.studentNumber}</p></div>
		    </div>
		
			<div class="data-row">
		        <div class="data-label"><p>学年:</p></div>
		        <div class="data-value"><p>${sessionScope.schoolYear}</p></div>
		    </div>
		
		    <div class="data-row">
		        <div class="data-label"><p>組:</p></div>
		        <div class="data-value"><p>${sessionScope.classNumber}</p></div>
		    </div>        
	
            <!-- 登録ボタン -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">登録</button>
			<a href="first-setting.jsp" class="w-100 btn btn-lg btn-secondary mb-3">戻る</a>         
	     </form>
	</div>     
</main>

<c:import url="/footer/footer.jsp" />

