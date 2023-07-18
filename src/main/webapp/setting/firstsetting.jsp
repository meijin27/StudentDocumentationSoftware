<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <main class="container">
        <h2 class="text-center p-5">初期設定</h2>

        <form action="FirstSetting.action" method="post">
            <div class="row">
                <!-- 氏名 -->
                <div class="col-md-6 mb-3">
                    <label class="form-label" for="">氏名</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="text" name="name" placeholder="YSE 太郎" required>
                </div>
                <!-- 学籍番号 -->
                <div class="col-md-6 mb-3">
                    <label class="form-label" for="">学籍番号</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="studentNumber" name="studentNumber" placeholder="240001" required>
                </div>
                 <c:if test="${not empty studentNumberError}">
                    <div class="alert alert-danger text-center input-field" role="alert">
                        ${studentNumberError}
                    </div>
                </c:if> 
                <!-- 秘密の質問 -->
                <div class="col-md-6 mb-3">
                    <label class="form-label" for="">秘密の質問を選択してください。</label>
                    <span class="text-danger">*</span>                    
                    <select name="secretQuestion" class="form-control">
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
                <!-- 学生の種類 -->
                <div class="col-md-6 mb-3">
                    <label class="form-label" for="">学生の種類</label>
                     <span class="text-danger">*</span> 
                    <select name="studentType" class="form-control">
                        <option value="">--- 学生の種類 ---</option>
			            <option value="一般学生">一般学生</option>
			            <option value="留学生">留学生</option>
			            <option value="職業訓練生">職業訓練生</option>
        			</select>
                </div>                                            
                <!-- 秘密の質問の答え -->
                <div class="col-md-6 mb-3">
                    <label class="form-label" for="">秘密の質問の答えを入力してください</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="text"　name="secretAnswer" placeholder="RX-93-ν2 Hi-νガンダム" required>
                </div>                        			

                <!-- クラス名 -->
                <div class="col-md-6 mb-3">
                    <label class="form-label" for="">クラス名</label>
                    <span class="text-danger">*</span>
                    <input class="form-control" type="text" name="className" placeholder="IG11" required>
                </div>
                <!-- 建物名・部屋番号 -->
                <div class="mb-3">
                    <label class="form-label" for="">建物名・部屋番号</label>
                    <input class="form-control" type="text" name="building" placeholder="YSEマンション 101号室">
                </div>

            </div>

            <div class="alert alert-info">
                ユーザ登録サービス利用規約・個人情報の取り扱いについて
            </div>

            <div class="form-check mb-3">
                <input class="form-check-input" type="checkbox" name="aggree" value="1">
                <label class="form-check-label" for="">同意する</label>
            </div>

            <!-- 送信ボタン -->
            <div class="d-grid">
                <button class="btn btn-primary">登録</button>
            </div>
        </form>
    </main>
    
<%@include file="../footer.html" %>
