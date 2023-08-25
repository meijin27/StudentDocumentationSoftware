package tool;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// サーブレットのファイル名末尾がActionだった場合にページ遷移するためのクラス
// ｊｓｐのform内に***.actionと記載されていた場合、***Action.javaのファイルに遷移するためのクラス
@WebServlet(urlPatterns = { "*.action" })
public class FrontController extends HttpServlet {
	private static final Logger logger = CustomLogger.getLogger(FrontController.class);

	@Override
	public void doPost(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String path = request.getServletPath();
			if (path == null) {
				logger.log(Level.WARNING, "Servlet path is null.");
				return;
			}

			String name = path.substring(1).replace(".a", "A").replace('/', '.');
			if (name == null) {
				logger.log(Level.WARNING, "Class name derived from path is null.");
				return;
			}

			Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();
			if (action == null) {
				logger.log(Level.WARNING, "Failed to create an instance of Action class.");
				return;
			}

			String url = action.execute(request, response);
			if (url == null) {
				logger.log(Level.WARNING, "URL returned by action is null.");
				return;
			}

			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Page transition failed", e);
		}
	}

	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
