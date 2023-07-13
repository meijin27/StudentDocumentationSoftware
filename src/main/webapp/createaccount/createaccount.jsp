<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="createmenu.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    html, body {
        height: 100%;
    }
    .center-form {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
    .form-group {
        margin-bottom: 15px;
    }
    .alert {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    /* New */
    .input-field {
        width: 100%;  
        max-width: 500px;  /* 最大幅も500pxに */
    }
</style>

<div class="container h-100">
    <div class="row justify-content-center align-items-center h-100">
        <div class="col-md-6">
            <form class="center-form" action="CreateAccount.action" method="post">
                <div class="form-group input-field">
                    <label for="account">新規作成するアカウント名</label>
                    <input type="text" id="account" name="account" class="form-control">
                </div>
                <div class="form-group text-center input-field">
                    <input type="submit" value="次へ" class="btn btn-primary md-5"/>
                </div>
                <c:if test="${not empty accountError}">
                    <div class="alert alert-danger text-center input-field" role="alert">
                        ${accountError}
                    </div>
                </c:if>                
            </form>
        </div>
    </div>
</div>

<%@include file="../footer.html" %> 