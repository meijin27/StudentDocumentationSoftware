<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/header/header-login.jsp" />


<p>ログイン成功</p>
<p>あなたのマスターキーは${master_key}です。</p>
<p>あなたのivは${iv}です。</p>

<c:import url="/footer/footer.jsp" />
