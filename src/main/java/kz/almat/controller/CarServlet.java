package kz.almat.controller;


import kz.almat.model.Agreement;
import kz.almat.model.Car;
import kz.almat.model.User;
import kz.almat.model.dto.CarDTO;
import kz.almat.service.impl.CarServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
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
            case "rent":
                rentDo(req, resp);
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
            case "returnBack":
                returnBack(req, resp);
                break;
            default:
                getList(req, resp);
                break;
        }

    }

    private void getOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        CarDTO car = carServiceImpl.getByIdDTO(id);

        req.setAttribute("car", car);
        RequestDispatcher dispatcher = req.getRequestDispatcher("car/car.jsp");
        dispatcher.forward(req, resp);

    }

    private void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Car> cars = carServiceImpl.getAll();

        req.setAttribute("cars", cars);
        RequestDispatcher dispatcher = req.getRequestDispatcher("car/cars.jsp");
        dispatcher.forward(req, resp);

    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String mark = req.getParameter("mark");
        String model = req.getParameter("model");
        String registeredNumber = req.getParameter("registeredNumber");

        Car car = new Car(null, mark, model, registeredNumber, null);
        carServiceImpl.create(car);

        getList(req, resp);

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));

        carServiceImpl.delete(id);

        getList(req, resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        Car carToUpdate = carServiceImpl.getById(id);

        req.setAttribute("car", carToUpdate);
        req.getRequestDispatcher("car/update.jsp").forward(req, resp);

    }

    private void updateDo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        String mark = req.getParameter("mark");
        String model = req.getParameter("model");
        String registeredNumber = req.getParameter("registeredNumber");

        Car carToUpdate = new Car(id, mark, model, registeredNumber, null);

        carServiceImpl.update(id, carToUpdate);

        getList(req, resp);

    }

    private void rent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));

        Car carToRent = carServiceImpl.getById(id);

        req.setAttribute("car", carToRent);
        req.getRequestDispatcher("car/rent.jsp").forward(req, resp);

    }

    private void rentDo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        Long car_id = Long.parseLong(req.getParameter("id"));
        Long user_id = (Long) session.getAttribute("user_id");

        String driverLicense = req.getParameter("driverLicense");
        Timestamp startDate = Timestamp.valueOf(req.getParameter("startDate"));
        Timestamp endDate = Timestamp.valueOf(req.getParameter("endDate"));

        User user = new User(user_id, null, null, null, null, null);
        Car car = new Car(car_id, null, null,null, null);

        Agreement agreement = new Agreement(null, user, car, startDate, endDate);

        carServiceImpl.rent(agreement, driverLicense);

        getList(req, resp);

    }

    private void returnBack(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        HttpSession session = req.getSession();
        Long userId = (Long) session.getAttribute("user_id");

        carServiceImpl.returnBack(id, userId);

        getList(req, resp);

    }

}
