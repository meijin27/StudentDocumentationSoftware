<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「在留期間更新許可申請書　３枚目」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>「在留期間更新許可申請書　３枚目」作成</h1><br>
    </div>			  
		<form action="PeriodUpdateThird.action" method="post" autocomplete="off">
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
   			<p class="text-start">・在留期間更新許可申請書は３枚組で、当該書類は３枚目です。</p>
  			<p class="text-start margin-bottom-50 mb-5">(The application form for permission to extend the period of stay is in triplicate, and the said document is the third one.)</p>
   			<p class="border-bottom"></p>
	        <div class="row">
 	            <!-- 申請人との関係 -->
                <label class="form-label" for="relationship">申請人との関係 （2枚目で在外経費支弁者負担又は在日経費支弁者負担を選択した場合に記入）</label>
                <label class="form-label" for="relationship">Relationship with the applicant (Check one of the followings when your answer to the question 22(1) is supporter living abroad or Japan)</label>
	            <div class="col-md-12 mb-3">
	                <select id="relationship" name="relationship" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.relationship}'/>">
	                    <option value="">-- 申請人との関係 --</option>
	                    <option value="夫">夫（Husband）</option>
	                    <option value="妻">妻（Wife）</option>
	                    <option value="父">父（Father）</option>
   	                    <option value="母">母（Mother）</option>
   	                    <option value="祖父">祖父（Grandfather）</option>
   	                    <option value="祖母">祖母（Grandmother）</option>
   	                    <option value="養父">養父（Foster father）</option>
   	                    <option value="養母">養母（Foster mother）</option>
   	                    <option value="兄弟姉妹">兄弟姉妹（Brother / Sister）</option>
   	                    <option value="叔父 （伯父）・叔母（伯母）">叔父 （伯父）・叔母（伯母）（Uncle / Aunt）</option>
   	                    <option value="受入教育機関">受入教育機関（Educational institute）</option>
   	                    <option value="友人・知人">友人・知人（Friend / Acquaintance）</option>
   	                    <option value="友人・知人の親族">友人・知人の親族（Relative of friend / acquaintance）</option>
   	                    <option value="取引関係者・現地企業等職員">取引関係者・現地企業等職員（Business connection / Personnel of local enterprise）</option>
   	                    <option value="取引関係者・現地企業等職員の親族">取引関係者・現地企業等職員の親族（Relative of business connection / personnel of local enterprise）</option>
   	                    <option value="その他">その他（Others）</option>
	                </select>
	            </div>	        
 	            <!-- その他の内容 -->
                <label class="form-label" for="relationshipOtherContents">その他を選択した場合は詳細記入（If you select "Others", please provide the following information）</label>
	            <div class="col-md-12 mb-5">
	                <input class="form-control" type="text" id="relationshipOtherContents" name="relationshipOtherContents" placeholder="" value="<c:out value='${relationshipOtherContents}'/>">
	            </div>   	            

      			<p class="border-bottom"></p>
 	            <!-- 奨学金支給機関 -->
       			<p class="text-start form-label">奨学金支給機関 （2枚目でで奨学金を選択した場合に記入）※複数選択可</p>
				<p class="text-start">Organization which provide scholarship (Check one of the following when the answer to the question 22(1) is scholarship)* multiple answers possible</p>
				<div class="col-md-6 mb-3 d-flex align-items-center">
				    <input class="form-check-input mt-0 mb-0" type="checkbox" id="foreignGovernment" name="foreignGovernment" value="foreignGovernment">
				    <label for="foreignGovernment">外国政府（Foreign government）</label>
				</div>
				<div class="col-md-6 mb-3 d-flex align-items-center">
				    <input class="form-check-input mt-0 mb-0" type="checkbox" id="japaneseGovernment" name="japaneseGovernment" value="japaneseGovernment">
				    <label for="japaneseGovernment">日本国政府（Japanese government）</label>
				</div>
				<div class="col-md-6 mb-3 d-flex align-items-center">
				    <input class="form-check-input mt-0 mb-0" type="checkbox" id="localGovernment" name="localGovernment" value="localGovernment">
				    <label for="localGovernment">地方公共団体（Local government）</label>
				</div>
				<div class="col-md-6 mb-3 d-flex align-items-center">
				    <input class="form-check-input mt-0 mb-0" type="checkbox" id="otherOrganization" name="otherOrganization" value="otherOrganization">
				    <label for="otherOrganization">その他（Others）</label>
				</div>
				<div class="col-md-12 mb-3 d-flex align-items-center">
				    <input class="form-check-input mt-0 mb-0" type="checkbox" id="publicInterest" name="publicInterest" value="publicInterest">
				    <label for="publicInterest">公益社団法人又は公益財団法人（Public interest incorporated association /Public interest incorporated foundation）</label>
				</div>
				
 	            <!-- 公益社団法人又は公益財団法人の内容 -->
                <label class="form-label" for="organizationpublicInterestContents">公益社団法人又は公益財団法人を選択した場合は詳細記入（If you have selected a public interest incorporated association or public interest incorporated foundation, please provide details.）</label>
	            <div class="col-md-12 mb-3">
	                <input class="form-control" type="text" id="organizationpublicInterestContents" name="organizationpublicInterestContents" placeholder="" value="<c:out value='${organizationpublicInterestContents}'/>">
	            </div>   	       

 	            <!-- その他の内容 -->
                <label class="form-label" for="organizationOtherContents">その他を選択した場合は詳細記入（If you select "Others", please provide the following information）</label>
	            <div class="col-md-12 mb-5">
	                <input class="form-control" type="text" id="organizationOtherContents" name="organizationOtherContents" placeholder="" value="<c:out value='${organizationOtherContents}'/>">
	            </div>   	            
      			
       			<p class="border-bottom"></p>
       			     			
  	            <!-- 資格外活動の有無 -->
  				<div class="col-md-12 mb-3">
				    <label class="form-label">資格外活動の有無（Are you engaging in activities other than those permitted under the status of residence previously granted?）<span class="required-label">必須</span></label>
					<div class="d-flex align-items-center justify-content-center margin-bottom-20">
					    <div class="form-check form-check-inline mr-lg-5">
					        <input class="form-check-input" type="radio" name="otherActivity" id="otherActivityYes" value="有" 
					               <% if ("有".equals(request.getParameter("otherActivity"))) { %> checked <% } %> required>
					        <label class="form-check-label" for="otherActivityYes">
					            有（Yes）
					        </label>
					    </div>
					    <div class="form-check form-check-inline ml-lg-5">
					        <input class="form-check-input" type="radio" name="otherActivity" id="otherActivityNo" value="無"
					               <% if ("無".equals(request.getParameter("otherActivity"))) { %> checked <% } %>  required>
					        <label class="form-check-label" for="otherActivityNo">
					            無（No）
					        </label>
					    </div>
					</div>
				</div>            

	  			<p class="text-start form-label">資格外活動が有の場合は，(1)から(4)までの各欄を記入（複数ある場合は全て記入すること）※任意様式の別紙可</p> 
 	  			<p class="text-start">(Fill in (1) to (4) when your answer is "Yes".(Give the information for all of the companies if the applicant works for multiple companies)*another paper may be attached, which does not have to use a prescribed format.)</p> 
 	            <!-- 内容 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="work">(1)内容（Type of work）</label>
	                <input class="form-control" type="text" id="work" name="work" placeholder="レジ係" value="<c:out value='${work}'/>">
	            </div>
   	            <!-- 勤務先名称 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="employment">(2)勤務先名称（Place of employment）</label>
	                <input class="form-control" type="text" id="employment" name="employment" placeholder="〇〇コンビニ" value="<c:out value='${employment}'/>">
	            </div>
   	            <!-- 勤務先電話番号 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="workPhone">勤務先電話番号（Telephone No.）</label>
	                <input class="form-control" type="text" id="workPhone" name="workPhone" placeholder="046123456" value="<c:out value='${workPhone}'/>">
	            </div>
   	            <!-- 週間稼働時間 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="workTimePerWeek">(3)週間稼働時間（Work time per week）</label>
	                <input class="form-control" type="text" id="workTimePerWeek" name="workTimePerWeek" placeholder="15" value="<c:out value='${workTimePerWeek}'/>">
	            </div>
   	            <!-- 報酬 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="salary">(4)報酬（Salary）</label>
	                <input class="form-control" type="text" id="salary" name="salary" placeholder="20000" value="<c:out value='${salary}'/>">
	            </div>	            
  	            <!-- 月額か日額か -->
   	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="monthlyOrDaily">月額・日額（Monthly or Daily）</label>
	                <select id="monthlyOrDaily" name="monthlyOrDaily" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.monthlyOrDaily}'/>">
	                    <option value="">-- 月額・日額 --</option>
	                    <option value="月額">月額（Monthly）</option>
	                    <option value="日額">日額（Daily）</option>
	                </select>
	            </div>	
	            
       			<p class="border-bottom"></p>

 	            <!-- 卒業後の予定 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="afterGraduation">卒業後の予定（Plan after graduation）</label>
	                <span class="required-label">必須</span>
	                <select id="afterGraduation" name="afterGraduation" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.afterGraduation}'/>" required>
	                    <option value="" disabled selected class="display_none">-- 卒業後の予定 --</option>
	                    <option value="帰国">帰国（Return to home country）</option>
	                    <option value="日本での進学">日本での進学（Enter a school of higher education in Japan）</option>
	                    <option value="日本での就職">日本での就職（Find work in Japan）</option>
	                    <option value="その他">その他（Others）</option>
	                </select>
	            </div>	
	            
 	            <!-- その他の内容 -->
                <label class="form-label" for="afterGraduationOtherContents">その他を選択した場合は詳細記入（If you select "Others", please provide the following information）</label>
	            <div class="col-md-12 mb-5">
	                <input class="form-control" type="text" id="afterGraduationOtherContents" name="afterGraduationOtherContents" placeholder="他国へ留学する" value="<c:out value='${afterGraduationOtherContents}'/>">
	            </div>
	        </div>   	    
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">			
			<!-- 作成ボタン -->  
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
