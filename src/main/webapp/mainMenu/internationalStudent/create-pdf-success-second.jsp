<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="container">
		<h3 class="pt-1 pb-5 pl-5 pr-5"><strong>${createPDF}</strong></h3>
		<p class="md-5">ＰＤＦ保存先はダウンロードフォルダです。(The PDF is saved in the download folder.)</p>
		<a href="<%=request.getContextPath()%>/mainMenu/internationalStudent/period-update-third.jsp" class="w-100 btn btn-lg btn-success mb-3">３枚目作成画面へ移行（Move to the screen for creating the third sheet）</a>
		<a href="<%=request.getContextPath()%>/mainMenu/main-menu.jsp" class="w-100 btn btn-lg btn-primary mb-3">HOME</a>
	</div>
</main>


<c:import url="/footer/footer-main-menu.jsp" />