<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="createmenu.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container h-100">
    <div class="row justify-content-center align-items-center h-100">
        <div class="col-md-6">
            <form class="center-form" action="StudentType.action" method="post">
				<div class="form-group text-center input-field">
				    <p>ご自身の学生の種類を選択してください。</p>
				    <input class="radio-option" type="radio" name="type" value="一般学生" checked>一般学生
				    <input class="radio-option" type="radio" name="type" value="留学生" checked>留学生       
				    <input class="radio-option" type="radio" name="type" value="職業訓練生" checked>職業訓練生
				    <a href="createpassword.jsp" class="btn btn-secondary md-5 mr-5">戻る</a> <!-- Button changed to link -->
				    <input type="submit" value="次へ" class="btn btn-primary md-5"/> <!-- Margin Right added -->
				</div>      
            </form>
        </div>
    </div>
</div>

<%@include file="../footer.html" %>
