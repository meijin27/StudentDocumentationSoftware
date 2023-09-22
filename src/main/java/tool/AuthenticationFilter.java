package tool;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//このアノテーションでフィルタリングするURLパターン、jspとサーブレットの実行ファイル
@WebFilter(urlPatterns = { "*.jsp", "*.action" })
public class AuthenticationFilter implements Filter {
	private static final Logger logger = CustomLogger.getLogger(AuthenticationFilter.class);

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false); // 引数にfalseを指定すると、存在しない場合はnullが返ります。

		// キャッシュ制御ヘッダの設定、セキュリティのためブラウザにキャッシュを保存しないように設定
		httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
		httpResponse.setHeader("Pragma", "no-cache");
		httpResponse.setHeader("Expires", "0");

		try {
			String uri = httpRequest.getRequestURI();

			if (session != null && session.getAttribute("id") != null) {
				// ログインしている場合は通常通りリクエストを続行				
				chain.doFilter(request, response);
			} else if (uri.endsWith("login.jsp") || uri.endsWith("Login.action")) {
				// ログインページからのアクセスは許可	
				chain.doFilter(request, response);
			} else if (uri.endsWith("create-account.jsp") || uri.endsWith("CreateAccount.action")) {
				// 新規アカウント作成ページは許可	
				chain.doFilter(request, response);
			} else if ((uri.endsWith("create-password.jsp") || uri.endsWith("CreatePassword.action"))
					&& session != null && session.getAttribute("encryptedAccount") != null) {
				// 新規アカウント作成用のパスワード作成ページは暗号化されたアカウントがセッションに格納されていれば許可
				chain.doFilter(request, response);
			} else if (uri.endsWith("create-success.jsp") && session != null
					&& session.getAttribute("accountName") != null) {
				// 新規アカウント作成用のアカウント作成成功ページはアカウント名がセッションに格納されていれば許可
				chain.doFilter(request, response);
			} else {
				// 上記以外の場合はログインページへリダイレクト
				// コンテキストパスを取得し、そのパスを基に正しいURLにリダイレクト
				String contextPath = httpRequest.getContextPath();
				httpResponse.sendRedirect(contextPath + "/login/login.jsp");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Page change failed", e);
		}
	}

	@Override
	public void destroy() {
	}
}
