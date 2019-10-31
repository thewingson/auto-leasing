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
import java.util.List;

public class CarServlet extends HttpServlet {

    private final CarServiceImpl carServiceImpl = new CarServiceImpl();

    @Override
    public void init() {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String method = "";

        if (req.getParameter("method") != null) {
            method = req.getParameter("method");
        }

        switch (method) {
            case "update":
                updateDo(req, resp);
                break;
            case "create":
                create(req, resp);
                break;
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String method = "";

        if (req.getParameter("method") != null) {
            method = req.getParameter("method");
        }

        switch (method) {
            case "update":
                update(req, resp);
                break;
            case "getOne":
                getOne(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            case "rent":
                rent(req, resp);
                break;
            default:
                getList(req, resp);
                break;
        }

    }

    protected void getOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        CarDTO car = carServiceImpl.getByIdDTO(id);

        req.setAttribute("car", car);
        RequestDispatcher dispatcher = req.getRequestDispatcher("car/car.jsp");
        dispatcher.forward(req, resp);

    }

    protected void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<CarDTO> cars = carServiceImpl.getAllDTO();

        req.setAttribute("cars", cars);
        RequestDispatcher dispatcher = req.getRequestDispatcher("car/cars.jsp");
        dispatcher.forward(req, resp);

    }

    protected void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String mark = req.getParameter("mark");
        String model = req.getParameter("model");
        String registeredNumber = req.getParameter("registeredNumber");

        Car car = new Car(null, mark, model, registeredNumber);
        carServiceImpl.create(car);

        getList(req, resp);

    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        carServiceImpl.delete(id);

        getList(req, resp);
    }

    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        Car carToUpdate = carServiceImpl.getById(id);

        req.setAttribute("car", carToUpdate);
        req.getRequestDispatcher("car/update.jsp").forward(req, resp);

    }

    protected void updateDo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        String mark = req.getParameter("mark");
        String model = req.getParameter("model");
        String registeredNumber = req.getParameter("registeredNumber");

        Car carToUpdate = new Car(id, mark, model, registeredNumber);

        carServiceImpl.update(id, carToUpdate);

        getList(req, resp);

    }

    protected void rent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");

        carServiceImpl.rent(id, username);

        getList(req, resp);

    }

}
