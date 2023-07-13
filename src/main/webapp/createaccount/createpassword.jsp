<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="createmenu.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container h-100">
    <div class="row justify-content-center align-items-center h-100">
        <div class="col-md-6">
            <form class="center-form" action="CreatePassword.action" method="post">
                <p>パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください</p> <!-- moved here -->
                <div class="form-group input-field">
                    <label for="password1">登録するパスワードを入力してください</label>
                    <input type="password" id="password1" name="password1" class="form-control">
                </div>
                <div class="form-group input-field">
                    <label for="password2">もう一度同じパスワードを入力してください</label>
                    <input type="password" id="password2" name="password2" class="form-control"> <!-- fixed type of input field -->
                </div>
                <c:if test="${not empty passwordError}">
                    <div class="alert alert-danger text-center input-field" role="alert">
                        ${passwordError}
                    </div>
                </c:if> 
				<div class="form-group text-center input-field">
				    <a href="createaccount.jsp" class="btn btn-secondary md-5 mr-5">戻る</a> <!-- Button changed to link -->
				    <input type="submit" value="次へ" class="btn btn-primary md-5"/> <!-- Margin Right added -->
				</div>      
            </form>
        </div>
    </div>
</div>

<%@include file="../footer.html" %>
