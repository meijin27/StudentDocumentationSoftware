<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 現在のURLを取得 -->
<%
  String currentURL = request.getRequestURI();
%>

<!-- サイドバー表示用JSP  -->
<!-- 当該JSPは単独で使用しておらず、他のメインメニューのＪＳＰに組み込んで使用している -->

<div class="container-fluid">
    <div class="row">
        <nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
            <div class="position-sticky pt-3 sidebar-sticky">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/main-menu.jsp") ? " active" : "" %>" aria-current="page" href="<%=request.getContextPath()%>/mainMenu/main-menu.jsp">
                                <span data-feather="home" class="align-text-bottom"></span>
                                Home
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/monitor.jsp") ? " active" : "" %>" aria-current="page" href="<%=request.getContextPath()%>/mainMenu/monitor.jsp">
                                <span data-feather="user-check" class="align-text-bottom"></span>
                                登録情報の確認
                        </a>
                    </li>
                </ul>                
                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted text-uppercase">
                            <span class="custom-pointer">諸届・願出（証明書等）</span>
                            <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse1"
                                aria-expanded="false" aria-controls="collapse1">
                                <span class="expand-icon" data-feather="plus-circle"></span>
                                <span class="collapse-icon d-none" data-feather="minus-circle"></span>
                            </a>
                </h6>
                <div id="collapse1" class="collapse">
                    <ul class="nav flex-column mb-2">
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/generalStudent/certificate-issuance.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/generalStudent/certificate-issuance.jsp">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                証明書交付願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/generalStudent/notification-of-change.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/generalStudent/notification-of-change.jsp">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                変更届
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/generalStudent/student-discount-coupon.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/generalStudent/student-discount-coupon.jsp">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                学割証発行願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/generalStudent/recommended-delivery.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/generalStudent/recommended-delivery.jsp">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                推薦書発行願
                            </a>
                        </li>
                       <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/generalStudent/permission-bike.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/generalStudent/permission-bike.jsp">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                自転車等通学許可願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/generalStudent/taking-re-test.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/generalStudent/taking-re-test.jsp">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                再試験受験申請書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/generalStudent/supplementary-lessons.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/generalStudent/supplementary-lessons.jsp">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                補習受講申請書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/generalStudent/petition-for-deferred-payment.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/generalStudent/petition-for-deferred-payment.jsp">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                学費延納願
                            </a>
                        </li>
                    </ul>
                </div>
                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted text-uppercase">
                    <span class="custom-pointer">留学生用書類</span>
                    <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse2" aria-expanded="false" aria-controls="collapse2">
                        <span class="expand-icon" data-feather="plus-circle"></span>
                        <span class="collapse-icon d-none" data-feather="minus-circle"></span>
                    </a>
                </h6>
                <div id="collapse2" class="collapse">
                    <ul class="nav flex-column mb-2">
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/internationalStudent/period-update-first.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/internationalStudent/period-update-first.jsp">
                                    <span data-feather="pen-tool" class="align-text-bottom"></span>
                                    在留期間更新許可申請書　１枚目
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/internationalStudent/period-update-second.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/internationalStudent/period-update-second.jsp">
                                    <span data-feather="pen-tool" class="align-text-bottom"></span>
                                    在留期間更新許可申請書　２枚目
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/internationalStudent/period-update-third.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/internationalStudent/period-update-third.jsp">
                                    <span data-feather="pen-tool" class="align-text-bottom"></span>
                                    在留期間更新許可申請書　３枚目
                            </a>
                        </li>                        
                    </ul>
                </div>
                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted text-uppercase">
                    <span class="custom-pointer">職業訓練用書類</span>
                    <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse3"
                        aria-expanded="false" aria-controls="collapse3">
                        <span class="expand-icon" data-feather="plus-circle"></span>
                        <span class="collapse-icon d-none" data-feather="minus-circle"></span>
                    </a>
                </h6>
                <div id="collapse3" class="collapse">
                    <ul class="nav flex-column mb-2">
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/certificate-vocational-training.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/certificate-vocational-training.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                公共職業訓練等受講証明書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/notification-absence-of-training.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/notification-absence-of-training.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                委託訓練欠席（遅刻・早退）届
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/absence-due-to-injury-or-illness.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/absence-due-to-injury-or-illness.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                傷病による欠席理由申立書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/reasons-for-non-attendance.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/reasons-for-non-attendance.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                欠席理由申立書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/petition-for-relatives.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/petition-for-relatives.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                親族続柄申立書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/attaching-receipts.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/attaching-receipts.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                別紙　領収書添付用
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/interview-certificate.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/interview-certificate.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                面接証明書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/certificate-of-employment.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/certificate-of-employment.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                就労証明書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/notification-of-change.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/notification-of-change.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                氏名・住所等変更届
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/certificate-issuance.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/certificate-issuance.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                証明書交付願
                            </a>
                        </li>
                    </ul>
                </div>
                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted text-uppercase">
                    <span class="custom-pointer">各種設定</span>
                    <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse4" aria-expanded="false" aria-controls="collapse4">
                        <span class="expand-icon" data-feather="plus-circle"></span>
                        <span class="collapse-icon d-none" data-feather="minus-circle"></span>
                    </a>
                </h6>
                <div id="collapse4" class="collapse">
                    <ul class="nav flex-column mb-2">
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/changeSetting/change-password.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/changeSetting/change-password.jsp">
                                <span data-feather="settings" class="align-text-bottom"></span>
                                パスワードの変更
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/changeSetting/change-secret.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/changeSetting/change-secret.jsp">
                                <span data-feather="settings" class="align-text-bottom"></span>
                                秘密の質問と答えの変更
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/changeSetting/change-name-date-of-birth.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/changeSetting/change-name-date-of-birth.jsp">
                                <span data-feather="settings" class="align-text-bottom"></span>
                                 氏名・生年月日の変更
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/changeSetting/change-address-tel.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/changeSetting/change-address-tel.jsp">
                                <span data-feather="settings" class="align-text-bottom"></span>
                                住所・電話番号の変更
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/changeSetting/change-student-info.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/changeSetting/change-student-info.jsp">
                                <span data-feather="settings" class="align-text-bottom"></span>
                                入学年月日・学生種類・学籍番号・クラス・学年・組の変更
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/changeSetting/change-vocational-trainee.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/changeSetting/change-vocational-trainee.jsp">
                                <span data-feather="settings" class="align-text-bottom"></span>
                                職業訓練生登録情報変更
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/changeSetting/delete-account.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/changeSetting/delete-account.jsp">
                                <span data-feather="trash-2" class="align-text-bottom"></span>
                                アカウントの削除
                            </a>
                        </li>
                    </ul>
                </div>
                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted text-uppercase">
                    <span class="custom-pointer">規約類</span>
                    <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse5" aria-expanded="false" aria-controls="collapse5">
                        <span class="expand-icon" data-feather="plus-circle"></span>
                        <span class="collapse-icon d-none" data-feather="minus-circle"></span>
                    </a>
                </h6>
                <div id="collapse5" class="collapse">
                    <ul class="nav flex-column mb-2">
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/rules/terms-of-use.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/rules/terms-of-use.jsp">
                                    <span data-feather="book-open" class="align-text-bottom"></span>
                                    利用規約
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/rules/privacy-policy.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/rules/privacy-policy.jsp">
                                    <span data-feather="book-open" class="align-text-bottom"></span>
                                    プライバシーポリシー
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
