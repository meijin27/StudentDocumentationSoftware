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
            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/main-menu.jsp") ? " active" : "" %>" aria-current="page" href="main-menu.jsp">
              <span data-feather="home" class="align-text-bottom"></span>
              Home
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/createAccount/createAccount.jsp") ? " active" : "" %>" href="<%=request.getContextPath()%>/createAccount/create-account.jsp">
              <span data-feather="file" class="align-text-bottom"></span>
              Orders
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/products.jsp") ? " active" : "" %>" href="#">
              <span data-feather="shopping-cart" class="align-text-bottom"></span>
              Products
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/customers.jsp") ? " active" : "" %>" href="#">
              <span data-feather="users" class="align-text-bottom"></span>
              Customers
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/reports.jsp") ? " active" : "" %>" href="#">
              <span data-feather="bar-chart-2" class="align-text-bottom"></span>
              Reports
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/integrations.jsp") ? " active" : "" %>" href="#">
              <span data-feather="layers" class="align-text-bottom"></span>
              Integrations
            </a>
          </li>
        </ul>
<h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted text-uppercase">
  <span>届け出</span>
  <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse1" aria-expanded="false" aria-controls="collapse1">
    <span class="expand-icon" data-feather="plus-circle"></span>
    <span class="collapse-icon d-none" data-feather="minus-circle"></span>
  </a>
</h6>
<div id="collapse1" class="collapse">
	        <ul class="nav flex-column mb-2">
	          <li class="nav-item">
	            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/currentMonth.jsp") ? " active" : "" %>" href="#">
	              <span data-feather="file-text" class="align-text-bottom"></span>
	              欠席届
	            </a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/lastQuarter.jsp") ? " active" : "" %>" href="#">
	              <span data-feather="file-text" class="align-text-bottom"></span>
	              就職活動
	            </a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/socialEngagement.jsp") ? " active" : "" %>" href="#">
	              <span data-feather="file-text" class="align-text-bottom"></span>
	              退学
	            </a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/yearEndSale.jsp") ? " active" : "" %>" href="#">
	              <span data-feather="file-text" class="align-text-bottom"></span>
	              その他
	            </a>
	          </li>
	        </ul>
	      </div>       
<h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted text-uppercase">
  <span>各種設定</span>
  <a class="link-secondary" href="#" data-bs-toggle="collapse" data-bs-target="#collapse2" aria-expanded="false" aria-controls="collapse2">
    <span class="expand-icon" data-feather="plus-circle"></span>
    <span class="collapse-icon d-none" data-feather="minus-circle"></span>
  </a>
</h6>
<div id="collapse2" class="collapse">
	        <ul class="nav flex-column mb-2">
	          <li class="nav-item">
	            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/currentMonth.jsp") ? " active" : "" %>" href="#">
	              <span data-feather="file-text" class="align-text-bottom"></span>
	              パスワードの変更
	            </a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/lastQuarter.jsp") ? " active" : "" %>" href="#">
	              <span data-feather="file-text" class="align-text-bottom"></span>
	              秘密の質問と答えの変更
	            </a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/socialEngagement.jsp") ? " active" : "" %>" href="#">
	              <span data-feather="file-text" class="align-text-bottom"></span>
	              クラス・学年・組の変更
	            </a>
	          </li>
	          <li class="nav-item">
	            <a class="nav-link<%= currentURL.equals(request.getContextPath() + "/mainMenu/yearEndSale.jsp") ? " active" : "" %>" href="#">
	              <span data-feather="file-text" class="align-text-bottom"></span>
	              学籍番号の変更
	            </a>
	          </li>
	        </ul>
	      </div>
       </div>     
    </nav>

