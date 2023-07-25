<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container h-100">
    <div class="row justify-content-center align-items-center h-100">
        <div class="col-md-6">
            <form class="center-form" action="ChangePassword.action" method="post">
                <p>パスワードは英大文字・小文字・数字をすべて含み８文字以上にしてください</p> <!-- moved here -->
                <div class="form-group input-field">
                    <label for="oldPassword">現在のパスワードを入力してください</label>
                    <input type="password" id="oldPassword" name="oldPassword" class="form-control">
                    <label for="newPassword">変更後のパスワードを入力してください</label>
                    <input type="password" id="newPassword" name="newPassword" class="form-control">
                    <label for="newPasswordCheck">もう一度同じパスワードを入力してください</label>
                    <input type="password" id="newPasswordCheck" name="newPasswordCheck" class="form-control"> <!-- fixed type of input field -->
                </div>
                <c:if test="${not empty passwordError}">
                    <div class="alert alert-danger text-center input-field" role="alert">
                        ${oldPasswordError}
                    </div>
                </c:if>
                 <c:if test="${not empty secretAnswerError}">
                    <div class="alert alert-danger text-center input-field" role="alert">
                        ${newPasswordError}
                    </div>
                </c:if> 
				<div class="form-group text-center input-field">
				    <input type="submit" value="変更" class="btn btn-primary md-5"/> <!-- Margin Right added -->
				</div>      
            </form>
        </div>
    </div>
</div>

<%@include file="../footer.html" %>
