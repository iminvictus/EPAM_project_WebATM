package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import models.User;
import services.ApplicationService;

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
