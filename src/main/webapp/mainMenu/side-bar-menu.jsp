<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 現在のURLを取得 -->
<%
  String currentURL = request.getRequestURI();
%>

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
                                <span data-feather="file" class="align-text-bottom"></span>
                                monitor
                        </a>
                    </li>
                </ul>                
                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted text-uppercase">
                            <span>諸届・願出（証明書等）</span>
                            <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse1"
                                aria-expanded="false" aria-controls="collapse1">
                                <span class="expand-icon" data-feather="plus-circle"></span>
                                <span class="collapse-icon d-none" data-feather="minus-circle"></span>
                            </a>
                </h6>
                <div id="collapse1" class="collapse">
                    <ul class="nav flex-column mb-2">
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/currentMonth.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                証明書交付願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/lastQuarter.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                変更届
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/socialEngagement.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                通学証明書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/yearEndSale.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                学割証
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/currentMonth.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                推薦書発行願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/lastQuarter.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                推薦状発行願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/socialEngagement.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                アルバイト届
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/yearEndSale.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                資格外活動届
                            </a>
                        </li>
                       <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/yearEndSale.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                自転車通学許可願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/currentMonth.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                受験、受講願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/lastQuarter.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                追試験受験願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/socialEngagement.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                再試験受験願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/yearEndSale.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                補習受講願
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/yearEndSale.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="file-text" class="align-text-bottom"></span>
                                学費延納願
                            </a>
                        </li>
                    </ul>
                </div>
                <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted text-uppercase">
                    <span>職業訓練用書類</span>
                    <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse4"
                        aria-expanded="false" aria-controls="collapse4">
                        <span class="expand-icon" data-feather="plus-circle"></span>
                        <span class="collapse-icon d-none" data-feather="minus-circle"></span>
                    </a>
                </h6>
                <div id="collapse4" class="collapse">
                    <ul class="nav flex-column mb-2">
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/vocationalTraineeDocument/certificate-vocational-training.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/mainMenu/vocationalTraineeDocument/certificate-vocational-training.jsp">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                公共職業訓練等受講証明書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/currentMonth.jsp") ? " active" : "" %>" href="#">
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
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/yearEndSale.jsp") ? " active" : "" %>" href="#">
                                <span data-feather="edit" class="align-text-bottom"></span>
                                就労証明書
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/currentMonth.jsp") ? " active" : "" %>" href="#">
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
                    <span>各種設定</span>
                    <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse5" aria-expanded="false" aria-controls="collapse5">
                        <span class="expand-icon" data-feather="plus-circle"></span>
                        <span class="collapse-icon d-none" data-feather="minus-circle"></span>
                    </a>
                </h6>
                <div id="collapse5" class="collapse">
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
                                学生種類・学籍番号・クラス・学年・組・入学年月日の変更
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
                    <span>規約類</span>
                    <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse6" aria-expanded="false" aria-controls="collapse6">
                        <span class="expand-icon" data-feather="plus-circle"></span>
                        <span class="collapse-icon d-none" data-feather="minus-circle"></span>
                    </a>
                </h6>
                <div id="collapse6" class="collapse">
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
