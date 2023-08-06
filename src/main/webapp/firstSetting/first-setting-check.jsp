<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-firstSetting.jsp" />

<main class="form-firstSetting w-100 m-auto flex-shrink-0">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	    <h2 class="p-5">入力内容確認</h2><br>
	    <form action="FirstSettingCheck.action" method="post">
	
			<div class="data-row">
		        <div class="data-label"><p>姓: </p></div>
		        <div class="data-value"><p>${lastName}</p></div>
		    </div>
		    <input type="hidden" name="lastName" value="${lastName}">

		    <div class="data-row">
		        <div class="data-label"><p>名:</p></div>
		        <div class="data-value"><p>${firstName}</p></div>
		    </div>
		    <input type="hidden" name="firstName" value="${firstName}">
		    
			<div class="data-row">
		        <div class="data-label"><p>姓（ふりがな）: </p></div>
		        <div class="data-value"><p>${lastNameRuby}</p></div>
		    </div>
		    <input type="hidden" name="lastNameRuby" value="${lastNameRuby}">

		    <div class="data-row">
		        <div class="data-label"><p>名（ふりがな）:</p></div>
		        <div class="data-value"><p>${firstNameRuby}</p></div>
		    </div>
		    <input type="hidden" name="firstNameRuby" value="${firstNameRuby}">
		
			<div class="data-row">
		        <div class="data-label"><p>生年:</p></div>
		        <div class="data-value"><p>${birthYear}</p></div>
		    </div>
		    <input type="hidden" name="birthYear" value="${birthYear}">
		    
		    <div class="data-row">
		        <div class="data-label"><p>生月:</p></div>
		        <div class="data-value"><p>${birthMonth}</p></div>
		    </div>
		    <input type="hidden" name="birthMonth" value="${birthMonth}">
		
		    <div class="data-row">
		        <div class="data-label"><p>生日:</p></div>
		        <div class="data-value"><p>${birthDay}</p></div>
		    </div>
		    <input type="hidden" name="birthDay" value="${birthDay}">
		    		
		    <div class="data-row">
				<div class="data-label"><p>電話番号:</p></div>
				<div class="data-value"><p>${tel}</p></div>
		    </div>
		    <input type="hidden" name="tel" value="${tel}">
		
		    <div class="data-row">
				<div class="data-label"><p>郵便番号:</p></div>
				<div class="data-value"><p>${postCode}</p></div>
		    </div>
		    <input type="hidden" name="postCode" value="${postCode}">
		
		    <div class="data-row">
		        <div class="data-label"><p>住所:</p></div>
		        <div class="data-value"><p>${address}</p></div>
		    </div>
		    <input type="hidden" name="address" value="${address}">

			<div class="data-row">
		        <div class="data-label"><p>入学年:</p></div>
		        <div class="data-value"><p>${admissionYear}</p></div>
		    </div>
		    <input type="hidden" name="admissionYear" value="${admissionYear}">
		    
		    <div class="data-row">
		        <div class="data-label"><p>入学月:</p></div>
		        <div class="data-value"><p>${admissionMonth}</p></div>
		    </div>
		    <input type="hidden" name="admissionMonth" value="${admissionMonth}">
		
		    <div class="data-row">
		        <div class="data-label"><p>入学日:</p></div>
		        <div class="data-value"><p>${admissionDay}</p></div>
		    </div>
		    <input type="hidden" name="admissionDay" value="${admissionDay}">
		
		    <div class="data-row">
				<div class="data-label"><p>学生種別:</p></div>
				<div class="data-value"><p>${studentType}</p></div>
		    </div>
		    <input type="hidden" name="studentType" value="${studentType}">

		    <div class="data-row">
				<div class="data-label"><p>クラス名:</p></div>
				<div class="data-value"><p>${className}</p></div>
		    </div>
		    <input type="hidden" name="className" value="${className}">
		
		    <div class="data-row">
		        <div class="data-label"><p>学生番号:</p></div>
		        <div class="data-value"><p>${studentNumber}</p></div>
		    </div>
		    <input type="hidden" name="studentNumber" value="${studentNumber}">
		
			<div class="data-row">
		        <div class="data-label"><p>学年:</p></div>
		        <div class="data-value"><p>${schoolYear}</p></div>
		    </div>
		    <input type="hidden" name="schoolYear" value="${schoolYear}">
		
		    <div class="data-row">
		        <div class="data-label"><p>組:</p></div>
		        <div class="data-value"><p>${classNumber}</p></div>
		    </div>        
		    <input type="hidden" name="classNumber" value="${classNumber}">

            <!-- 登録ボタン -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">登録</button>
	     </form>            
	    <form action="FirstSettingCheck.action" method="post">
		    <input type="hidden" name="lastName" value="${lastName}">
		    <input type="hidden" name="firstName" value="${firstName}">
   		    <input type="hidden" name="lastNameRuby" value="${lastNameRuby}">
		    <input type="hidden" name="firstNameRuby" value="${firstNameRuby}">
		    <input type="hidden" name="tel" value="${tel}">
		    <input type="hidden" name="postCode" value="${postCode}">
		    <input type="hidden" name="address" value="${address}">
		    <input type="hidden" name="birthYear" value="${birthYear}">
		    <input type="hidden" name="birthMonth" value="${birthMonth}">
		    <input type="hidden" name="birthDay" value="${birthDay}">
   		    <input type="hidden" name="birthYear" value="${admissionYear}">
		    <input type="hidden" name="birthMonth" value="${admissionMonth}">
		    <input type="hidden" name="birthDay" value="${admissionDay}">
		    <input type="hidden" name="studentType" value="${studentType}">
		    <input type="hidden" name="className" value="${className}">
		    <input type="hidden" name="studentNumber" value="${studentNumber}">
		    <input type="hidden" name="schoolYear" value="${schoolYear}">
		    <input type="hidden" name="classNumber" value="${classNumber}">
		    <input type="hidden" name="goBack" value="true">
		    <input type="submit" class="w-100 btn btn-lg btn-secondary mb-3" value="戻る">
		</form>
	</div>     
</main>

<c:import url="/footer/footer.jsp" />

