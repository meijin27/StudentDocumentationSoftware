<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-firstSetting.jsp" />
<c:import url="/token/token.jsp" />

<!-- 初期登録内容確認用JSP  -->
<main class="form-firstSetting col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
 	    <h2 class="p-5">入力内容確認</h2><br>
        <form action="FirstSettingCheck.action" method="post">
		    <div class="data-row-marginbottom-0">
		        <div class="data-label "><p class="form-label">ふ り が な: </p></div>
		        <div class="data-value  width-150"><p class="form-label"><c:out value='${lastNameRuby}'/></p></div>
		        <div class="data-value-second "><p class="form-label"><c:out value='${firstNameRuby}'/></p></div>
		    </div>
		    
		    <div class="data-row">
		        <div class="data-label"><p>名　　前: </p></div>
		        <div class="data-value big-font width-150 top-aligned"><p><c:out value='${lastName}'/></p></div>
		        <div class="data-value-second big-font top-aligned"><p><c:out value='${firstName}'/></p></div>
		    </div> 

		    <div class="data-row">
	  			<div class="data-label"><p>郵便番号:</p></div>
				<div class="data-value"><p><c:out value='${postCode}'/></p></div>
	
		    </div>
		
		    <div class="data-row">
		        <div class="data-label"><p>住　　所:</p></div>
		        <div class="data-value"><p><c:out value='${address}'/></p></div>
		    </div>
		
			<div class="data-row">
				<div class="data-label"><p>電話番号:</p></div>
				<div class="data-value"><p><c:out value='${tel}'/></p></div>
		    </div>
				
			<div class="data-row">
		        <div class="data-label"><p>生年月日:</p></div>
		        <div class="data-value"><p><c:out value="${birthYear}" />年<c:out value="${birthMonth}" />月<c:out value="${birthDay}" />日</p></div>
		    </div>
			
		    <div class="data-row">
		        <div class="data-label"><p>学生番号:</p></div>
		        <div class="data-value"><p><c:out value='${studentNumber}'/></p></div>	    
	  	    </div>
	  	    
		    <div class="data-row">
	  	        <div class="data-label"><p>入学年月日:</p></div>
		        <div class="data-value"><p><c:out value="${admissionYear}" />年<c:out value="${admissionMonth}" />月<c:out value="${admissionDay}" />日</p></div>
		    </div>
		    
		    <div class="data-row">
	  	        <div class="data-label"><p></p></div>
				<div class="data-value big-font2"><p><c:out value='${className}'/></p></div>
		        <div class="data-value big-font2"><p><c:out value='${schoolYear}'/>年<c:out value='${classNumber}'/>組</p></div>
		    </div>
				
		    <div class="data-row">
				<div class="data-label"><p>学生種別:</p></div>
				<div class="data-value"><p><c:out value='${studentType}'/></p></div>	    
		    </div>


			<input type="hidden" name="lastName" value="<c:out value='${lastName}'/>">
			<input type="hidden" name="firstName" value="<c:out value='${firstName}'/>">
			<input type="hidden" name="lastNameRuby" value="<c:out value='${lastNameRuby}'/>">
			<input type="hidden" name="firstNameRuby" value="<c:out value='${firstNameRuby}'/>">
			<input type="hidden" name="tel" value="<c:out value='${tel}'/>">
			<input type="hidden" name="postCode" value="<c:out value='${postCode}'/>">
			<input type="hidden" name="address" value="<c:out value='${address}'/>">
			<input type="hidden" name="birthYear" value="<c:out value='${birthYear}'/>">
			<input type="hidden" name="birthMonth" value="<c:out value='${birthMonth}'/>">
			<input type="hidden" name="birthDay" value="<c:out value='${birthDay}'/>">
			<input type="hidden" name="admissionYear" value="<c:out value='${admissionYear}'/>">
			<input type="hidden" name="admissionMonth" value="<c:out value='${admissionMonth}'/>">
			<input type="hidden" name="admissionDay" value="<c:out value='${admissionDay}'/>">
			<input type="hidden" name="studentType" value="<c:out value='${studentType}'/>">
			<input type="hidden" name="className" value="<c:out value='${className}'/>">
			<input type="hidden" name="studentNumber" value="<c:out value='${studentNumber}'/>">
			<input type="hidden" name="schoolYear" value="<c:out value='${schoolYear}'/>">
			<input type="hidden" name="classNumber" value="<c:out value='${classNumber}'/>">
		    <!-- トークンの格納  -->
		    <input type="hidden" name="csrfToken" value="${csrfToken}">
            <!-- 登録ボタン -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">登録</button>
	     </form>            
	     <!-- 初期登録JSPへ戻るボタン、登録内容をリクエストに格納している  -->
	     <form action="FirstSettingCheck.action" method="post">
			<input type="hidden" name="lastName" value="<c:out value='${lastName}'/>">
			<input type="hidden" name="firstName" value="<c:out value='${firstName}'/>">
			<input type="hidden" name="lastNameRuby" value="<c:out value='${lastNameRuby}'/>">
			<input type="hidden" name="firstNameRuby" value="<c:out value='${firstNameRuby}'/>">
			<input type="hidden" name="tel" value="<c:out value='${tel}'/>">
			<input type="hidden" name="postCode" value="<c:out value='${postCode}'/>">
			<input type="hidden" name="address" value="<c:out value='${address}'/>">
			<input type="hidden" name="birthYear" value="<c:out value='${birthYear}'/>">
			<input type="hidden" name="birthMonth" value="<c:out value='${birthMonth}'/>">
			<input type="hidden" name="birthDay" value="<c:out value='${birthDay}'/>">
			<input type="hidden" name="admissionYear" value="<c:out value='${admissionYear}'/>">
			<input type="hidden" name="admissionMonth" value="<c:out value='${admissionMonth}'/>">
			<input type="hidden" name="admissionDay" value="<c:out value='${admissionDay}'/>">
			<input type="hidden" name="studentType" value="<c:out value='${studentType}'/>">
			<input type="hidden" name="className" value="<c:out value='${className}'/>">
			<input type="hidden" name="studentNumber" value="<c:out value='${studentNumber}'/>">
			<input type="hidden" name="schoolYear" value="<c:out value='${schoolYear}'/>">
			<input type="hidden" name="classNumber" value="<c:out value='${classNumber}'/>">

			<input type="hidden" name="goBack" value="true">
		    <!-- 戻るボタン  -->		
			<input type="submit" class="w-100 btn btn-lg btn-secondary mb-3" value="戻る">
		 </form>
	</div>     
</main>

<c:import url="/footer/footer.jsp" />

