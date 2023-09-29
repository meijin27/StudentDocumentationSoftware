<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-firstSetting.jsp" />
<c:import url="/token/token.jsp" />

<!-- 秘密の質問と答え登録用JSP  -->
<main class="form-firstSetting w-100 m-auto flex-shrink-0">
	<div class="container">
        <form action="SecretSetting.action" method="post">
             <!-- 秘密の質問 -->
             <div class="col-md-12 mb-3">
                 <label class="form-label" for="secretQuestion">秘密の質問を選択してください。</label>
                 <span class="required-label">必須</span>                    
                 <select id="secretQuestion" name="secretQuestion" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.secretQuestion}'/>" required>
                    <option value="" disabled selected style="display:none;">--- 秘密の質問 ---</option>
		            <option value="好きなモビルスーツ">好きなモビルスーツ</option>
		            <option value="一番やりこんだゲーム">一番やりこんだゲーム</option>
		            <option value="ペットの名前">ペットの名前</option>
		            <option value="卒業した小学校の名前">卒業した小学校の名前</option>
		            <option value="親の旧姓">親の旧姓</option>
		            <option value="尊敬する人">尊敬する人</option>
			        <option value="座右の銘">座右の銘</option>
            		<option value="はじめて買った車">はじめて買った車</option>
            		<option value="ご自由にご記載ください">ご自由にご記載ください</option>
     			</select>
             </div>
            <!-- 秘密の質問の答え -->
            <div class="col-md-12 mb-5">
                <label class="form-label" for="secretAnswer">秘密の質問の答えを入力してください</label>
                <span class="required-label">必須</span>
                <input class="form-control" type="text" id="secretAnswer" name="secretAnswer" placeholder="RX-93-ν2 Hi-νガンダム" required>
            </div>
		    <!-- エラー表示  -->
	        <c:if test="${not empty validationError}" >	
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${validationError}
	            </div>
	        </c:if>	       
  	        <c:if test="${not empty secretError}">
              <div class="alert alert-danger text-center input-field" role="alert">
                  ${secretError}
              </div>
            </c:if>            
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">
		    <!-- 次へボタン  -->
            <button class="w-100 btn btn-lg btn-primary mb-3" type="submit">次へ</button>
        </form>
     </div>
</main>


<c:import url="/footer/footer.jsp" />
