<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<div class="container h-100">
    <div class="row justify-content-center align-items-center h-100">
        <div class="col-md-6">
            <form class="center-form" action="CreatePassword.action" method="post">
                <!-- 秘密の質問 -->
                <div class="col-md-12 mb-3">
                    <label class="form-label" for="">秘密の質問を選択してください。</label>
                    <span class="text-danger">*</span>                    
                    <select name="secretQuestion" class="form-control" required>
                        <option value="">--- 秘密の質問 ---</option>
			            <option value="好きなモビルスーツは?">好きなモビルスーツは?</option>
			            <option value="ペットの名前は？">ペットの名前は？</option>
			            <option value="卒業した小学校の名前は？">卒業した小学校の名前は？</option>
			            <option value="母親の旧姓は？">母親の旧姓は？</option>
			            <option value="初恋の人はだれ？">初恋の人はだれ？</option>
   			            <option value="座右の銘はなに？">座右の銘はなに？</option>
			            <option value="はじめて買った車は？">はじめて買った車は？</option>
			            <option value="ご自由にご記載ください">ご自由にご記載ください</option>
        			</select>
                </div>
                <!-- 秘密の質問の答え -->
                <div class="col-md-12 mb-3">
                    <label class="form-label" for="">秘密の質問の答えを入力してください</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="text" name="secretAnswer" placeholder="RX-93-ν2 Hi-νガンダム" value="${secretAnswer}" required>
                </div>      
				<div class="form-group text-center input-field">
				    <input type="submit" value="次へ" class="btn btn-primary md-5"/> <!-- Margin Right added -->
				</div>      
            </form>
        </div>
    </div>
</div>

<%@include file="../footer.html" %>

<!--
    <main class="w-50 m-auto mt-5">
        <h1 class="text-center h-3">Member Login</h1>
        <div>
            <form action="" method="post">
                <div class="form-floating">
                    <input class="form-control border-0 border-bottom rounded-0" type="text" name="email">
                    <label for="">Email</label>
                </div>
                <div class="form-floating">
                    <input class="form-control border-0 border-bottom rounded-0" type="password" name="password">
                    <label for="">Password</label>
                </div>
                <div class="d-grid mt-2">
                    <button class="btn btn-primary">Sign in</button>
                </div>
            </form>
        </div>
    </main>
--!>