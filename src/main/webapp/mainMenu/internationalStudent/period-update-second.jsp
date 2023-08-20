<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-mainMenu.jsp" />

<%@include file="/mainMenu/side-bar-menu.jsp" %>

<main class="form-mainMenu col-md-9 ms-sm-auto col-lg-10  w-100 m-auto flex-shrink-0 px-md-4 mt-5">
    <div class="justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-5 border-bottom">
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
      			<p class="text-start text-center">①試験による証明(Proof based on a Japanese Language Test)</p>
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="testName">試験名（Name of the test）</label>
	                <input class="form-control" type="text" name="testName" placeholder="日本語能力検定試験" value="${testName}">
	            </div>   	            
   	            <!-- 級又は点数 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="attainedLevelOrScore">級又は点数（Attained level or score）</label>
	                <input class="form-control" type="text" name="attainedLevelOrScore" placeholder="１級" value="${attainedLevelOrScore}">
	            </div>
      			<p class="text-start text-center">②日本語教育を受けた教育機関及び期間(Organization and period to have received Japanese language education)</p>
    	        <!-- 機関名 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="organization">機関名（Organization）</label>
	                <input class="form-control" type="text" name="organization" placeholder="日本語学校　希望ヶ丘駅前支店" value="${organization}" >
	            </div>
  	 	        <!-- 期間年月日（自） -->
	            <p class="text-start text-center">期間（自）(Period from)</p>
	            <div class="col-md-6 mb-5">
	                <select name="startYear" class="form-control select-center" >
	                    <option value="">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-10; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-6 mb-5">
	                <select name="startMonth" class="form-control select-center" >
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
  	            <!-- 期間年月日（至） -->
   	            <p class="text-start text-center">期間（至）(Period to)</p>
	            <div class="col-md-6 mb-5">
	                <select name="endYear" class="form-control select-center" >
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear-10; i <=currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>年
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-6 mb-5">
	                <select name="endMonth" class="form-control select-center" >
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>月
	                        </option>
	                    <% } %>
	                </select>
	            </div>
 	            <!-- その他 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="others">③その他(Others)</label>
	                <input class="form-control" type="text" name="others" placeholder="2010年から2018年まで日本で生活しており、小学校も日本の学校に通っていた。" value="${others}">
	            </div> 
	              
      			<p class="border-bottom"></p>
      			
	  			<p class="text-start">滞在費の支弁方法等（生活費，学費及び家賃等全てについて記入すること。）※複数選択可(Others)</p> 
 	  			<p class="text-start">(Method of support to pay for expenses while in Japan(fill in with regard to living expenses, tuition and rent) * multiple answers possible)</p> 
     			<p class="text-start">(1)支弁方法及び月平均支弁額</p>
	  			<p class="text-start">(Method of support and an amount of support per month (average))</p>    
     			<p class="text-start">下記５つの選択肢のうち、一つ以上を入力してください。</p>
	  			<p class="text-start">(Please enter one or more of the five options below.)</p>
 	            <!-- 本人負担 -->
	            <div class="col-md-5 mb-5">
	                <label class="form-label" for="self">①本人負担（Self）</label>
	                <input class="form-control" type="text" name="self" placeholder="100,000" value="${self}">
	            </div>
   	            <!-- 在外経費支弁者負担 -->
	            <div class="col-md-7 mb-5">
	                <label class="form-label" for="supporterLivingAbroad">②在外経費支弁者負担（Supporter living abroad）</label>
	                <input class="form-control" type="text" name="supporterLivingAbroad" placeholder="50,000" value="${supporterLivingAbroad}">
	            </div>
   	            <!-- 在日経費支弁者負担 -->
	            <div class="col-md-7 mb-5">
	                <label class="form-label" for="supporterInJapan">③在日経費支弁者負担（Supporter in Japan）</label>
	                <input class="form-control" type="text" name="supporterInJapan" placeholder="80,000" value="${supporterInJapan}">
	            </div>
   	            <!-- 奨学金 -->
	            <div class="col-md-5 mb-5">
	                <label class="form-label" for="scholarship">④奨学金（Scholarship）</label>
	                <input class="form-control" type="text" name="scholarship" placeholder="30,000" value="${scholarship}">
	            </div>
   	            <!-- その他 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="otherDisbursement ">⑤その他（Others）</label>
	                <input class="form-control" type="text" name="otherDisbursement" placeholder="20,000" value="${otherDisbursement}">
	            </div>	            
      			<p class="border-bottom"></p>	            
     			<p class="text-start">(2)送金・携行等の別</p>
	  			<p class="text-start">(Remittances from abroad or carrying cash)</p>    
     			<p class="text-start">下記３つの選択肢のうち、一つ以上を入力してください。</p>
	  			<p class="text-start">(Please enter one or more of the three options below.)</p>	  
 	            <!-- 外国からの携行 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="carryingAbroad">①外国からの携行（Carrying from abroad）</label>
	                <input class="form-control" type="text" name="carryingAbroad" placeholder="2,350,000/年" value="${carryingAbroad}">
	            </div>	  			
	            <!-- 携行者 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="carryingName">携行者（Name of the individual carrying cash）</label>
	                <input class="form-control" type="text" name="carryingName" placeholder="ジョン・スミス" value="${carryingName}">
	            </div>	  	
	            <!-- 携行時期 -->	  			
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="carryingTime">携行時期（Date and time of carrying cash）</label>
	                <input class="form-control" type="text" name="carryingTime" placeholder="2020年頃" value="${carryingTime}">
	            </div>	  	
 	            <!-- 外国からの送金 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="remittancesAbroad">②外国からの送金（Remittances from abroad）</label>
	                <input class="form-control" type="text" name="remittancesAbroad" placeholder="200,000/月" value="${remittancesAbroad}">
	            </div>	  			  			          
 	            <!-- その他の送金 -->
	            <div class="col-md-12 mb-5">
	                <label class="form-label" for="otherRemittances">③その他（Others）</label>
	                <input class="form-control" type="text" name="otherRemittances" placeholder="" value="${otherRemittances}">
	            </div>	  			
 
      			<p class="border-bottom"></p>	            
     			<p class="text-start">経費支弁者（複数人いる場合は全てについて記載すること。）※任意様式の別紙可</p>
	  			<p class="text-start">(Supporter(If there is more than one, give information on all of the supporters )*another paper may be attached, which does not have to use a prescribed format.)</p>    
     			<p class="text-start">当該フォームは一人用のため複数人いる場合は別紙に記載してください。</p>
	  			<p class="text-start">(The form is for one person, so if there is more than one person, please list them on a separate sheet of paper.)</p>	 
 	            <!-- 経費支弁者氏名 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="supporterName">経費支弁者氏名(Supporter Name)</label>
	                <input class="form-control" type="text" name="supporterName" placeholder="有澤　宗一郎" value="${supporterName}">
	            </div>	  
 	            <!--  経費支弁者住所 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="supporterAddress">経費支弁者住所(Supporter Address)</label>
	                <input class="form-control" type="text" name="supporterAddress" placeholder="京都府京都市四条通４－２" value="${supporterAddress}">
	            </div>	  
 	            <!-- 経費支弁者電話番号 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="supporterTel">経費支弁者電話番号(Supporter Tel)</label>
	                <input class="form-control" type="text" name="supporterTel" placeholder="03-1234-5678" value="${supporterTel}">
	            </div>	  
  	            <!-- 経費支弁者年収 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="supporterIncome">経費支弁者年収(Supporter Annual income)</label>
	                <input class="form-control" type="text" name="supporterIncome" placeholder="5600000" value="${supporterIncome}">
	            </div>	  
  	            <!-- 経費支弁者勤務先 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="supporterEmployment">経費支弁者勤務先(Supporter place of employment)</label>
	                <input class="form-control" type="text" name="supporterEmployment" placeholder="有澤重工業株式会社" value="${supporterEmployment}">
	            </div>	  
  	            <!-- 経費支弁者勤務先電話番号 -->
	            <div class="col-md-6 mb-5">
	                <label class="form-label" for="supporterWorkTel">経費支弁者勤務先電話番号(Supporter Work Phone Number)</label>
	                <input class="form-control" type="text" name="supporterWorkTel" placeholder="0120-55-9876" value="${supporterWorkTel}">
	            </div>	 

	        <c:if test="${not empty inputError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${inputError}
	            </div>
	        </c:if>	        
	        <c:if test="${not empty testNameError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${testNameError}
	            </div>
	        </c:if>
	        <c:if test="${not empty organizationError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${organizationError}
	            </div>
	        </c:if>
	        <c:if test="${not empty othersError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${othersError}
	            </div>
	        </c:if>
	        <c:if test="${not empty payError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${payError}
	            </div>
	        </c:if>
	        <c:if test="${not empty carryingAbroadError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${carryingAbroadError}
	            </div>
	        </c:if>
            <c:if test="${not empty remittancesAbroadError}">
            	<div class="alert alert-danger" role="alert">
                	${remittancesAbroadError}
            	</div>
        	</c:if>
            <c:if test="${not empty otherRemittancesError}">
            	<div class="alert alert-danger" role="alert">
                	${otherRemittancesError}
            	</div>
        	</c:if>
        	<c:if test="${not empty supporterError}">
            	<div class="alert alert-danger" role="alert">
                	${supporterError}
            	</div>
        	</c:if>
            <c:if test="${not empty exchangeStudentError}">
            	<div class="alert alert-danger" role="alert">
                	${exchangeStudentError}
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
