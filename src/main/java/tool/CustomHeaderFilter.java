package tool;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CustomHeaderFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResp = (HttpServletResponse) response;
		// CSPヘッダーを追加
		httpResp.setHeader("Content-Security-Policy",
				"default-src 'self'; img-src 'self' data:; script-src 'self' https://cdnjs.cloudflare.com https://cdn.jsdelivr.net; style-src 'self' https://cdn.jsdelivr.net; frame-ancestors 'self'; form-action 'self'");

		// Access-Control-Allow-Origin ヘッダーを追加
		httpResp.setHeader("Access-Control-Allow-Origin", "https://studentdocusoft.ddns.net");

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
