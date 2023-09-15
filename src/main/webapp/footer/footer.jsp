<%@page contentType="text/html; charset=UTF-8" %>

<!-- メインメニュー以外のJSPに適用するフッター  -->
<footer class="footer bg-light">
	<div class="container">
	      <p class="text-muted">&copy; 2023–<%=java.time.Year.now()%>  Akinori Takahashi</p>
  	</div>
</footer>
<!-- セレクトボックスの初期表示のグレー化用 -->
<script src="<%=request.getContextPath()%>/js/input.js"></script>
</body>
</html>