<%@page contentType="text/html; charset=UTF-8" %>

<!-- メインメニュー以外のJSPに適用するフッター  -->
<footer class="footer bg-light">
	<div class="container">
	      <p class="text-muted">&copy; 2023–<%=java.time.Year.now()%>  Andows</p>
  	</div>
</footer>

<!-- jQuery -->
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<!-- アイコン追加するjs(アイコン画像はリンク参照　https://feathericons.com/)  -->
<script src="<%=request.getContextPath()%>/js/feather.min.js"></script>
<!-- アイコン追加  -->
<script src="<%=request.getContextPath()%>/js/feather_replace.js"></script>
    
<!-- セレクトボックスの入力値の自動選択 -->
<script src="<%=request.getContextPath()%>/js/auto_select.js"></script>

<!-- パスワードのポップアップボックス -->
<script src="<%=request.getContextPath()%>/js/password_helper_popup.js"></script>

<!-- セレクトボックスの初期表示のグレー化用 -->
<script src="<%=request.getContextPath()%>/js/input_gray.js"></script>

<!-- 未入力項目の枠線色付け用 -->
<script src="<%=request.getContextPath()%>/js/input_invalid.js"></script>

</body>
</html>