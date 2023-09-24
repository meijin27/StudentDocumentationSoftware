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
			// 宛先アドレス
			String uri = httpRequest.getRequestURI();
			// リダイレクト用コンテキストパス
			String contextPath = httpRequest.getContextPath();

			// セッションにID情報が格納されているログインしている状態の場合
			if (session != null && session.getAttribute("id") != null) {
				// 秘密の質問が未登録状態のセッションがある場合は秘密の質問の初期登録ページは許可,それ以外のページにアクセスしようとしても強制遷移
				if ((uri.endsWith("secret-setting.jsp") || uri.endsWith("SecretSetting.action"))
						&& session.getAttribute("secretSetting") != null) {
					chain.doFilter(request, response);
				} else if (session.getAttribute("secretSetting") != null) {
					httpResponse.sendRedirect(contextPath + "/firstSetting/secret-setting.jsp");
					// 初期設定未登録情報のセッションがある場合は初期登録ページは許可,それ以外のページにアクセスしようとしても強制遷移
				} else if ((uri.endsWith("first-setting.jsp") || uri.endsWith("FirstSetting.action"))
						&& session.getAttribute("firstSetting") != null) {
					chain.doFilter(request, response);
					// 初期設定未チェック情報のセッションがある場合は初期登録確認ページは許可,それ以外のページにアクセスしようとしても強制遷移
				} else if ((uri.endsWith("first-setting-check.jsp") || uri.endsWith("FirstSettingCheck.action"))
						&& session.getAttribute("firstSetting") != null
						&& session.getAttribute("firstSettingCheck") != null) {
					chain.doFilter(request, response);
				} else if (session.getAttribute("firstSetting") != null) {
					httpResponse.sendRedirect(contextPath + "/firstSetting/first-setting.jsp");

				} else {
					// ログインしている場合は通常通りリクエストを続行				
					chain.doFilter(request, response);
				}
				// ログインページからのアクセスは許可	
			} else if (uri.endsWith("login.jsp") || uri.endsWith("Login.action")) {
				chain.doFilter(request, response);
				// 新規アカウント作成ページは許可	
			} else if (uri.endsWith("create-account.jsp") || uri.endsWith("CreateAccount.action")) {
				chain.doFilter(request, response);
				// 新規アカウント作成用のパスワード作成ページは暗号化されたアカウントがセッションに格納されていれば許可
			} else if ((uri.endsWith("create-password.jsp") || uri.endsWith("CreatePassword.action"))
					&& session != null && session.getAttribute("encryptedAccount") != null) {
				chain.doFilter(request, response);
				// 新規アカウント作成用のアカウント作成成功ページはアカウント名がセッションに格納されていれば許可
			} else if (uri.endsWith("create-success.jsp") && session != null
					&& session.getAttribute("accountName") != null) {
				chain.doFilter(request, response);
				// 上記以外の場合はログインページへリダイレクト
			} else {
				// セッションを取得し、無効化
				session.invalidate();
				// ログインページへリダイレクト
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
