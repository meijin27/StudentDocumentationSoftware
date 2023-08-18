<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">現在の登録状況</h1><br>
		<form action="Monitor.action" method="post">
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">登録情報の取得</button>
		</form>

	    <div class="data-row">
	        <div class="data-label"><p>姓: </p></div>
	        <div class="data-value"><p>${lastName}</p></div>
  	        <div class="data-label-second"><p>名:</p></div>
	        <div class="data-value"><p>${firstName}</p></div>
	    </div>

	    <div class="data-row">
	        <div class="data-label"><p>姓（ふりがな）: </p></div>
	        <div class="data-value"><p>${lastNameRuby}</p></div>
	        <div class="data-label-second"><p>名（ふりがな）:</p></div>
	        <div class="data-value"><p>${firstNameRuby}</p></div>
	    </div>
	
	    <div class="data-row">
  			<div class="data-label"><p>郵便番号:</p></div>
			<div class="data-value"><p>${postCode}</p></div>

	    </div>
	
	    <div class="data-row">
	        <div class="data-label"><p>住所:</p></div>
	        <div class="data-value-0px"><p>${address}</p></div>
	    </div>
	
		<div class="data-row">
			<div class="data-label"><p>電話番号:</p></div>
			<div class="data-value"><p>${tel}</p></div>
	        <div class="data-label-second"><p>生年月日:</p></div>
	        <div class="data-value-0px"><p>${birthYear}年${birthMonth}月${birthDay}日</p></div>
	    </div>
		
	    <div class="data-row">
	        <div class="data-label"><p>学生番号:</p></div>
	        <div class="data-value"><p>${studentNumber}</p></div>	    
  	        <div class="data-label-second"><p>入学年月日:</p></div>
	        <div class="data-value-0px"><p>${admissionYear}年${admissionMonth}月${admissionDay}日</p></div>
	    </div>
	    
	    <div class="data-row">
			<div class="data-label"><p>学生種別:</p></div>
			<div class="data-value"><p>${studentType}</p></div>	    
			<div class="data-label-second"><p>クラス名:</p></div>
			<div class="data-value-0px"><p>${className}</p></div>
	    </div>

	    <div class="data-row">			        
	    	<div class="data-label"><p>学年:</p></div>
	        <div class="data-value"><p>${schoolYear}年</p></div>
  	        <div class="data-label-second "><p>組:</p></div>
	        <div class="data-value-0px"><p>${classNumber}組</p></div>   
	    </div>

	    <div class="data-row">
	        <div class="data-label"><p>ハローワーク:</p></div>
	        <div class="data-value"><p>${namePESO}</p></div>
			<div class="data-label-second"><p>支給番号:</p></div>
			<div class="data-value-0px"><p>${supplyNumber}</p></div>	        
	    </div>
	    <div class="data-row">			
			<div class="data-label"><p>出席番号:</p></div>
			<div class="data-value"><p>${attendanceNumber}</p></div>	        
	        <div class="data-label-second"><p>雇用保険有無:</p></div>
	        <div class="data-value-0px"><p>${employmentInsurance}</p></div>	        
	    </div>
	
        <c:if test="${not empty innerError}">
            <div class="alert alert-danger text-center input-field" role="alert">
                ${innerError}
            </div>
        </c:if>	
    </div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />
