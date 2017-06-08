package filter;

import java.io.IOException;

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

/**
 * Servlet implementation class FiltroDeSeguranca
 */

@WebFilter(urlPatterns = { "/atualizarCadCli.jsp/*", "/sucesso.jsp/*", "/index.jsp/*"} )

public class FiltroDeSeguranca implements Filter {

	public void init(FilterConfig config) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,

	FilterChain chain) throws IOException, ServletException {
/*
		HttpSession session = ((HttpServletRequest) request).getSession();

		String id = (String) session.getAttribute("id");

	//	String dispatcherType = String.valueOf(request.getDispatcherType());

	//	System.out.println(dispatcherType);

		if (id == null) {

			((HttpServletResponse) response).sendRedirect("index.jsp");

		} else {

			chain.doFilter(request, response);

		}
*/
		((HttpServletResponse)response).sendRedirect("ControllerServlet");
		
	}

	public void destroy() {

	}
}
