<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-firstSetting.jsp" />

<!-- 秘密の質問と答え登録用JSP  -->
<main class="form-firstSetting w-100 m-auto text-center flex-shrink-0">
	<div class="container">
        <form action="SecretSetting.action" method="post">
             <!-- 秘密の質問 -->
             <div class="col-md-12 mb-3">
                 <label class="form-label" for="">秘密の質問を選択してください。</label>
                 <span class="text-danger">*</span>                    
                 <select name="secretQuestion" class="form-control select-center" required>
                    <option value="">--- 秘密の質問 ---</option>
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
                <label class="form-label" for="">秘密の質問の答えを入力してください</label>
                <span class="text-danger">*</span>
                <input class="form-control" type="text" name="secretAnswer" placeholder="RX-93-ν2 Hi-νガンダム" required>
            </div>
		    <!-- エラー表示  -->
  	        <c:if test="${not empty secretError}">
              <div class="alert alert-danger text-center input-field" role="alert">
                  ${secretError}
              </div>
            </c:if>            
            <!-- サブミットボタン  -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
        </form>
     </div>
</main>


<c:import url="/footer/footer.jsp" />
