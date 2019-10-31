package kz.almat.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SecurityFilter implements Filter {

    private String role;
    private String requestPath;
    private String ADMIN = "ADMIN";
    private String USER = "USER";

    public void init(FilterConfig filterConfig) {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        role = (String) request.getSession().getAttribute("role");

        requestPath = request.getServletPath();

        switch (requestPath) {
            case "/car":
                car(servletRequest, servletResponse, filterChain);
                break;
            case "/user":
                user(servletRequest, servletResponse, filterChain);
                break;
            default:
                RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("error/not-found.jsp");
                dispatcher.forward(servletRequest, servletResponse);
                break;

        }


    }

    public void car(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String method = "";

        if (servletRequest.getParameter("method") != null) {
            method = servletRequest.getParameter("method");
        }

        switch (method) {
            case "create":
                roleCheck(ADMIN, servletRequest, servletResponse, filterChain);
                break;
            case "update":
                roleCheck(ADMIN, servletRequest, servletResponse, filterChain);
                break;
            case "delete":
                roleCheck(ADMIN, servletRequest, servletResponse, filterChain);
                break;
            case "rent":
                roleCheck(USER, servletRequest, servletResponse, filterChain);
                break;
            case "return":
                roleCheck(USER, servletRequest, servletResponse, filterChain);
                break;
            default:
                filterChain.doFilter(servletRequest, servletResponse);
                break;
        }
    }

    public void user(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String method = "";

        if (servletRequest.getParameter("method") != null) {
            method = servletRequest.getParameter("method");
        }

        switch (method) {
            case "create":
                roleCheck(ADMIN, servletRequest, servletResponse, filterChain);
                break;
            case "update":
                roleCheck(ADMIN, servletRequest, servletResponse, filterChain);
                break;
            case "delete":
                roleCheck(ADMIN, servletRequest, servletResponse, filterChain);
                break;
            default:
                filterChain.doFilter(servletRequest, servletResponse);
                break;
        }
    }

    private void roleCheck(String necessaryRole, ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (role != null && role.equals(necessaryRole)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("error/not-authorized.jsp");
            dispatcher.forward(servletRequest, servletResponse);
        }
    }

    public void destroy() {

    }
}
