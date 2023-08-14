<%@page contentType="text/html; charset=UTF-8" %>

<footer class="footer bg-light">
	<div class="container">
	      <p class="text-muted">&copy; 2023–<%=java.time.Year.now()%>  Akinori Takahashi</p>
    </div>
</footer>
    </div>
</div>

<!-- jQuery -->
<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<!-- notification-absence-of-training.jspで使用している行の追加削除用 -->
<script src="<%=request.getContextPath()%>/js/row.js"></script>
<% if (request.getRequestURI().endsWith("/mainMenu/generalStudent/student-discount-coupon.jsp")) { %>
    <!-- このスクリプトは student-discount-coupon.jsp でのみ実行される -->
    <script>
    	var customOptions = {
            maxSets: 2,
            renameEnabled: false
        };
        $(document).ready(function() {
            initializeFormSets(customOptions);
        });
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

<!-- サイドバーのリンクの展開と格納  -->
<script>
    var sidebarHeadings = document.querySelectorAll('.sidebar-heading');

    sidebarHeadings.forEach(function(heading, index) {
    
        var link = heading.querySelector('a');
        var targetId = link.getAttribute('data-bs-target').substring(1);  // '#' を取り除く
        var targetCollapse = new bootstrap.Collapse(document.getElementById(targetId), {toggle: false});
        
        // Check if the collapse contains an active link. If so, show it by default.
        var hasActiveLink = !!document.getElementById(targetId).querySelector('.nav-link.active');
        if (hasActiveLink) {
            targetCollapse.show();
            link.innerHTML = '<span data-feather="minus-circle"></span>';
            feather.replace();
        }
    
        link.addEventListener('click', function(event) {
            event.preventDefault();
            targetCollapse.toggle();
        });
    
        document.getElementById(targetId).addEventListener('show.bs.collapse', function () {
            link.innerHTML = '<span data-feather="minus-circle"></span>';
            feather.replace();
        });
    
        document.getElementById(targetId).addEventListener('hide.bs.collapse', function () {
            link.innerHTML = '<span data-feather="plus-circle"></span>';
            feather.replace();
        });
    });
</script>

</body>
</html>