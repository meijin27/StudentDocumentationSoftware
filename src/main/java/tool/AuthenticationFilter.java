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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// ページ遷移のフィルタリング
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

			// ログイン・ログアウト・新規アカウント作成（最初のページ）・パスワード再発行（最初のページ）はアクセスを無条件で許可
			if (uri.endsWith("/login.jsp") || uri.endsWith("/Login.action") || uri.endsWith("/Logout")) {
				// ログインページ・ログアウトページへのアクセスは許可	
				chain.doFilter(request, response);
			} else if (uri.endsWith("/create-account.jsp") || uri.endsWith("/CreateAccount.action")) {
				// 新規アカウント作成ページは許可				
				chain.doFilter(request, response);
			} else if (uri.endsWith("/search-account.jsp") || uri.endsWith("/SearchAccount.action")) {
				// パスワードを忘れた場合のアカウント検索ページは許可				
				chain.doFilter(request, response);
				// セッションがない場合
			} else if (session == null) {
				// セッションがない状態で上記以外のページにアクセスしようとした場合、ログアウトページへリダイレクト
				httpResponse.sendRedirect(contextPath + "/Logout");
				// セッションがある場合	
			} else {
				// 新規アカウント作成
				// 新規アカウント作成用のパスワード作成ページは暗号化されたアカウントがセッションに格納されていれば許可
				if ((uri.endsWith("/create-password.jsp") || uri.endsWith("/CreatePassword.action"))
						&& session.getAttribute("encryptedAccount") != null) {
					chain.doFilter(request, response);
				}
				// 暗号化されたアカウントがセッションに格納されていない状態で新規アカウント作成用のパスワード作成ページにアクセスするとログアウトする
				else if ((uri.endsWith("/create-password.jsp") || uri.endsWith("/CreatePassword.action"))
						&& session.getAttribute("encryptedAccount") == null) {
					httpResponse.sendRedirect(contextPath + "/Logout");
				}
				// 新規アカウント作成用のアカウント作成成功ページはアカウント名がセッションに格納されていれば許可
				else if (uri.endsWith("/create-success.jsp") && session.getAttribute("accountName") != null) {
					chain.doFilter(request, response);
				}
				// アカウント名がセッションに格納されていない状態で新規アカウント作成成功ページにアクセスするとログアウトする
				else if (uri.endsWith("/create-success.jsp") && session.getAttribute("accountName") == null) {
					httpResponse.sendRedirect(contextPath + "/Logout");
				}

				// パスワードを忘れた場合の処理
				// パスワード忘却時の秘密の質問の答え解答ページは秘密の質問と暗号化されたIDがセッションに格納されていれば許可
				else if ((uri.endsWith("/secret-check.jsp") || uri.endsWith("/SecretCheck.action"))
						&& (session.getAttribute("secretQuestion") != null
								&& session.getAttribute("encryptedId") != null)) {
					chain.doFilter(request, response);
				}
				// 秘密の質問か暗号化されたIDがセッションに格納されていない状態でパスワード忘却時の秘密の質問の答え解答ページにアクセスするとログアウトする
				else if ((uri.endsWith("/secret-check.jsp") || uri.endsWith("/SecretCheck.action"))
						&& (session.getAttribute("secretQuestion") == null
								|| session.getAttribute("encryptedId") == null)) {
					httpResponse.sendRedirect(contextPath + "/Logout");
				}
				// パスワード忘却時のパスワード再作成ページはマスターキーと暗号化されたIDがセッションに格納されていれば許可
				else if ((uri.endsWith("/recreate-password.jsp") || uri.endsWith("/RecreatePassword.action"))
						&& (session.getAttribute("master_key") != null
								&& session.getAttribute("encryptedId") != null)) {
					chain.doFilter(request, response);
				}
				// マスターキーか暗号化されたIDがセッションに格納されていない状態でパスワード再作成ページにアクセスするとログアウトする
				else if ((uri.endsWith("/recreate-password.jsp") || uri.endsWith("/RecreatePassword.action"))
						&& (session.getAttribute("master_key") == null
								|| session.getAttribute("encryptedId") == null)) {
					httpResponse.sendRedirect(contextPath + "/Logout");
				}
				// パスワード再作成成功ページはパスワード再作成成功メッセージがセッションに格納されていれば許可
				else if (uri.endsWith("/recreate-success.jsp") && session.getAttribute("recreateSuccess") != null) {
					chain.doFilter(request, response);
				}
				// パスワード再作成成功メッセージがセッションに格納されていない状態でパスワード再作成成功ページにアクセスするとログアウトする
				else if (uri.endsWith("/recreate-success.jsp") && session.getAttribute("recreateSuccess") == null) {
					httpResponse.sendRedirect(contextPath + "/Logout");
				}

				// ログイン時の処理
				// セッションにID情報が格納されているログインしている状態の場合
				else if (session.getAttribute("id") != null) {
					// 秘密の質問が未登録状態のセッションがある場合は秘密の質問の初期登録ページは許可,それ以外のページにアクセスしようとしても強制遷移
					if ((uri.endsWith("/secret-setting.jsp") || uri.endsWith("/SecretSetting.action"))
							&& session.getAttribute("secretSetting") != null) {
						chain.doFilter(request, response);
					} else if (session.getAttribute("secretSetting") != null) {
						httpResponse.sendRedirect(contextPath + "/firstSetting/secret-setting.jsp");
						// 秘密の質問が登録状態ではアクセス不許可、メインページに遷移する
					} else if ((uri.endsWith("/secret-setting.jsp") || uri.endsWith("/SecretSetting.action"))
							&& session.getAttribute("secretSetting") == null) {
						httpResponse.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
						// 初期設定未登録情報のセッションがある場合は初期登録ページは許可,それ以外のページにアクセスしようとしても強制遷移
					} else if ((uri.endsWith("/first-setting.jsp") || uri.endsWith("/FirstSetting.action"))
							&& session.getAttribute("firstSetting") != null) {
						chain.doFilter(request, response);
						// 初期設定未チェック情報のセッションがある場合は初期登録確認ページは許可,それ以外のページにアクセスしようとしても強制遷移
					} else if ((uri.endsWith("/first-setting-check.jsp") || uri.endsWith("/FirstSettingCheck.action"))
							&& session.getAttribute("firstSetting") != null
							&& session.getAttribute("firstSettingCheck") != null) {
						chain.doFilter(request, response);
					} else if (session.getAttribute("firstSetting") != null) {
						httpResponse.sendRedirect(contextPath + "/firstSetting/first-setting.jsp");
						//  初期設定が登録状態ではアクセス不許可、メインページに遷移する
					} else if ((uri.endsWith("/first-setting.jsp") || uri.endsWith("/FirstSetting.action")
							|| uri.endsWith("/first-setting-check.jsp") || uri.endsWith("/FirstSettingCheck.action"))
							&& session.getAttribute("firstSetting") == null) {
						httpResponse.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
						// 職業訓練生情報未登録情報のセッションがある場合は職業訓練生情報登録ページは許可,それ以外のページにアクセスしようとしても強制遷移
					} else if ((uri.endsWith("/vocational-trainee-setting.jsp")
							|| uri.endsWith("/VocationalTraineeSetting.action"))
							&& session.getAttribute("vocationalSetting") != null) {
						chain.doFilter(request, response);
						// 職業訓練生情報未チェック情報のセッションがある場合は職業訓練生情報確認ページは許可,それ以外のページにアクセスしようとしても強制遷移
					} else if ((uri.endsWith("/vocational-trainee-setting-check.jsp")
							|| uri.endsWith("/VocationalTraineeSettingCheck.action"))
							&& session.getAttribute("vocationalSetting") != null
							&& session.getAttribute("vocationalSettingCheck") != null) {
						chain.doFilter(request, response);
					} else if (session.getAttribute("vocationalSetting") != null) {
						httpResponse.sendRedirect(contextPath + "/firstSetting/vocational-trainee-setting.jsp");
						//  職業訓練生情報が登録状態ではアクセス不許可、メインページに遷移する
					} else if ((uri.endsWith("/vocational-trainee-setting.jsp")
							|| uri.endsWith("/VocationalTraineeSetting.action")
							|| uri.endsWith("/vocational-trainee-setting-check.jsp")
							|| uri.endsWith("/VocationalTraineeSettingCheck.action"))
							&& session.getAttribute("vocationalSetting") == null) {
						httpResponse.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
						// 行為成功ページは行為成功メッセージがセッションに格納されていれば許可
					} else if (uri.endsWith("/action-success.jsp")
							&& session.getAttribute("action") != null) {
						chain.doFilter(request, response);
					}
					// 行為成功メッセージがセッションに格納されていない状態で行為成功ページにアクセスは不許可、メインページに遷移する
					else if (uri.endsWith("/action-success.jsp") && session.getAttribute("action") == null) {
						httpResponse.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");
						// PDF作成成功ページは 書類名がセッションに格納されていれば許可
					} else if (uri.endsWith("/PDFcreate-success.jsp")
							&& session.getAttribute("document") != null) {
						chain.doFilter(request, response);
					}
					//  PDF書類名がセッションに格納されていない状態で PDF作成成功ページにアクセスは不許可、メインページに遷移する
					else if (uri.endsWith("/PDFcreate-success.jsp") && session.getAttribute("document") == null) {
						httpResponse.sendRedirect(contextPath + "/mainMenu/main-menu.jsp");

						// その他のページへのアクセスは許可する
					} else {
						chain.doFilter(request, response);
					}
					// 何にも該当しない場合はログアウトページへ遷移する	
				} else {
					httpResponse.sendRedirect(contextPath + "/Logout");
				}

			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Page change failed", e);
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
	}
}
