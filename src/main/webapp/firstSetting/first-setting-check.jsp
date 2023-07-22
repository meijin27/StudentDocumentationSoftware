<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="first-menu.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <main class="container">
        <h2 class="text-center p-5">入力内容確認</h2>
        <form action="FirstSettingCheck.action" method="post">
		    <div class="content">
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
		    </div>
            <!-- 登録ボタン -->
            <div class="text-center">
			    <a href="first-setting.jsp" class="btn btn-secondary md-5 mr-5">戻る</a>             
				<button class="btn btn-primary" id="submitButton">登録</button>
            </div>
        </form>
    </main>
    
<%@include file="../footer.html" %>
