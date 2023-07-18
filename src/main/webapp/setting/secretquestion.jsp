<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container h-100">
    <div class="row justify-content-center align-items-center h-100">
        <div class="col-md-6">
            <form class="center-form" action="SecretQuestion.action" method="post">
 				<div class="form-group text-center input-field">
                    <label for="secretQuestion">秘密の質問を選択してください。</label>
                    <select name="secretQuestion">
			            <option value="好きなモビルスーツは?">好きなモビルスーツは?</option>
			            <option value="ペットの名前は？">ペットの名前は？</option>
			            <option value="卒業した小学校の名前は？">卒業した小学校の名前は？</option>
			            <option value="母親の旧姓は？">母親の旧姓は？</option>
			            <option value="初恋の人はだれ？">初恋の人はだれ？</option>
   			            <option value="座右の銘はなに？">座右の銘はなに？</option>
			            <option value="はじめて買った車は？">はじめて買った車は？</option>
			            <option value="ご自由にご記載ください">ご自由にご記載ください</option>
        			</select>
                    <label for="secretAnswer">秘密の質問の答えを入力してください</label>
                    <input type="text" id="secretAnswer" name="secretAnswer" class="form-control"> 
 				    <input type="submit" value="確定" class="btn btn-primary md-5"/> <!-- Margin Right added -->
				</div>      
                 <c:if test="${not empty secretAnswerError}">
                    <div class="alert alert-danger text-center input-field" role="alert">
                        ${secretAnswerError}
                    </div>
                </c:if> 
            </form>
        </div>
    </div>
</div>

<%@include file="../footer.html" %>
