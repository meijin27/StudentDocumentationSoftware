<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「証明書交付願」作成</h1><br>
    </div>			  
		<form action="CertificateIssuance.action" method="post">
	        <div class="row">
   	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="requestYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="requestYear" class="form-control select-center" required>
	                    <option value="">-- 令和　年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestMonth">申請年月日</label>
	                <span class="text-danger">*</span>
	                <select name="requestMonth" class="form-control select-center" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="requestDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="requestDay" class="form-control select-center" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
  	            <!-- 用途 -->  	        
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="use">用途</label>
	                <span class="text-danger">*</span>
	                <select name="use" class="form-control select-center" required>
	                    <option value="">-- 用途 --</option>
	                    <option value="就職・進学活動">就職・進学活動</option>
	                    <option value="奨学金等申請">奨学金等申請</option>                 
	                    <option value="被扶養者控除申請">被扶養者控除申請</option>
	                    <option value="アルバイト">アルバイト</option>
	                    <option value="その他">その他</option>
	                    <option value="留学ビザ更新申請">留学ビザ更新申請</option>
	                    <option value="就労ビザ申請">就労ビザ申請</option>                 
	                    <option value="特定活動ビザ申請">特定活動ビザ申請</option>
	                    <option value="その他のビザ申請">その他のビザ申請</option>
	                    <option value="国民年金申請">国民年金申請</option>
	                </select>
	            </div> 
  	            <!-- 提出先 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="">提出先</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="propose" placeholder="横浜市役所" value="${propose}" required>
	            </div>
	            <!-- 提出先は入管ですか？ -->
				<div class="col-md-4 mb-5">
	                <label class="form-label" for="immigrationBureau">提出先は入管ですか？</label>
				    <span class="text-danger">*</span>
				    <div class="d-flex flex-column align-items-start"> 
				        <div class="form-check mb-2"> 
				            <input class="form-check-input" type="radio" name="immigrationBureau" id=immigrationBureauYes value="はい" 
				                   <% if ("はい".equals(request.getParameter("immigrationBureau"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="immigrationBureauYes">
				                はい
				            </label>
				        </div>
				        <div class="form-check"> 
				            <input class="form-check-input" type="radio" name="immigrationBureau" id="immigrationBureauNo" value="いいえ" 
									<% if ("いいえ".equals(request.getParameter("immigrationBureau"))) { %> checked <% } %> required>
				            <label class="form-check-label" for="immigrationBureauNo">
				                いいえ
				            </label>
				        </div>
				    </div>
				</div>  	            
	            <!-- 下記は必要になる証明書類の選択フォーム -->
	            <p>必要な書類の枚数を選択してください</p>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="proofOfStudent">在学証明書</label>
	                <select name="proofOfStudent" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
   	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="attendanceRate">出席率証明書</label>
	                <select name="attendanceRate" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="results">成績証明書</label>
	                <select name="results" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="expectedGraduation">卒業見込証明書</label>
	                <select name="expectedGraduation" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="diploma">卒業証明書</label>
	                <select name="diploma" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="certificateCompletion">履修証明書</label>
	                <select name="certificateCompletion" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        	            
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="enrollmentCertificate">在籍期間証明書</label>
	                <select name="enrollmentCertificate" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        	            
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="healthCertificate">健康診断書</label>
	                <select name="healthCertificate" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>		                   
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="closedPeriod">休業期間証明書</label>
	                <select name="closedPeriod" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>		                   
	            <p>英文証明書を発行する場合は氏名を英語で記載してください。</p>
 	            <!-- 英語姓 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="englishLastName">姓（英語）</label>
	                <input class="form-control" type="text" name="englishLastName" placeholder="TANAKA" value="${englishLastName}">
	            </div>
	            <!-- 英語名 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="englishFirstName">名（英語）</label>
	                <input class="form-control" type="text" name="englishFirstName" placeholder="TARO" value="${englishFirstName}">
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="englishProofOfStudent">在学証明書（英語）</label>
	                <select name="englishProofOfStudent" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
 	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="englishResults">成績証明書（英語）</label>
	                <select name="englishResults" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>  
  	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="englishDiploma">卒業証明書（英語）</label>
	                <select name="englishDiploma" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div> 
 	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="reissueBrokenStudentID">学生証再発行（破損）</label>
	                <select name="reissueBrokenStudentID" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>
 	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="reissueLostStudentID">学生証再発行（紛失）</label>
	                <select name="reissueLostStudentID" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>
  	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="reissueTemporaryIdentification">仮身分証明書再発行</label>
	                <select name="reissueTemporaryIdentification" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="internationalRemittanceRequest">海外送金依頼書</label>
	                <select name="internationalRemittanceRequest" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="applicationForm">所属機関等作成用申請書</label>
	                <select name="applicationForm" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="overseasRemittanceCalculator">海外送金計算書</label>
	                <select name="overseasRemittanceCalculator" class="form-control select-center">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>  
	        </div>      	        
	        <c:if test="${not empty nullError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${nullError}
	            </div>
	        </c:if>
   	        <c:if test="${not empty  inputError}">
	            <div class="alert alert-danger" role="alert">
	                ${inputError}
	            </div>
	        </c:if>	        
   	        <c:if test="${not empty  nameError}">
	            <div class="alert alert-danger" role="alert">
	                ${nameError}
	            </div>
	        </c:if>
	        <c:if test="${not empty dayError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${dayError}
	            </div>
	        </c:if>	        
            <c:if test="${not empty  valueLongError}">
            	<div class="alert alert-danger" role="alert">
                	${valueLongError}
            	</div>
        	</c:if>
			<c:if test="${not empty innerError}">
				<div class="alert alert-danger" role="alert">${innerError}
				</div>
			</c:if>
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
