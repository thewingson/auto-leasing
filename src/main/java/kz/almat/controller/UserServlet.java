package kz.almat.controller;

import kz.almat.model.User;
import kz.almat.service.impl.UserServiceimpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserServlet extends HttpServlet {

    private final UserServiceimpl userServiceimpl = new UserServiceimpl();

    @Override
    public void init() { }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String method = req.getParameter("method");

        if (method != null) {
            if (method.equals("update")) {
                updateDo(req, resp);
            } else if (method.equals("delete")) {
                delete(req, resp);
            }
        } else {
            create(req, resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String method = req.getParameter("method");

        if (method != null) {
            if (method.equals("update")) {
                update(req, resp);
            } else if (method.equals("getOne")) {
                getOne(req, resp);
            }
        } else {
            getList(req, resp);
        }

    }

    protected void getOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        User user = null;
        try {
            user = userServiceimpl.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("user", user);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/user.jsp");
        dispatcher.forward(req, resp);

    }

    protected void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<User> users = null;
        try {
            users = userServiceimpl.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("users", users);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/users.jsp");
        dispatcher.forward(req, resp);

    }

    protected void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User(null, firstName, lastName, email, username, password);
        try {
            userServiceimpl.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getList(req, resp);

    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        try {
            userServiceimpl.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getList(req, resp);
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        User userToUpdate = null;
        try {
            userToUpdate = userServiceimpl.getById(id);
        }
        // TODO: this is what I referred to in services. Service layer should be responsible for handling these exceptions
        catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("user", userToUpdate);
        req.getRequestDispatcher("user/update.jsp").forward(req, resp);

    }

    protected void updateDo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User userToUpdate = new User(id, firstName, lastName, email, username, password);

        try {
            userServiceimpl.update(id, userToUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getList(req, resp);

    }
}
