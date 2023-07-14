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
                    <label for="password">登録するパスワードを入力してください</label>
                    <input type="password" id="password" name="password" class="form-control">
                    <label for="passwordCheck">もう一度同じパスワードを入力してください</label>
                    <input type="password" id="passwordCheck" name="passwordCheck" class="form-control"> <!-- fixed type of input field -->
                    <label for="secretQuestion">秘密の質問を選択してください。</label>
                    <select name="secretQuestion">
			            <option value="好きなモビルスーツは?">好きなモビルスーツは?</option>
			            <option value="ペットの名前は？">ペットの名前は？</option>
			            <option value="卒業した小学校の名前は？">卒業した小学校の名前は？</option>
			            <option value="母親の旧姓は？">母親の旧姓は？</option>
			            <option value="初恋の人はだれ？">初恋の人はだれ？</option>
   			            <option value="座右の銘はなに？">座右の銘はなに？</option>
			            <option value="はじめて買った車は？">はじめて買った車は？</option>
        			</select>
                    <label for="secretAnswer">秘密の質問の答えを入力してください</label>
                    <input type="password" id="secretAnswer" name="secretAnswer" class="form-control"> 
                    <label for="secretAnswerCheck">もう一度同じ秘密の質問の答えを入力してください</label>
                    <input type="password" id="secretAnswerCheck" name="secretAnswerCheck" class="form-control"> 
                </div>
                <c:if test="${not empty passwordError}">
                    <div class="alert alert-danger text-center input-field" role="alert">
                        ${passwordError}
                    </div>
                </c:if>
                 <c:if test="${not empty secretAnswerError}">
                    <div class="alert alert-danger text-center input-field" role="alert">
                        ${secretAnswerError}
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
