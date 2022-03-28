package controllers;

import lombok.extern.log4j.Log4j;
import models.User;
import org.apache.log4j.Logger;
import services.ApplicationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j
@WebServlet("/service")
public class Application extends HttpServlet {
  private ApplicationService applicationService;

  @Override
  public void init() {
    applicationService = new ApplicationService();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
            req.getMethod(), resp.getStatus(), req.getRequestURI(), resp.getLocale(), req.getRequestedSessionId()));
    List<User> userList = applicationService.getAllUsers();
    req.setAttribute("userList", userList);
    req.getRequestDispatcher("view/index.jsp").forward(req, resp);
  }

  @Override
  public void destroy() {
    applicationService.destroy();
  }
}