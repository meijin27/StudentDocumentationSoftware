<%@page contentType="text/html; charset=UTF-8" %>

<footer class="footer mt-3 py-1 bg-light">
	<div class="container">
	      <p class="mb-3 text-muted">&copy; 2023–<%=java.time.Year.now()%>  Akinori Takahashi</p>
  	</div>
</footer>
  </div>
</div>
<script src="<%=request.getContextPath()%>/js/feather.min.js"></script>
<script>
  feather.replace();
</script>
</body>
</html>