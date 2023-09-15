<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>

<!-- 入学年月日・学生種類・学籍番号・クラス・学年・組の設定変更用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
	<div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>入学年月日・学生種類・学籍番号・クラス・学年・組の変更</h1>
    </div>
    <div class="container">
		<form action="ChangeStudentInfo.action" method="post">
	        <div class="row">
  	            <!-- 入学年月日-->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="admissionYear">入学年月日</label>
	                <span class="required-label">必須</span>
	                <select name="admissionYear" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label invisible-text" for="admissionMonth">月</label>
	                <select name="admissionMonth" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label invisible-text" for="admissionDay">日</label>
	                <select name="admissionDay" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	      
	             <!-- 学生の種類 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="">学生の種類</label>
	                <span class="required-label">必須</span>
	                <select name="studentType" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 学生の種類 --</option>
	                    <option value="一般学生">一般学生</option>
	                    <option value="留学生">留学生</option>
	                    <option value="職業訓練生">職業訓練生</option>
	                </select>
	            </div>
  	            <div class="col-md-8 mb-3"></div>	            
	            <!-- 学籍番号 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="">学籍番号</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" name="studentNumber" placeholder="240001" value="<c:out value='${studentNumber}'/>" required>
	            </div>
  	            <div class="col-md-8 mb-3"></div>	            
	            <!-- クラス名 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="">クラス名</label>
	                <span class="required-label">必須</span>
	                <select name="className" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- クラス名 --</option>
	                    <option value="ＩＴ・ゲームソフト科">ＩＴ・ゲームソフト科</option>
	                    <option value="ＡＩ・データサイエンス科">ＡＩ・データサイエンス科</option>
	                    <option value="デジタルビジネスデザイン科">デジタルビジネスデザイン科</option>
   	                    <option value="グローバルＩＴシステム科">グローバルＩＴシステム科</option>
	                    <option value="グローバルＩＴビジネス科">グローバルＩＴビジネス科</option>
	                    <option value="ロボット・ＩＯＴソフト科">ロボット・ＩＯＴソフト科</option>
   	                    <option value="ＩＴライセンス科（通信制）">ＩＴライセンス科（通信制）</option>
	                </select>
	            </div>
	            <!-- 学年 -->
	            <div class="col-md-3 mb-5">
	                <label class="form-label" for="">学年</label>
	                <span class="required-label">必須</span>
	                <select name="schoolYear" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 学年 --</option>
	                    <% for(int i=1; i <=2; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-3 mb-5">
	                <label class="form-label" for="">組</label>
	                <span class="required-label">必須</span>
	                <select name="classNumber" class="form-control select-center" required>
	                    <option value="" disabled selected style="display:none;">-- 組 --</option>
	                    <% for(int i=1; i <=3; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>組
	                        </option>
	                    <% } %>
	                </select>
   	            </div>
                
 	        </div>
 	        <!-- エラー表示  -->
	        <c:if test="${not empty nullError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${nullError}
	            </div>
	        </c:if>
	        <c:if test="${not empty studentNumberError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${studentNumberError}
	            </div>
	        </c:if>
   	        <c:if test="${not empty dayError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${dayError}
	            </div>
	        </c:if>
   	        <c:if test="${not empty innerError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${innerError}
	            </div>
	        </c:if>
	        <!-- サブミットボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">変更</button>
		</form>
	</div>
</main>

<c:import url="/footer/footer-main-menu.jsp" />

