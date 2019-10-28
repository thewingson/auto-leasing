package kz.almat.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecurityFilter implements Filter {

    private String role;

    public void init(FilterConfig filterConfig) {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        role = (String) request.getSession().getAttribute("role");

        if (request.getServletPath().equals("/car")) {
            car(servletRequest, servletResponse, filterChain);
        } else if (request.getServletPath().equals("/user")) {
//            user(servletRequest, servletResponse, filterChain); for future
        } else {
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("error/not-authorized.jsp");
            dispatcher.forward(servletRequest, servletResponse);
        }

    }

    public void car(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String method = servletRequest.getParameter("method");
        if (method == null) {                                                                                             // READ
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (method.equals("create") || method.equals("update") || method.equals("delete")) {                    // CUD
            if (role != null && role.equals("ADMIN")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("error/not-authorized.jsp");
                dispatcher.forward(servletRequest, servletResponse);
            }
        } else if (method.equals("rent") || method.equals("return")) {                                                 // RENT, RETURN
            if (role != null && role.equals("USER")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("error/not-authorized.jsp");
                dispatcher.forward(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

//    public void user(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        String method = servletRequest.getParameter("method");
//        if (method == null || method == null || method.equals("create") || method.equals("update") || method.equals("delete")) {
//            if (role != null && role.equals("ADMIN")) {
//                filterChain.doFilter(servletRequest, servletResponse);
//            } else {
//                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("error/not-authorized.jsp");
//                dispatcher.forward(servletRequest, servletResponse);
//            }
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//    }

    public void destroy() {

    }
}
