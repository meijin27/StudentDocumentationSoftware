<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 秘密の質問と答え変更用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>秘密の質問と答えの変更</h1>
    </div>
    <div class="container">
		<form action="ChangeSecret.action" method="post">
			<!-- パスワード -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="password">現在のパスワードを入力してください</label>
                <span class="required-label">必須</span>
				<input type="password" class="form-control" id="password" name="password" required> 
			</div>		
             <!-- 秘密の質問 -->
             <div class="col-md-12 mb-3">
                 <label class="form-label" for="secretQuestion">新しい秘密の質問を選択してください。</label>
                 <span class="required-label">必須</span>                    
                 <select id="secretQuestion" name="secretQuestion" class="form-control select-center" required>
                    <option value="" disabled selected style="display:none;">--- 秘密の質問 ---</option>
		            <option value="好きなモビルスーツは?">好きなモビルスーツは?</option>
		            <option value="一番やりこんだゲームは？">一番やりこんだゲームは？</option>
		            <option value="ペットの名前は？">ペットの名前は？</option>
		            <option value="卒業した小学校の名前は？">卒業した小学校の名前は？</option>
		            <option value="親の旧姓は？">親の旧姓は？</option>
		            <option value="尊敬する人はだれ？">尊敬する人はだれ？</option>
			        <option value="座右の銘はなに？">座右の銘はなに？</option>
            		<option value="はじめて買った車は？">はじめて買った車は？</option>
            		<option value="ご自由にご記載ください">ご自由にご記載ください</option>
     			</select>
             </div>
            <!-- 秘密の質問の答え -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="secretAnswer">新しい秘密の質問の答えを入力してください</label>
                <span class="required-label">必須</span>
                <input class="form-control" type="text" id="secretAnswer" name="secretAnswer" placeholder="RX-93-ν2 Hi-νガンダム" required>
            </div>      
            <!-- エラー表示 -->
  	        <c:if test="${not empty secretError}">
              <div class="alert alert-danger text-center input-field" role="alert">
                  ${secretError}
              </div>
            </c:if>
   		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">         
            <!-- 変更ボタン -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>

        </form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

