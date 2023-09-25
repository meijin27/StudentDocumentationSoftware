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

// エンコーディングフィルタのクラス、サーブレットにUTF-8等を自動適用する
public class EncodingFilter implements Filter {
	private static final Logger logger = CustomLogger.getLogger(EncodingFilter.class);

	@Override
	public void doFilter(
			ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			chain.doFilter(request, response);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Page encoding failed", e);
		}
	}

	@Override
	public void init(FilterConfig filtercConfig) {
	}

	@Override
	public void destroy() {
	}
}
