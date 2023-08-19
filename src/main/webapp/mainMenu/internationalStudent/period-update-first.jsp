<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
        <h1>「在留期間更新許可申請書　１枚目」作成</h1><br>
    </div>			  
		<form action="PeriodUpdateFirst.action" method="post">
   			<p class="text-start">・在留期間更新許可申請書は３枚組で、当該書類は１枚目です。</p>
  			<p class="text-start">(The application form for permission to extend the period of stay is in triplicate, and the said document is the first one.)</p>
  			<p class="text-start">・当該書類は印刷後、顔写真の貼り付けをお願いします。</p>
   			<p class="text-start margin-bottom-50">(Please print out the relevant documents and paste your photo on them.)</p>
	        <div class="row">
  	            <!-- 国籍・地域 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="nationalityRegion">国籍・地域（Nationality/Region）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="nationalityRegion" placeholder="モンゴル" value="${nationalityRegion}" required>
	            </div>   	            
   	            <!-- 本国における居住地 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="homeTown">本国における居住地（Home town/city）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="homeTown" placeholder="Olympic Street 1, Ulaanbaatar" value="${homeTown}" required>
	            </div>
  	            <!-- 性別 -->
  				<div class="col-md-6 mb-5">
				    <label class="form-label">性別（Sex）</label>
	                <span class="text-danger">*</span>
					<div class="d-flex align-items-center justify-content-center margin-bottom-20">
					    <div class="form-check form-check-inline mr-lg-5">
					        <input class="form-check-input" type="radio" name="sex" id="sexMale" value="男" 
					               <% if ("男".equals(request.getParameter("sex"))) { %> checked <% } %> required>
					        <label class="form-check-label" for="sexMale">
					            男（Male）
					        </label>
					    </div>
					    <div class="form-check form-check-inline ml-lg-5">
					        <input class="form-check-input" type="radio" name="sex" id="sexFemale" value="女"
					               <% if ("女".equals(request.getParameter("sex"))) { %> checked <% } %>  required>
					        <label class="form-check-label" for="sexFemale">
					            女（Female）
					        </label>
					    </div>
					</div>
				</div>
 	            <!-- 配偶者の有無 -->
  				<div class="col-md-6 mb-5">
				    <label class="form-label">配偶者の有無（Marital status）</label>
	                <span class="text-danger">*</span>
					<div class="d-flex align-items-center justify-content-center margin-bottom-20">
					    <div class="form-check form-check-inline mr-lg-5">
					        <input class="form-check-input" type="radio" name="maritalStatus" id="maritalStatusMarried" value="有" 
					               <% if ("有".equals(request.getParameter("maritalStatusMarried"))) { %> checked <% } %> required>
					        <label class="form-check-label" for="maritalStatus">
					            有（Married）
					        </label>
					    </div>
					    <div class="form-check form-check-inline ml-lg-5">
					        <input class="form-check-input" type="radio" name="maritalStatus" id="maritalStatusSingle" value="無"
					               <% if ("無".equals(request.getParameter("maritalStatus"))) { %> checked <% } %>  required>
					        <label class="form-check-label" for="maritalStatusSingle">
					            無（Single）
					        </label>
					    </div>
					</div>
				</div>
   	            <!-- 旅券番号 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="passportNumber">旅券番号（Passport Number）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="passportNumber" placeholder="XS1234567" value="${passportNumber}" required>
	            </div>
  	            <!-- 旅券有効期限 -->
  	            <p>旅券有効期限（Passport expiration date）</p>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="effectiveYear">年</label>
	                <span class="text-danger">*</span>
	                <select name="effectiveYear" class="form-control select-center" required>
	                    <option value="">-- 年（Year） --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear; i <=currentYear+10;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="effectiveMonth">月</label>
	                <span class="text-danger">*</span>
	                <select name="effectiveMonth" class="form-control select-center" required>
	                    <option value="">-- 月（Month） --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <label class="form-label invisible-text" for="effectiveDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="effectiveDay" class="form-control select-center" required>
	                    <option value="">-- 日（Day） --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
  	            <!-- 現に有する在留資格 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="statusOfResidence">現に有する在留資格（Status of residence）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="statusOfResidence" placeholder="留学" value="${statusOfResidence}" required>
	            </div>
   	            <!-- 在留期間 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="periodOfStay">在留期間（Period of stay）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="periodOfStay" placeholder="2年" value="${periodOfStay}" required>
	            </div>
  	            <!-- 在留期間の満了日 -->
  	            <p>在留期間の満了日（Expiration date of period of stay）</p>
	            <div class="col-md-4 mb-5">
	                <span class="text-danger">*</span>
	                <select name="periodYear" class="form-control select-center" required>
	                    <option value="">-- 年（Year） --</option>
	                    <% for(int i=currentYear; i <=currentYear+5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <span class="text-danger">*</span>
	                <select name="periodMonth" class="form-control select-center" required>
	                    <option value="">-- 月（Month） --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-5">
	                <span class="text-danger">*</span>
	                <select name="periodDay" class="form-control select-center" required>
	                    <option value="">-- 日（Day） --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
  	            <!-- 在留カード番号（Residence card number） -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="residentCard">在留カード番号（Residence card number）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="residentCard" placeholder="AB12345678CD" value="${residentCard}" required>
	            </div>
  	            <!-- 希望する在留期間（Desired length of extension） -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="desiredPeriodOfStay">希望在留期間(Desired length of extension)</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="desiredPeriodOfStay" placeholder="3年" value="${desiredPeriodOfStay}" required>
	            </div>
  	            <!-- 更新の理由（Reason for extension） -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="reason">更新の理由（Reason for extension）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="reason" placeholder="専門学校卒業へ留学中のため" value="${reason}" required>
	            </div>	            
 	            <!-- 犯罪を理由とする処分を受けたことの有無 （日本国外におけるものを含む。）※交通違反等による処分を含む。 -->
  				<div class="col-md-12 mb-3">
				    <label class="form-label">犯罪を理由とする処分を受けたことの有無 （日本国外におけるものを含む。）</label>
	                <span class="text-danger">*</span>
  					<label class="form-label">※交通違反等による処分を含む。</label>
  					<label class="form-label">Criminal record (in Japan / overseas)※Including dispositions due to traffic violations, etc.</label>
					<div class="d-flex align-items-center justify-content-center margin-bottom-20">
					    <div class="form-check form-check-inline mr-lg-5">
					        <input class="form-check-input" type="radio" name="criminalRecord" id="criminalRecordYes" value="有" 
					               <% if ("有".equals(request.getParameter("criminalRecord"))) { %> checked <% } %> required>
					        <label class="form-check-label" for="criminalRecordYes">
					            有（having a criminal record）
					        </label>
					    </div>
					    <div class="form-check form-check-inline ml-lg-5">
					        <input class="form-check-input" type="radio" name="criminalRecord" id="criminalRecordNo" value="無"
					               <% if ("無".equals(request.getParameter("criminalRecord"))) { %> checked <% } %>  required>
					        <label class="form-check-label" for="criminalRecordNo">
					            無（No criminal record）
					        </label>
					    </div>
					</div>
				</div>	            
  	            <!-- 犯罪の具体的な理由（Reason for the crime） -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="reasonForTheCrime">犯罪の具体的な理由（Reason for the crime）</label>
  	                <label class="form-label" for="reasonForTheCrime">前科がない場合は記入不要（Not required if no criminal record.）</label>
	                <input class="form-control" type="text" name="reasonForTheCrime" placeholder="高速道路で速度超過した（３０km）" value="${reasonForTheCrime}">
	            </div>		            
 	            <!-- 在日親族（父・母・配偶者・子・兄弟姉妹・祖父母・叔(伯)父・叔(伯)母など）及び同居者 -->
  				<div class="col-md-12 mb-3">
				    <label class="form-label">在日親族（父・母・配偶者・子・兄弟姉妹・祖父母・叔(伯)父・叔(伯)母など）及び同居者</label>
	                <span class="text-danger">*</span>
  					<label class="form-label">Family in Japan (father, mother, spouse, children, siblings,grandparents, uncle, aunt or others) and cohabitants</label>
					<div class="d-flex align-items-center justify-content-center margin-bottom-20">
					    <div class="form-check form-check-inline mr-lg-5">
					        <input class="form-check-input" type="radio" name="familyInJapan" id="familyInJapanYes" value="有" 
					               <% if ("有".equals(request.getParameter("familyInJapan"))) { %> checked <% } %> required>
					        <label class="form-check-label" for="familyInJapanYes">
					            有（Has relatives or cohabitants in Japan）
					        </label>
					    </div>
					    <div class="form-check form-check-inline ml-lg-5">
					        <input class="form-check-input" type="radio" name="familyInJapan" id="familyInJapanNo" value="無"
					               <% if ("無".equals(request.getParameter("familyInJapan"))) { %> checked <% } %>  required>
					        <label class="form-check-label" for="familyInJapanNo">
					            無（No relatives and cohabitants in Japan）
					        </label>
					    </div>
					</div>
				</div>	            	            
      			<p class="text-start">在日親族及び同居者が「有」の場合は，以下の欄に在日親族及び同居者を記入してください。</p>
	   			<p class="text-start margin-bottom-50">(If "Yes", please enter the relative(s) living in Japan and the person(s) living with you in the space provided below.)</p>
	         </div>
             <% for(int set = 1; set <= 6; set++){ %>
			    <div class="row set additional-set hidden"  data-set="<%= set %>">
	               <h6 class="border-bottom"><%= set %>人目の在日親族及び同居人(<%= set %>st relative living in Japan and persons living together)</h3>
	  	            <!-- 続柄（Relationship） -->
		            <div class="col-md-6 mb-5">
		                <label class="form-label" for="relationship<%= set %>">続柄（Relationship）</label>
		                <span class="text-danger">*</span>
		                <input class="form-control" type="text" name="relationship<%= set %>" placeholder="父" data-required="true">
		            </div>
	  	            <!-- 氏名（Name） -->
		            <div class="col-md-6 mb-5">
		                <label class="form-label" for="relativeName<%= set %>">氏名（Name）</label>
		                <span class="text-danger">*</span>
		                <input class="form-control" type="text" name="relativeName<%= set %>" placeholder="Genghis Khan" data-required="true">
		            </div>
	   	            <!-- 生年月日 -->
		            <div class="col-md-4 mb-5">
		                <label class="form-label invisible-text" for="birthYear<%= set %>">年</label>
		                <span class="text-danger">*</span>
		                <select name=" set %>" class="form-control select-center" data-required="true">
		                    <option value="">-- 年 --</option>
		                    <% for(int i=currentYear-110; i <=currentYear;
		                        i++){ %>
		                        <option value="<%= i %>">
		                            <%= i %>年
		                        </option>
		                    <% } %>
		                </select>
		            </div>
		            <div class="col-md-4 mb-5">
		                <label class="form-label" for="birthMonth<%= set %>">生年月日（Date of birth）</label>
		                <span class="text-danger">*</span>
		                <select name="birthMonth<%= set %>" class="form-control select-center" data-required="true">
		                    <option value="">-- 月 --</option>
		                    <% for(int i=1; i <=12; i++){ %>
		                        <option value="<%= i %>">
		                            <%= i %>月
		                        </option>
		                    <% } %>
		                </select>
		            </div>
		            <div class="col-md-4 mb-5">
		                <label class="form-label invisible-text" for="birthDay<%= set %>">日</label>
		                <span class="text-danger">*</span>
		                <select name="birthDay<%= set %>" class="form-control select-center" data-required="true">
		                    <option value="">-- 日 --</option>
		                    <% for(int i=1; i <=31; i++){ %>
		                        <option value="<%= i %>">
		                            <%= i %>日
		                        </option>
		                    <% } %>
		                </select>
		            </div>		         
      	            <!-- 国籍・地域 -->
		            <div class="col-md-6 mb-5">
		                <label class="form-label" for="relativeNationalityRegion<%= set %>">国籍・地域（Nationality/Region）</label>
		                <span class="text-danger">*</span>
		                <input class="form-control" type="text" name="relativeNationalityRegion<%= set %>" placeholder="モンゴル" data-required="true">
		            </div>       
      	            <!-- 同居の有無 -->
	  				<div class="col-md-6 mb-3">
					    <label class="form-label" for="livingTogether<%= set %>">同居の有無（Residing with）</label>
					    <span class="text-danger">*</span>
						<div class="d-flex align-items-center justify-content-center margin-bottom-20">
						    <div class="form-check form-check-inline mr-lg-5">
					            <input class="form-check-input" type="radio" name="livingTogether<%= set %>" id="livingTogetherYes<%= set %>" value="有" 
					                   <% if ("有".equals(request.getParameter("livingTogether" + set))) { %> checked <% } %>  data-required="true">
					            <label class="form-check-label" for="livingTogetherYes<%= set %>">
					                有（Living together）
					            </label>
					        </div>
					 	    <div class="form-check form-check-inline ml-lg-5">
					            <input class="form-check-input" type="radio" name="livingTogether<%= set %>" id="livingTogetherNo<%= set %>" value="無" 
										<% if ("無".equals(request.getParameter("livingTogether" + set))) { %> checked <% } %> data-required="true">
					            <label class="form-check-label" for="livingTogetherNo<%= set %>">
					                無（Not living together）
					            </label>
					         </div>
					    </div>
					</div>   
	  	            <!-- 勤務先名称・通学先名称 -->
		            <div class="col-md-6 mb-5">
		                <label class="form-label" for="placeOfEmployment<%= set %>">勤務先名称・通学先名称（Place of employment/Place of school）</label>
		                <span class="text-danger">*</span>
		                <input class="form-control" type="text" name="placeOfEmployment<%= set %>" placeholder="横浜市役所" data-required="true">
		            </div>	 
		         	<!-- 在留カード番号・特別永住者証明書番号 -->
		            <div class="col-md-6 mb-5">
		                <label class="form-label" for="cardNumber<%= set %>">在留カード番号・特別永住者証明書番号（Residence card number Special Permanent Resident Certificate number）</label>
		                <span class="text-danger">*</span>
		                <input class="form-control" type="text" name="cardNumber<%= set %>" placeholder="AB12345678CD" data-required="true">
		            </div>	 
		            <!-- 削除ボタン -->
		            <div class="col-md-12 mb-5">
		                <button type="button" class="w-100 btn btn-lg btn-danger removeSetBtn border-bottom"><%= set %>人目の在日親族及び同居人の削除(<%= set %>st Delete)</button>
		            </div>
		        </div>  
        	<% } %>		            
		            
	        <c:if test="${not empty nullError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${nullError}
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
	        <c:if test="${not empty residentCardError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${residentCardError}
	            </div>
	        </c:if>	        
   	        <c:if test="${not empty criminalError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${criminalError}
	            </div>
	        </c:if>	    
            <c:if test="${not empty  exchangeStudentError}">
            	<div class="alert alert-danger" role="alert">
                	${exchangeStudentError}
            	</div>
        	</c:if>        	
			<c:if test="${not empty innerError}">
				<div class="alert alert-danger" role="alert">${innerError}
				</div>
			</c:if>
			<button type="button" id="addSetBtn" class="w-100 btn btn-lg btn-success mb-3">在日親族及び同居人の追加（Addition of relatives living in Japan and persons living together）</button>
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />