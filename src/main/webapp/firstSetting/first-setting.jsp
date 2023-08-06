<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-firstSetting.jsp" />

<main class="form-firstSetting w-100 m-auto flex-shrink-0">
    <h2 class="p-5">初期設定</h2>
    <div class="content">
	    <form action="FirstSetting.action" method="post">
	        <div class="row">
	            <!-- 姓 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">姓</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="lastName" placeholder="田中" value="${lastName}" required>
	            </div>
	            <!-- 名 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">名</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="firstName" placeholder="太郎" value="${firstName}" required>
	            </div>
	            <!-- 生年月日 -->
	            <div class="col-md-2 mb-3">
	                <label class="form-label" for="birthYear">生年月日</label>
	                <span class="text-danger">*</span>
	                <select name="birthYear" class="form-control" required>
	                    <option value="">-- 年 --</option>
	                    <% int currentYear=java.time.Year.now().getValue(); for(int i=currentYear-60; i <= currentYear - 14;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-3">
	                <label class="form-label invisible-text" for="birthManth">月</label>
	                <span class="text-danger">*</span>
	                <select name="birthMonth" class="form-control" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-3">
	                <label class="form-label invisible-text" for="birthDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="birthDay" class="form-control" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <!-- 姓（ふりがな） -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">姓（ふりがな）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="lastNameRuby" placeholder="たなか" value="${lastNameRuby}" required>
	            </div>
	            <!-- 名（ふりがな） -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">名（ふりがな）</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="firstNameRuby" placeholder="たろう" value="${firstNameRuby}" required>
	            </div>
	            <!-- 電話番号 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">電話番号</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="tel" placeholder="08011112222"value="${tel}" required>
	            </div>
	            <!-- 郵便番号 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">郵便番号</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="postCode" placeholder="2310017" value="${postCode}" required>
	            </div>
	            <!-- 住所 -->
	            <div class="col-md-12 mb-3">
	                <label class="form-label" for="">住所</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="address" placeholder="神奈川県横浜市中区港町１丁目１ 横浜スタジアム"value="${address}" required>
	            </div>

  	            <!-- 入校年月日 -->
	            <div class="col-md-2 mb-3">
	                <label class="form-label" for="admissionYear">入校日</label>
	                <span class="text-danger">*</span>
	                <select name="admissionYear" class="form-control" required>
	                    <option value="">-- 年 --</option>
	                    <% for(int i=currentYear-2; i <= currentYear;
	                        i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-3">
	                <label class="form-label invisible-text" for="admissionManth">月</label>
	                <span class="text-danger">*</span>
	                <select name="admissionMonth" class="form-control" required>
	                    <option value="">-- 月 --</option>
	                    <% for(int i=1; i <=12; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-3">
	                <label class="form-label invisible-text" for="admissionDay">日</label>
	                <span class="text-danger">*</span>
	                <select name="admissionDay" class="form-control" required>
	                    <option value="">-- 日 --</option>
	                    <% for(int i=1; i <=31; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	        </div>
	        <div class="row">
	            <!-- 学生の種類 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">学生の種類</label>
	                <span class="text-danger">*</span>
	                <select name="studentType" class="form-control" required>
	                    <option value="">- 学生の種類 -</option>
	                    <option value="一般学生">一般学生</option>
	                    <option value="留学生">留学生</option>
	                    <option value="職業訓練生">職業訓練生</option>
	                </select>
	            </div>
	            <!-- クラス名 -->
	            <div class="col-md-3 mb-3">
	                <label class="form-label" for="">クラス名</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="className" placeholder="IT・ゲームソフト科"value="${className}" required>
	            </div>
	            <!-- 学籍番号 -->
	            <div class="col-md-2 mb-3">
	                <label class="form-label" for="">学籍番号</label>
	                <span class="text-danger">*</span>
	                <input class="form-control" type="text" name="studentNumber" placeholder="240001"value="${studentNumber}" required>
	            </div>
	            <!-- 学年 -->
	            <div class="col-md-2 mb-3">
	                <label class="form-label" for="">学年</label>
	                <span class="text-danger">*</span>
	                <select name="schoolYear" class="form-control" required>
	                    <option value="">- 学年 -</option>
	                    <% for(int i=1; i <=2; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	            <div class="col-md-2 mb-3">
	                <label class="form-label" for="">組</label>
	                <span class="text-danger">*</span>
	                <select name="classNumber" class="form-control" required>
	                    <option value="">-- 組 --</option>
	                    <% for(int i=1; i <=4; i++){ %>
	                        <option value="<%= i %>">
	                            <%= i %>
	                        </option>
	                    <% } %>
	                </select>
	            </div>
	
	        </div>
	        <c:if test="${not empty nullError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${nullError}
	            </div>
	        </c:if>
	        <c:if test="${not empty agreeError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${agreeError}
	            </div>
	        </c:if>
  	        <c:if test="${not empty  rubyError}">
	            <div class="alert alert-danger" role="alert">
	                ${rubyError}
	            </div>
	        </c:if>
	        <c:if test="${not empty  valueLongError}">
	            <div class="alert alert-danger" role="alert">
	                ${valueLongError}
	            </div>
	        </c:if>
            <c:if test="${not empty telError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${telError}
	            </div>
	        </c:if>
	        <c:if test="${not empty postCodeError}">
	            <div class="alert alert-danger text-center input-field" role="alert">
	                ${postCodeError}
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
	        <button type="button" class="w-100 btn btn-warning btn-primary mb-3" data-bs-toggle="modal"
	            data-bs-target="#termsModal" id="termsLink">
	            利用規約を確認する
	        </button>
	
	        <div class="modal fade" id="termsModal" tabindex="-1" aria-labelledby="termsModalLabel" aria-hidden="true">
	            <div class="modal-dialog modal-dialog-scrollable">
	                <div class="modal-content">
	                    <div class="modal-header">
	                        <h5 class="modal-title" id="termsModalLabel">利用規約</h5>
	                        <button type="button" class="btn-close" data-bs-dismiss="modal"
	                            aria-label="Close"></button>
	                    </div>
	                    <div class="modal-body" data-terms-url="<%=request.getContextPath()%>/txt/terms_of_use.txt">
	                    </div>
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <button type="button" class="w-100 btn btn-warning btn-primary mb-3" data-bs-toggle="modal"
	            data-bs-target="#privacyModal" id="privacyLink">
	            プライバシーポリシーを確認する
	        </button>
	
	        <div class="modal fade" id="privacyModal" tabindex="-1" aria-labelledby="privacyModalLabel"
	            aria-hidden="true">
	            <div class="modal-dialog modal-dialog-scrollable">
	                <div class="modal-content">
	                    <div class="modal-header">
	                        <h5 class="modal-title" id="privacyModalLabel">プライバシーポリシー</h5>
	                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	                    </div>
	                    <div class="modal-body"
	                        data-terms-url="<%=request.getContextPath()%>/txt/privacy_policy.txt">
	                    </div>
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">閉じる</button>
	                    </div>
	                </div>
	            </div>
	        </div>
	
	        <div class="mb-3">
	            <input class="form-check-input" id="agreeCheckbox" type="checkbox" name="agree" value="1">
	            <label class="form-check-label" for="">利用規約及びプライバシーポリシーに同意する</label>
	        </div>
	
	        <button class="w-100 btn btn-lg btn-primary mb-3" id="submitButton" type="submit">次へ</button>
	    </form>
    </div>
</main>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.0-beta3/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>//js/modal.js"></script>
<script src="<%=request.getContextPath()%>/js/first_setting.js"></script>

<c:import url="/footer/footer.jsp" />


