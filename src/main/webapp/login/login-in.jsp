<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container h-100">
    <div class="row justify-content-center align-items-center h-100">
        <div class="col-md-6 center-form">
            <form action="Login.action" method="post">
                <div class="form-group input-field">
                    <label for="login">ログイン名</label>
                    <input type="text" id="login" name="login" class="form-control">
                </div>
                <div class="form-group input-field">
                    <label for="password">パスワード</label>
                    <input type="password" id="password" name="password" class="form-control">
                </div>
                <div class="form-group text-center input-field">
                    <input type="submit" value="ログイン" class="btn btn-primary md-3"/>
                </div>
                <c:if test="${not empty loginError}">
                    <div class="alert alert-danger text-center input-field" role="alert">
                        ${loginError }
                    </div>
                </c:if>                
                <div class="text-center input-field">
                    <a href="../createaccount/createaccount.jsp" class="d-block mb-3">新規登録はこちら</a>
                    <a href="CartRemove.action?id=${item.product.id}">パスワードを忘れた方はこちら</a>
                </div>
            </form>
        </div>
    </div>
</div>

<%@include file="../footer.html" %> 
