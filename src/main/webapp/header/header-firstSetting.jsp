<%@page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head class="h-100">
    <meta charset="UTF-8">
    <title>Student Documentation Software</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/firstSetting.css">
	<link rel="icon" href="<%=request.getContextPath()%>/img/favicon.ico">
</head>
<body class="text-center d-flex flex-column h-100">
	
<header>
  <!-- Fixed navbar -->
  <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="container-fluid">
	  <span class="navbar-brand">提出書類作成ソフト</span>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav me-auto mb-2 mb-md-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="../mainMane/mein-menu.jsp">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="../Logout">LogOut</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</header>

