<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header/header-firstSetting.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<main class="form-first w-100 m-auto flex-shrink-0">
     <h2 class="p-5">入力内容確認</h2>
     <form action="FirstSettingCheck.action" method="post">

		        <p>姓: ${sessionScope.lastName}</p>
		        <p>名: ${sessionScope.firstName}</p>
		        <p>学生種別: ${sessionScope.studentType}</p>
		        <p>クラス名: ${sessionScope.className}</p>
		        <p>学年: ${sessionScope.schoolYear}</p>		       
		        <p>組: ${sessionScope.classNumber}</p>		        
		        <p>学生番号: ${sessionScope.studentNumber}</p>
		        <p>生年: ${sessionScope.birthYear}</p>
		        <p>生月: ${sessionScope.birthMonth}</p>
		        <p>生日: ${sessionScope.birthDay}</p>

            <!-- 登録ボタン -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">登録</button>
			<a href="first-setting.jsp" class="w-100 btn btn-lg btn-secondary mb-3">戻る</a>         
     </form>
</main>

<%@include file="../footer.jsp"%>

