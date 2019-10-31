package kz.almat.controller;

import kz.almat.constant.CommonViewParameters;
import kz.almat.model.Role;
import kz.almat.model.User;
import kz.almat.service.impl.AuthServiceImpl;
import kz.almat.service.impl.UserServiceimpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    private final UserServiceimpl userServiceimpl = new UserServiceimpl();
    private final AuthServiceImpl authServiceImpl = new AuthServiceImpl();

    @Override
    public void init() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String method = req.getParameter("method");

        if (method != null) {
            if (method.equals("signUp")) {
                signUpDo(req, resp);
            } else if (method.equals("signIn")) {
                signInDo(req, resp);
            } else if (method.equals("signOut")) {
                signOutDo(req, resp);
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String method = req.getParameter("method");

        if (method != null) {
            if (method.equals("signIn")) {
                signIn(req, resp);
            } else if (method.equals("signUp")) {
                signUp(req, resp);
            } else if (method.equals("signOut")) {
                signOutDo(req, resp);
            }
        } else {
            signIn(req, resp);
        }

    }

    protected void signIn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("auth/sign-in.jsp");
        dispatcher.forward(req, resp);

    }

    protected void signUp(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("auth/sign-up.jsp");
        dispatcher.forward(req, resp);

    }

    protected void signOutDo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute(CommonViewParameters.USERNAME);

        // TODO: session.invalidate() is used for this purpose
        if (username != null) {
            session.removeAttribute(CommonViewParameters.USERNAME);
            session.removeAttribute(CommonViewParameters.PASSWORD);
            session.removeAttribute(CommonViewParameters.ROLE);
        }

        signIn(req, resp);

    }

    protected void signUpDo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String username = req.getParameter(CommonViewParameters.USERNAME);
        String password = req.getParameter(CommonViewParameters.PASSWORD);
        String confirmPassword = req.getParameter("confirmPassword");

        if (password.equals(confirmPassword)) {
            User user = new User(null, firstName, lastName, email, username, password, Role.valueOf("USER"));

            userServiceimpl.create(user);

            req.setAttribute(CommonViewParameters.USERNAME, username);
            req.setAttribute(CommonViewParameters.PASSWORD, password);

            signInDo(req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("auth/sign-up.jsp");
            dispatcher.forward(req, resp);
        }

    }

    protected void signInDo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter(CommonViewParameters.USERNAME);
        String password = req.getParameter(CommonViewParameters.PASSWORD);

        User userToValidate = authServiceImpl.authenticate(username, password);

        HttpSession session = req.getSession();

        if (userToValidate != null) {
            session.setAttribute(CommonViewParameters.USERNAME, username);
            session.setAttribute(CommonViewParameters.PASSWORD, password);
            session.setAttribute(CommonViewParameters.ROLE, userToValidate.getRole().toString());

            resp.sendRedirect("car");
        } else {
            signIn(req, resp);
        }

    }
}
