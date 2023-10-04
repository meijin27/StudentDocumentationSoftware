<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「在留期間更新許可申請書　１枚目」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>「在留期間更新許可申請書　１枚目」作成</h1><br>
    </div>			  
		<form action="PeriodUpdateFirst.action" method="post" autocomplete="off">
   			<p class="text-start">・在留期間更新許可申請書は３枚組で、当該書類は１枚目です。</p>
  			<p class="text-start">(The application form for permission to extend the period of stay is in triplicate, and the said document is the first one.)</p>
  			<p class="text-start">・当該書類は印刷後、顔写真の貼り付けをお願いします。</p>
   			<p class="text-start margin-bottom-50 border-bottom">(Please print out the relevant documents and paste your photo on them.)</p>
	        <div class="row">
  	            <!-- 国籍・地域 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="nationalityRegion">国籍・地域（Nationality/Region）</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="nationalityRegion" name="nationalityRegion" placeholder="モンゴル" value="<c:out value='${nationalityRegion}'/>" required>
	            </div>   
	            <div class="col-md-6 mb-3"></div>	            
   	            <!-- 本国における居住地 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="homeTown">本国における居住地（Home town/city）</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="homeTown" name="homeTown" placeholder="Olympic Street 1, Ulaanbaatar" value="<c:out value='${homeTown}'/>" required>
	            </div>
  	            <!-- 性別 -->
  				<div class="col-md-6 mb-3 text-center">
				    <label class="form-label" for="sexMale">性別（Sex）</label>
	                <span class="required-label">必須</span>
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
	            <div class="col-md-6 mb-3"></div>	      
 	            <!-- 配偶者の有無 -->
  				<div class="col-md-6 mb-3 text-center">
				    <label class="form-label" for="maritalStatusSingle">配偶者の有無（Marital status）</label>
	                <span class="required-label">必須</span>
					<div class="d-flex align-items-center justify-content-center margin-bottom-20">
					    <div class="form-check form-check-inline mr-lg-5">
					        <input class="form-check-input" type="radio" name="maritalStatus" id="maritalStatusMarried" value="有" 
					               <% if ("有".equals(request.getParameter("maritalStatus"))) { %> checked <% } %> required>
					        <label class="form-check-label" for="maritalStatusMarried">
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
	            <div class="col-md-6 mb-3"></div>	      
   	            <!-- 旅券番号 -->
                <label class="form-label" for="passportNumber">旅券番号（Passport Number）<span class="required-label">必須</span></label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="passportNumber" name="passportNumber" placeholder="XS1234567" value="<c:out value='${passportNumber}'/>" required>
	            </div>
	            <div class="col-md-8 mb-3"></div>	      
  	            <!-- 旅券有効期限 -->
                <label class="form-label" for="effectiveYear">旅券有効期限（Passport expiration date）<span class="required-label">必須</span></label>
	            <div class="col-md-4 mb-3">
	                <select id="effectiveYear" name="effectiveYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.effectiveYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 年（Year） --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear; i <=currentYear+10;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <select id="effectiveMonth" name="effectiveMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.effectiveMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月（Month） --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <select id="effectiveDay" name="effectiveDay" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.effectiveDay}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 日（Day） --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
  	            <!-- 現に有する在留資格 -->
                <label class="form-label" for="statusOfResidence">現に有する在留資格（Status of residence）<span class="required-label">必須</span></label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="statusOfResidence" name="statusOfResidence" placeholder="留学" value="<c:out value='${statusOfResidence}'/>" required>
	            </div>
	            <div class="col-md-8 mb-3"></div>	      	            
   	            <!-- 在留期間 -->
                <label class="form-label" for="periodOfStay">在留期間（Period of stay）<span class="required-label">必須</span></label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="periodOfStay" name="periodOfStay" placeholder="2年" value="<c:out value='${periodOfStay}'/>" required>
	            </div>
	            <div class="col-md-8 mb-3"></div>	      	            
  	            <!-- 在留期間の満了日 -->
                <label class="form-label" for="periodYear">在留期間の満了日（Expiration date of period of stay）<span class="required-label">必須</span></label>
	            <div class="col-md-4 mb-3">
	                <select id="periodYear" name="periodYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.periodYear}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 年（Year） --</option>
	                    <% for(int i=currentYear; i <=currentYear+5; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <select id="periodMonth" name="periodMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.periodMonth}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 月（Month） --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-4 mb-3">
	                <select id="periodDay" name="periodDay" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.periodDay}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 日（Day） --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>日
	                        </option>
	                    <% } %>
	                </select>
	            </div>	
  	            <!-- 在留カード番号（Residence card number） -->
                <label class="form-label" for="residentCard">在留カード番号（Residence card number）<span class="required-label">必須</span></label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="residentCard" name="residentCard" placeholder="AB12345678CD" value="<c:out value='${residentCard}'/>" required>
	            </div>
	            <div class="col-md-8 mb-3"></div>	      	            
  	            <!-- 希望する在留期間（Desired length of extension） -->
                <label class="form-label" for="desiredPeriodOfStay">希望在留期間(Desired length of extension)<span class="required-label">必須</span></label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="desiredPeriodOfStay" name="desiredPeriodOfStay" placeholder="3年" value="<c:out value='${desiredPeriodOfStay}'/>" required>
	            </div>
	            <div class="col-md-8 mb-3"></div>	      	            
  	            <!-- 更新の理由（Reason for extension） -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="reason">更新の理由（Reason for extension）</label>
	                <span class="required-label">必須</span>
	                <input class="form-control" type="text" id="reason" name="reason" placeholder="専門学校へ留学中のため" value="<c:out value='${reason}'/>" required>
	            </div>	            
 	            <!-- 犯罪を理由とする処分を受けたことの有無 （日本国外におけるものを含む。）※交通違反等による処分を含む。 -->
  				<div class="col-md-12 mb-3">
				    <label class="form-label" for="criminalRecordNo">犯罪を理由とする処分を受けたことの有無 （日本国外におけるものを含む。）</label>
	                <span class="required-label">必須</span>
  					<label class="form-label" for="criminalRecordNo">※交通違反等による処分を含む。</label>
  					<label class="form-label" for="criminalRecordNo">Criminal record (in Japan / overseas)※Including dispositions due to traffic violations, etc.</label>
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
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="reasonForTheCrime">犯罪の具体的な理由（Reason for the crime）</label>
  	                <label class="form-label" for="reasonForTheCrime">前科がない場合は記入不要（Not required if no criminal record.）</label>
	                <input class="form-control" type="text" id="reasonForTheCrime" name="reasonForTheCrime" placeholder="高速道路で速度超過した（３０km）" value="<c:out value='${reasonForTheCrime}'/>">
	            </div>		            
 	            <!-- 在日親族（父・母・配偶者・子・兄弟姉妹・祖父母・叔(伯)父・叔(伯)母など）及び同居者 -->
  				<div class="col-md-12 mb-3">
				    <label class="form-label"  for="familyInJapanNo">在日親族（父・母・配偶者・子・兄弟姉妹・祖父母・叔(伯)父・叔(伯)母など）及び同居者</label>
	                <span class="required-label">必須</span>
  					<label class="form-label"  for="familyInJapanNo">Family in Japan (father, mother, spouse, children, siblings,grandparents, uncle, aunt or others) and cohabitants</label>
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
	               <h3 class="border-bottom"><%= set %>人目の在日親族及び同居人(<%= set %>st relative living in Japan and persons living together)</h3>
	  	            <!-- 続柄（Relationship） -->
	                <label class="form-label" for="relationship<%= set %>">続柄（Relationship）<span class="required-label">必須</span></label>
		            <div class="col-md-4 mb-3">
		                <input class="form-control" type="text" id="relationship<%= set %>" name="relationship<%= set %>" placeholder="父" data-required="true">
		            </div>
	            	<div class="col-md-8 mb-3"></div>	      	            
	  	            <!-- 氏名（Name） -->
	                <label class="form-label" for="relativeName<%= set %>">氏名（Name）<span class="required-label">必須</span></label>
		            <div class="col-md-6 mb-3">
		                <input class="form-control" type="text" id="relativeName<%= set %>" name="relativeName<%= set %>" placeholder="Genghis Khan" data-required="true">
		            </div>
	            	<div class="col-md-6 mb-3"></div>	      	            
	   	            <!-- 生年月日 -->
	                <label class="form-label" for="relativeBirthYear<%= set %>">生年月日（Date of birth）<span class="required-label">必須</span></label>
		            <div class="col-md-4 mb-3">
		                <select id="relativeBirthYear<%= set %>" name="relativeBirthYear<%= set %>" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.year}'/>" data-required="true">
		                    <option value="" disabled selected class="display_none">-- 年 --</option>
		                    <% for(int i=currentYear-110; i <=currentYear;
		                        i++){ %>
		                        <option value="<%= i %>">
		                            <%= i %>年
		                        </option>
		                    <% } %>
		                </select>
		            </div>
		            <div class="col-md-4 mb-3">
		                <select id="relativeBirthMonth<%= set %>" name="relativeBirthMonth<%= set %>" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.year}'/>" data-required="true">
		                    <option value="" disabled selected class="display_none">-- 月 --</option>
		                    <% for(int i=1; i <=12; i++){ %>
		                        <option value="<%= i %>">
		                            <%= i %>月
		                        </option>
		                    <% } %>
		                </select>
		            </div>
		            <div class="col-md-4 mb-3">
		                <select id="relativeBirthDay<%= set %>" name="relativeBirthDay<%= set %>" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.year}'/>" data-required="true">
		                    <option value="" disabled selected class="display_none">-- 日 --</option>
		                    <% for(int i=1; i <=31; i++){ %>
		                        <option value="<%= i %>">
		                            <%= i %>日
		                        </option>
		                    <% } %>
		                </select>
		            </div>		         
      	            <!-- 国籍・地域 -->
		            <div class="col-md-6 mb-3">
		                <label class="form-label" for="relativeNationalityRegion<%= set %>">国籍・地域（Nationality/Region）</label>
		                <span class="required-label">必須</span>
		                <input class="form-control" type="text" id="relativeNationalityRegion<%= set %>" name="relativeNationalityRegion<%= set %>" placeholder="モンゴル" data-required="true">
		            </div>       
	            	<div class="col-md-6 mb-3"></div>	      	            
      	            <!-- 同居の有無 -->
	  				<div class="col-md-8 mb-3 text-center">
					    <label class="form-label" for="livingTogetherYes<%= set %>">同居の有無（Residing with）</label>
					    <span class="required-label">必須</span>
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
	                <label class="form-label" for="placeOfEmployment<%= set %>">勤務先名称・通学先名称（Place of employment/Place of school）<span class="required-label">必須</span></label>
		            <div class="col-md-6 mb-3">
		                <input class="form-control" type="text" id="placeOfEmployment<%= set %>" name="placeOfEmployment<%= set %>" placeholder="横浜市役所" data-required="true">
		            </div>
	            	<div class="col-md-6 mb-3"></div>	      	            
		         	<!-- 在留カード番号・特別永住者証明書番号 -->
	                <label class="form-label" for="cardNumber<%= set %>">在留カード番号・特別永住者証明書番号（Residence card number Special Permanent Resident Certificate number）<span class="required-label">必須</span></label>
		            <div class="col-md-4 mb-3">
		                <input class="form-control" type="text" id="cardNumber<%= set %>" name="cardNumber<%= set %>" placeholder="AB12345678CD" data-required="true">
		            </div>	 
	            	<div class="col-md-8 mb-3"></div>	      	            
		            <!-- 削除ボタン -->
		            <div class="col-md-12 mb-3">
		                <button type="button" class="w-100 btn btn-lg btn-danger removeSetBtn border-bottom"><%= set %>人目の在日親族及び同居人の削除(<%= set %>st Delete)</button>
		            </div>
		        </div>  
        	<% } %>		            
	        <!-- エラー表示  -->
			<c:forEach var="attr" items="${pageContext.request.attributeNames}">
			    <c:set var="attrName" value="${attr}" />
			    <c:if test="${fn:endsWith(attrName, 'Error')}">
			        <c:set var="errorMsg" value="${requestScope[attrName]}" />
			        <c:if test="${not empty errorMsg}">
			            <div class="alert alert-danger text-center input-field" role="alert">
			                <c:out value="${errorMsg}" />
			            </div>
			        </c:if>
			    </c:if>
			</c:forEach> 
			<!-- 追加ボタン -->
			<button type="button" id="addSetBtn" class="w-100 btn btn-lg btn-success mb-5">在日親族及び同居人の追加（Addition of relatives living in Japan and persons living together）</button>
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">
			<!-- 作成ボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
