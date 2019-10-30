package kz.almat.controller;


import kz.almat.model.Car;
import kz.almat.model.dto.CarDTO;
import kz.almat.service.impl.CarServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CarServlet extends HttpServlet {

    private final CarServiceImpl carServiceImpl = new CarServiceImpl();

    @Override
    public void init() {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String method = req.getParameter("method");

        if (method != null) {
            if (method.equals("update")) {
                updateDo(req, resp);
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
            } else if (method.equals("delete")) {
                delete(req, resp);
            } else if (method.equals("rent")) {
                rent(req, resp);
            }
        } else {
            getList(req, resp);
        }

    }

    protected void getOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        CarDTO car = null;
        try {
            car = carServiceImpl.getByIdDTO(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("car", car);
        RequestDispatcher dispatcher = req.getRequestDispatcher("car/car.jsp");
        dispatcher.forward(req, resp);

    }

    protected void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<CarDTO> cars = null;
        try {
            cars = carServiceImpl.getAllDTO();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("cars", cars);
        RequestDispatcher dispatcher = req.getRequestDispatcher("car/cars.jsp");
        dispatcher.forward(req, resp);

    }

    protected void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String mark = req.getParameter("mark");
        String model = req.getParameter("model");
        String registeredNumber = req.getParameter("registeredNumber");

        Car car = new Car(null, mark, model, registeredNumber);
        try {
            carServiceImpl.create(car);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getList(req, resp);

    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        try {
            carServiceImpl.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getList(req, resp);
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        Car carToUpdate = null;
        try {
            carToUpdate = carServiceImpl.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("car", carToUpdate);
        req.getRequestDispatcher("car/update.jsp").forward(req, resp);

    }

    protected void updateDo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        String mark = req.getParameter("mark");
        String model = req.getParameter("model");
        String registeredNumber = req.getParameter("registeredNumber");

        Car carToUpdate = new Car(id, mark, model, registeredNumber);

        try {
            carServiceImpl.update(id, carToUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getList(req, resp);

    }

    protected void rent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        try {
            carServiceImpl.rent(id, username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getList(req, resp);

    }

}
