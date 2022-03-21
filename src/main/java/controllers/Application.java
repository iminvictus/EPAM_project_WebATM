package controllers;

import models.User;
import services.ApplicationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/service")
public class Application extends HttpServlet {

  private ApplicationService applicationService;

  @Override
  public void init() throws ServletException {
    applicationService = new ApplicationService();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<User> userList = applicationService.getAllUsers();
    req.setAttribute("userList", userList);
    req.getRequestDispatcher("view/index.jsp").forward(req, resp);
  }

  @Override
  public void destroy() {
    applicationService.destroy();
  }
}