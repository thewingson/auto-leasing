package kz.almat.controller;

import kz.almat.model.User;
import kz.almat.service.impl.UserServiceimpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {

    private final UserServiceimpl userServiceimpl = new UserServiceimpl();

    @Override
    public void init() {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String method = "";

        if (req.getParameter("method") != null){
            method =  req.getParameter("method");
        }

        switch (method){
            case "update":
                updateDo(req, resp);
                break;
            case "create":
                create(req, resp);
                break;
            default:
                getList(req, resp);
                break;
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String method = "";

        if (req.getParameter("method") != null){
           method =  req.getParameter("method");
        }

        switch (method){
            case "update":
                update(req, resp);
                break;
            case "getOne":
                getOne(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            default:
                getList(req, resp);
                break;
        }

    }

    private void getOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        User user = userServiceimpl.getById(id);
        req.setAttribute("user", user);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/user.jsp");
        dispatcher.forward(req, resp);

    }

    private void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<User> users = userServiceimpl.getAll();
        req.setAttribute("users", users);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/users.jsp");
        dispatcher.forward(req, resp);

    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User(null, firstName, lastName, email, username, password);
        userServiceimpl.create(user);
        getList(req, resp);

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        userServiceimpl.delete(id);

        getList(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        User userToUpdate = userServiceimpl.getById(id);
        req.setAttribute("user", userToUpdate);
        req.getRequestDispatcher("user/update.jsp").forward(req, resp);

    }

    private void updateDo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User userToUpdate = new User(id, firstName, lastName, email, username, password);

        userServiceimpl.update(id, userToUpdate);

        getList(req, resp);

    }
}
