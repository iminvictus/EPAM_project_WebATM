package controllers;

import models.User;
import org.apache.log4j.Logger;
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
  private static final Logger logger = Logger.getLogger(Application.class);
  private ApplicationService applicationService;

  @Override
  public void init() throws ServletException {
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