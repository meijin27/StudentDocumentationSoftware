<%@page contentType="text/html; charset=UTF-8" %>

<!-- メインメニューのJSPに適用するフッター  -->
<footer class="footer bg-light">
	<div class="container">
	      <p class="text-muted">&copy; 2023–<%=java.time.Year.now()%>  Akinori Takahashi</p>
    </div>
</footer>
    </div>
</div>

<!-- jQuery -->
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<!-- notification-absence-of-training.jsp等で使用している行の追加削除用 -->
<script src="<%=request.getContextPath()%>/js/row.js"></script>
<% if (request.getRequestURI().endsWith("/mainMenu/generalStudent/student-discount-coupon.jsp")) { %>
    <!-- このスクリプトは student-discount-coupon.jsp でのみ実行される -->
    <script>
    	var customOptions = {
            maxSets: 2,
            renameEnabled: false
        };
	</script>
<% } %>
<% if (request.getRequestURI().endsWith("/mainMenu/generalStudent/petition-for-deferred-payment.jsp")) { %>
    <!-- このスクリプトは petition-for-deferred-payment.jsp でのみ実行される -->
    <script>
    	var customOptions = {
            maxSets: 4,
            renameEnabled: true,
            labelSuffix: "個目の納期と金額",
            buttonSuffix: "個目の納期と金額の削除"
        };
	</script>
<% } %>
<% if (request.getRequestURI().endsWith("/mainMenu/internationalStudent/period-update-first.jsp")) { %>
    <!-- このスクリプトは period-update-first.jsp でのみ実行される -->
    <script>
    	var customOptions = {
            maxSets: 6,
            renameEnabled: true,
            labelSuffix: "人目の在日親族及び同居人",
            buttonSuffix: "人目の在日親族及び同居人の削除"
        };
	</script>
<% } %>
<!-- サイドバーのリンクの展開と格納のためのブートストラップ  -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
<!-- サイドバーにアイコン追加するjs(アイコン画像はリンク参照　https://feathericons.com/)  -->
<script src="<%=request.getContextPath()%>/js/feather.min.js"></script>

<!-- サイドバーにアイコン追加  -->
<script>
    feather.replace();
</script>

<!-- セレクトボックスの入力値の自動選択 -->
<script src="<%=request.getContextPath()%>/js/auto_select.js"></script>

<!-- サイドバーのリンクの展開と格納  -->
<script src="<%=request.getContextPath()%>/js/sidebar_expansion.js"></script>

<!-- パスワードのポップアップボックス -->
<script src="<%=request.getContextPath()%>/js/password_helper_popup.js"></script>

<!-- セレクトボックスの初期表示のグレー化用 -->
<script src="<%=request.getContextPath()%>/js/input_gray.js"></script>

<!-- 未入力項目の枠線色付け用 -->
<script src="<%=request.getContextPath()%>/js/input_invalid.js"></script>

</body>
</html>