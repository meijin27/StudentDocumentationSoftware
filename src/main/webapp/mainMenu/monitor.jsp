<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">現在の登録状況</h1><br>

	    <div class="data-row">
	        <div class="data-label"><p>ID:</p></div>
	        <div class="data-value"><p>${sessionScope.monitorId}</p></div>
	    </div>
	
	    <div class="data-row">
	        <div class="data-label"><p>アカウント名:</p></div>
	        <div class="data-value"><p>${sessionScope.account}</p></div>
	    </div>
	
	    <div class="data-row">
	        <div class="data-label"><p>パスワード:</p></div>
	        <div class="data-value"><p>${sessionScope.password}</p></div>
	    </div>
	
	    <div class="data-row">
			<div class="data-label"><p>マスターキー:</p></div>
			<div class="data-value"><p>${sessionScope.monitorMasterKey}</p></div>
	    </div>
	
	    <div class="data-row">
			<div class="data-label"><p>IV:</p></div>
			<div class="data-value"><p>${sessionScope.iv}</p></div>
	    </div>
	
	    <div class="data-row">
	        <div class="data-label"><p>秘密の質問:</p></div>
	        <div class="data-value"><p>${sessionScope.secretQuestion}</p></div>
	    </div>
	
		<div class="data-row">
	        <div class="data-label"><p>秘密の質問の答え:</p></div>
	        <div class="data-value"><p>${sessionScope.secretAnswer}</p></div>
	    </div>
	
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
	
	    <div class="data-row">
	        <div class="data-label"><p>公共職業安定所名:</p></div>
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
	        <div class="data-label"><p>雇用保険有無:</p></div>
	        <div class="data-value"><p>${sessionScope.employmentInsurance}</p></div>
	    </div>
	
	
			
		<form action="Monitor.action" method="post">
		
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">更新</button>
		</form>
    </div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />
