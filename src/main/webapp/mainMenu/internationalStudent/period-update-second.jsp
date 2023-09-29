<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/header/header-mainMenu.jsp" />
<%@include file="/mainMenu/side-bar-menu.jsp" %>
<c:import url="/token/token.jsp" />

<!-- 「在留期間更新許可申請書　２枚目」作成用JSP  -->
<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h1>「在留期間更新許可申請書　２枚目」作成</h1><br>
    </div>			  
		<form action="PeriodUpdateSecond.action" method="post">
   			<p class="text-start">・在留期間更新許可申請書は３枚組で、当該書類は２枚目です。</p>
  			<p class="text-start margin-bottom-50  border-bottom">(The application form for permission to extend the period of stay is in triplicate, and the said document is the second one.)</p>
	        <div class="row">
    			<p class="text-start">日本語能力証明を入力してください。</p>
	  			<p class="text-start">(Please enter proof of Japanese language proficiency.)</p>
    			<p class="text-start">下記３つの選択肢のうち、一つ以上を入力してください。</p>
	  			<p class="text-start">(Please enter one or more of the three options below.)</p>
    			<p class="text-start">①試験による証明</p>
	  			<p class="text-start">(Proof based on a Japanese Language Test)</p> 
     			<p class="text-start">②日本語教育を受けた教育機関及び期間</p>
	  			<p class="text-start">(Organization and period to have received Japanese language education)</p> 
     			<p class="text-start">③その他</p>
	  			<p class="text-start">(Others)</p> 

 	            <!-- 試験による証明 -->
      			<p class="text-start">①試験による証明(Proof based on a Japanese Language Test)</p>
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="testName">試験名（Name of the test）</label>
	                <input class="form-control" type="text" id="testName" name="testName" placeholder="日本語能力検定試験" value="<c:out value='${testName}'/>">
	            </div>   	            
   	            <!-- 級又は点数 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="attainedLevelOrScore">級又は点数（Attained level or score）</label>
	                <input class="form-control" type="text" id="attainedLevelOrScore" name="attainedLevelOrScore" placeholder="１級" value="<c:out value='${attainedLevelOrScore}'/>">
	            </div>
      			<p class="text-start">②日本語教育を受けた教育機関及び期間(Organization and period to have received Japanese language education)</p>
    	        <!-- 機関名 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="organization">機関名（Organization）</label>
	                <input class="form-control" type="text" id="organization" name="organization" placeholder="日本語学校　希望ヶ丘駅前支店" value="<c:out value='${organization}'/>" >
	            </div>
  	 	        <!-- 期間年月日（自） -->
                <label class="form-label" for="startYear">期間（自）(Period from)</label>
	            <div class="col-md-6 mb-3">
	                <select id="startYear" name="startYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.startYear}'/>" >
	                    <option value="">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-10; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-6 mb-3">
	                <select id="startMonth" name="startMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.startMonth}'/>" >
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
  	            <!-- 期間年月日（至） -->
                <label class="form-label" for="endYear">期間（至）(Period to)</label>
	            <div class="col-md-6 mb-3">
	                <select id="endYear" name="endYear" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.endYear}'/>" >
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear-10; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-6 mb-3">
	                <select id="endMonth" name="endMonth" class="form-control select-center auto-select" data-selected-value="<c:out value='${param.endMonth}'/>" >
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
 	            <!-- その他 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="others">③その他(Others)</label>
	                <input class="form-control" type="text" id="others" name="others" placeholder="2010年から2018年まで日本で生活しており、小学校も日本の学校に通っていた。" value="<c:out value='${others}'/>">
	            </div> 
	              
      			<p class="border-bottom"></p>
      			
	  			<p class="text-start">滞在費の支弁方法等（生活費，学費及び家賃等全てについて記入すること。）※複数選択可(Others)</p> 
 	  			<p class="text-start">(Method of support to pay for expenses while in Japan(fill in with regard to living expenses, tuition and rent) * multiple answers possible)</p> 
     			<p class="text-start">(1)支弁方法及び月平均支弁額</p>
	  			<p class="text-start">(Method of support and an amount of support per month (average))</p>    
     			<p class="text-start">下記５つの選択肢のうち、一つ以上を入力してください。</p>
	  			<p class="text-start">(Please enter one or more of the five options below.)</p>
 	            <!-- 本人負担 -->
                <label class="form-label" for="self">①本人負担（Self）</label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="self" name="self" placeholder="100,000" value="<c:out value='${self}'/>">
	            </div>
	            <div class="col-md-8 mb-3"></div>	      
   	            <!-- 在外経費支弁者負担 -->
                <label class="form-label" for="supporterLivingAbroad">②在外経費支弁者負担（Supporter living abroad）</label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="supporterLivingAbroad" name="supporterLivingAbroad" placeholder="50,000" value="<c:out value='${supporterLivingAbroad}'/>">
	            </div>
	            <div class="col-md-8 mb-3"></div>	      
   	            <!-- 在日経費支弁者負担 -->
                <label class="form-label" for="supporterInJapan">③在日経費支弁者負担（Supporter in Japan）</label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="supporterInJapan" name="supporterInJapan" placeholder="80,000" value="<c:out value='${supporterInJapan}'/>">
	            </div>
	            <div class="col-md-8 mb-3"></div>	      
   	            <!-- 奨学金 -->
                <label class="form-label" for="scholarship">④奨学金（Scholarship）</label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="scholarship" name="scholarship" placeholder="30,000" value="<c:out value='${scholarship}'/>">
	            </div>
	            <div class="col-md-8 mb-3"></div>	      
   	            <!-- その他 -->
                <label class="form-label" for="otherDisbursement">⑤その他（Others）</label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="otherDisbursement" name="otherDisbursement" placeholder="20,000" value="<c:out value='${otherDisbursement}'/>">
	            </div>	            
	            <div class="col-md-8 mb-3"></div>	      
      			<p class="border-bottom"></p>	            
     			<p class="text-start">(2)送金・携行等の別</p>
	  			<p class="text-start">(Remittances from abroad or carrying cash)</p>    
     			<p class="text-start">下記３つの選択肢のうち、一つ以上を入力してください。</p>
	  			<p class="text-start">(Please enter one or more of the three options below.)</p>	  
 	            <!-- 外国からの携行 -->
                <label class="form-label" for="carryingAbroad">①外国からの携行（Carrying from abroad）</label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="carryingAbroad" name="carryingAbroad" placeholder="2,350,000/年" value="<c:out value='${carryingAbroad}'/>">
	            </div>	  			
	            <div class="col-md-8 mb-3"></div>	      
	            <!-- 携行者 -->
                <label class="form-label" for="carryingName">携行者（Name of the individual carrying cash）</label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="carryingName" name="carryingName" placeholder="ジョン・スミス" value="<c:out value='${carryingName}'/>">
	            </div>	  	
	            <div class="col-md-8 mb-3"></div>	      
	            <!-- 携行時期 -->	 
                <label class="form-label" for="carryingTime">携行時期（Date and time of carrying cash）</label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="carryingTime" name="carryingTime" placeholder="2020年頃" value="<c:out value='${carryingTime}'/>">
	            </div>	  	
	            <div class="col-md-8 mb-3"></div>	      
 	            <!-- 外国からの送金 -->
                <label class="form-label" for="remittancesAbroad">②外国からの送金（Remittances from abroad）</label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="remittancesAbroad" name="remittancesAbroad" placeholder="200,000/月" value="<c:out value='${remittancesAbroad}'/>">
	            </div>	  			  		
	            <div class="col-md-8 mb-3"></div>	      
 	            <!-- その他の送金 -->
                <label class="form-label" for="otherRemittances">③その他（Others）</label>
	            <div class="col-md-4 mb-3">
	                <input class="form-control" type="text" id="otherRemittances" name="otherRemittances" placeholder="" value="<c:out value='${otherRemittances}'/>">
	            </div>	  			
	            <div class="col-md-8 mb-3"></div>	      
      			<p class="border-bottom"></p>	            
     			<p class="text-start">経費支弁者（複数人いる場合は全てについて記載すること。）※任意様式の別紙可</p>
	  			<p class="text-start">(Supporter(If there is more than one, give information on all of the supporters )*another paper may be attached, which does not have to use a prescribed format.)</p>    
     			<p class="text-start">当該フォームは一人用のため複数人いる場合は別紙に記載してください。</p>
	  			<p class="text-start">(The form is for one person, so if there is more than one person, please list them on a separate sheet of paper.)</p>	 
 	            <!-- 経費支弁者氏名 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="supporterName">経費支弁者氏名(Supporter Name)</label>
	                <input class="form-control" type="text" id="supporterName" name="supporterName" placeholder="有澤　宗一郎" value="<c:out value='${supporterName}'/>">
	            </div>	  
 	            <!--  経費支弁者住所 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="supporterAddress">経費支弁者住所(Supporter Address)</label>
	                <input class="form-control" type="text" id="supporterAddress" name="supporterAddress" placeholder="京都府京都市四条通４－２" value="<c:out value='${supporterAddress}'/>">
	            </div>	  
 	            <!-- 経費支弁者電話番号 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="supporterTel">経費支弁者電話番号(Supporter Tel)</label>
	                <input class="form-control" type="text" id="supporterTel" name="supporterTel" placeholder="03-1234-5678" value="<c:out value='${supporterTel}'/>">
	            </div>	  
  	            <!-- 経費支弁者年収 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="supporterIncome">経費支弁者年収(Supporter Annual income)</label>
	                <input class="form-control" type="text" id="supporterIncome" name="supporterIncome" placeholder="5600000" value="<c:out value='${supporterIncome}'/>">
	            </div>	  
  	            <!-- 経費支弁者勤務先 -->
	            <div class="col-md-6 mb-3">
	                <label class="form-label" for="supporterEmployment">経費支弁者勤務先(Supporter place of employment)</label>
	                <input class="form-control" type="text" id="supporterEmployment" name="supporterEmployment" placeholder="有澤重工業株式会社" value="<c:out value='${supporterEmployment}'/>">
	            </div>	  
  	            <!-- 経費支弁者勤務先電話番号 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="supporterWorkTel">経費支弁者勤務先電話番号(Supporter Work Phone Number)</label>
	                <input class="form-control" type="text" id="supporterWorkTel" name="supporterWorkTel" placeholder="0120-55-9876" value="<c:out value='${supporterWorkTel}'/>">
	            </div>	 
	        </div>    
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
		    <!-- トークンの格納  -->
 		    <input type="hidden" name="csrfToken" value="${csrfToken}">			
			<!-- 作成ボタン -->
			<button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">作成</button>
		</form>

</main>

<c:import url="/footer/footer-main-menu.jsp" />
