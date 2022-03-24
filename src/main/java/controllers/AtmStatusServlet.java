package controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import dao.users.UserDAO;
import models.User;
import services.ApplicationService;
import utils.BdCredentials;
import utils.DatabaseConnectionManager;

@WebServlet(value = "/status")
public class AtmStatusServlet extends HttpServlet {
    ApplicationService applicationService = new ApplicationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        long id = Long.parseLong(request.getParameter("id"));
        User user = applicationService.getUserById(id);
        request.setAttribute("user", user);
        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/view/AtmService.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    public void destroy() {
        applicationService.destroy();
        super.destroy();
    }
}
