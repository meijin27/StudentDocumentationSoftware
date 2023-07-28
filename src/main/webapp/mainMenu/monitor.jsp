<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1 class="h2">現在の登録状況</h1><br>
	        <p>ID; ${sessionScope.monitorId}</p>
  	        <p>アカウント名: ${sessionScope.account}</p>	
	        <p>パスワード: ${sessionScope.password}</p>
   	        <p>マスターキー: ${sessionScope.monitorMasterKey}</p>
   	        <p>IV: ${sessionScope.iv}</p>
	        <p>秘密の質問: ${sessionScope.secretQuestion}</p>
	        <p>秘密の質問の答え: ${sessionScope.secretAnswer}</p>	    
	        <p>姓: ${sessionScope.lastName}</p>
	        <p>名: ${sessionScope.firstName}</p>
	        <p>電話番号: ${sessionScope.tel}</p>
	        <p>郵便番号: ${sessionScope.postCode}</p>
	        <p>住所: ${sessionScope.address}</p>		        
	        <p>生年: ${sessionScope.birthYear}</p>
	        <p>生月: ${sessionScope.birthMonth}</p>
	        <p>生日: ${sessionScope.birthDay}</p>
	        <p>学生種別: ${sessionScope.studentType}</p>
	        <p>クラス名: ${sessionScope.className}</p>
	        <p>学生番号: ${sessionScope.studentNumber}</p>
	        <p>学年: ${sessionScope.schoolYear}</p>		       
	        <p>組: ${sessionScope.classNumber}</p>		        
	    <form action="Monitor.action" method="post">
	    
	        <button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">更新</button>
	    </form>
</main>
<c:import url="/footer/footer-main-menu.jsp" />
