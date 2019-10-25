package kz.almat.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecurityFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if(request.getSession().getAttribute("username") != null){
            filterChain.doFilter(servletRequest, servletResponse);
        } else{
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("error/not-authorized.jsp");
            dispatcher.forward(servletRequest, servletResponse);
        }

    }

    public void destroy() {

    }
}
