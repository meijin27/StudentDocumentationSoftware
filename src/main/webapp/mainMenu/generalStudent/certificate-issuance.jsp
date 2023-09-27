<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「証明書交付願」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>「証明書交付願」作成</h1><br>
    </div>			  
		<form action="CertificateIssuance.action" method="post">
	        <div class="row">
   	            <!-- 申請年月日 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="requestYear">申請年月日</label>
	                <span class="required-label">必須</span>
	                <select id="requestYear" name="requestYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.requestYear}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 令和　年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-2019; i <=currentYear-2018;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            令和<%= i %>年（<%= i+2018 %>年）
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="requestMonth">申請年月日</label>
	                <select id="requestMonth" name="requestMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.requestMonth}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="requestDay">日</label>
	                <select id="requestDay" name="requestDay" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.requestDay}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
       			<p class="border-bottom"></p>
  	            <!-- 用途 -->  	        
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="use">用途</label>
	                <span class="required-label">必須</span>
	                <select id="use" name="use" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.use}'/>" required>
	                    <option value="" disabled selected style="display:none;">-- 用途 --</option>
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
	            <div class="col-md-8 mb-3"></div>
  	            <!-- 提出先 -->
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="propose">提出先</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="propose" name="propose" placeholder="横浜市役所" value="<c:out value='${propose}'/>" required>
	            </div>
	            <div class="col-md-8 mb-3"></div>
	            <!-- 提出先は入管ですか？ -->
				<div class="col-md-4 mb-5 text-center">
	                <label class="form-label" for="immigrationBureau">提出先は入管ですか？</label>
				    <span class="required-label">必須</span>
				    <div class="d-flex flex-column align-items-start"> 
					    <div class="form-check">
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
	            <div class="col-md-8 mb-5"></div>
      			<p class="border-bottom"></p>
	            <!-- 下記は必要になる証明書類の選択フォーム -->
	            <p>必要な書類の枚数を選択してください</p>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="proofOfStudent">在学証明書</label>
	                <select id="proofOfStudent" name="proofOfStudent" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.proofOfStudent}'/>">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
   	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="attendanceRate">出席率証明書</label>
	                <select id="attendanceRate" name="attendanceRate" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.attendanceRate}'/>">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="results">成績証明書</label>
	                <select id="results" name="results" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.results}'/>">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="expectedGraduation">卒業見込証明書</label>
	                <select id="expectedGraduation" name="expectedGraduation" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.expectedGraduation}'/>">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>	        
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="diploma">卒業証明書</label>
	                <select id="diploma" name="diploma" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.diploma}'/>">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="certificateCompletion">履修証明書</label>
	                <select id="certificateCompletion" name="certificateCompletion" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.certificateCompletion}'/>">
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
	                <select id="enrollmentCertificate" name="enrollmentCertificate" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.enrollmentCertificate}'/>">
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
	                <select id="healthCertificate" name="healthCertificate" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.healthCertificate}'/>">
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
	                <select id="closedPeriod" name="closedPeriod" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.closedPeriod}'/>">
	                    <option value="">-- 不要 --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>枚
	                        </option>
	                    <% } %>
	                </select>
	            </div>
      			<p class="border-bottom"></p>
	            <p>Select the number of documents required ( English Form Only )</p>
	            <p>＊ Names must be entered for English documents</p>
 	            <!-- 英語姓 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="englishLastName">Last Name</label>
	                <input class="form-control" type="text" id="englishLastName" name="englishLastName" placeholder="TANAKA" value="<c:out value='${englishLastName}'/>">
	            </div>
	            <!-- 英語名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="englishFirstName">First Name</label>
	                <input class="form-control" type="text" id="englishFirstName" name="englishFirstName" placeholder="TARO" value="<c:out value='${englishFirstName}'/>">
	            </div>
	            <!-- 英語在籍証明書 -->
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="englishProofOfStudent">Proof of Enrollment</label>
	                <select id="englishProofOfStudent" name="englishProofOfStudent" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.englishProofOfStudent}'/>">
	                    <option value="">-- unneeded --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %> sheet
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <!-- 英語成績証明書 -->
 	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="englishResults">Academic Transcript</label>
	                <select id="englishResults" name="englishResults" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.englishResults}'/>">
	                    <option value="">-- unneeded --</option>
	                    <% for(int i=1; i <=5;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %> sheet
	                        </option>
	                    <% } %>
	                </select>
	            </div>  
	            <!-- 英語卒業証明書 -->
  	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="englishDiploma">Certificate of Graduation</label>
	                <select id="englishDiploma" name="englishDiploma" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.englishDiploma}'/>">
	                    <option value="">-- unneeded --</option>
	                    <% for(int i=1; i <=5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %> sheet
	                        </option>
	                    <% } %>
	                </select>
	            </div> 
      			<p class="border-bottom"></p>
 	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="reissueBrokenStudentID">学生証再発行（破損）</label>
	                <select id="reissueBrokenStudentID" name="reissueBrokenStudentID" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.reissueBrokenStudentID}'/>">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>
 	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="reissueLostStudentID">学生証再発行（紛失）</label>
	                <select id="reissueLostStudentID" name="reissueLostStudentID" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.reissueLostStudentID}'/>">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>
  	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="reissueTemporaryIdentification">仮身分証明書再発行</label>
	                <select id="reissueTemporaryIdentification" name="reissueTemporaryIdentification" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.reissueTemporaryIdentification}'/>">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="internationalRemittanceRequest">海外送金依頼書</label>
	                <select id="internationalRemittanceRequest" name="internationalRemittanceRequest" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.internationalRemittanceRequest}'/>">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <label class="form-label" for="applicationForm">所属機関等作成用申請書</label>
	                <select id="applicationForm" name="applicationForm" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.applicationForm}'/>">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label" for="overseasRemittanceCalculator">海外送金計算書</label>
	                <select id="overseasRemittanceCalculator" name="overseasRemittanceCalculator" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.overseasRemittanceCalculator}'/>">
	                    <option value="">-- 不要 --</option>
	                    <option value="1">1枚</option>
	                </select>
	            </div>  
	        </div>      
	        <!-- エラー表示 -->	        
	        <c:if test="${not empty nullError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${nullError}
	            </div>
	        </c:if>
   	        <c:if test="${not empty  inputError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${inputError}
	            </div>
	        </c:if>	        
   	        <c:if test="${not empty  nameError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${nameError}
	            </div>
	        </c:if>
	        <c:if test="${not empty dayError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${dayError}
	            </div>
	        </c:if>	        
            <c:if test="${not empty  valueLongError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
                	${valueLongError}
            	</div>
        	</c:if>
			<c:if test="${not empty innerError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
					${innerError}
				</div>
			</c:if>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">			
			<!-- 作成ボタン  -->
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
