package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.log4j.Log4j;

@Log4j
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                request.getMethod(), response.getStatus(), request.getRequestURI(), response.getLocale(), request.getRequestedSessionId()));
        request.getSession().invalidate();

        // Redrect to login page.
        response.sendRedirect(request.getContextPath() + "/login");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info(String.format("METHOD:%s STATUS:%s URI:%s LOCALE:%s SESSION_ID:%s",
                request.getMethod(), response.getStatus(), request.getRequestURI(), response.getLocale(), request.getRequestedSessionId()));
        this.doGet(request, response);
    }
}
