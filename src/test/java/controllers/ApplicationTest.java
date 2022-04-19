package controllers;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import models.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import models.User;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import services.ApplicationService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {
    @Mock
    private ApplicationService service;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    HttpSession session;

    @InjectMocks
    Application application;

    private List<User> userListTest;

    private final String clientPath = "/view/Home.jsp";
    private final String adminPath = "/view/HomeAdmin.jsp";

    @Before
    public void initTest() {
        userListTest = new ArrayList<>();
        User user1 = new User("Mike", "Mayers");
        User user2 = new User("Spike", "Spayers");
        userListTest.add(user1);
        userListTest.add(user2);
    }

    @Test
    public void doAppTestClientRole() throws ServletException, IOException {
        //given
        when(service.getAllUsers()).thenReturn(userListTest);
        when(request.getSession()).thenReturn(session);
        when(request.getAttribute("userListTest")).thenReturn(userListTest);
        when(session.getAttribute("role")).thenReturn(Role.CLIENT);
        //when
        application.doGet(request, response);
        //then
        List<User> userListTestGot = (ArrayList<User>) request.getAttribute("userListTest");
        verify(request).getAttribute("userListTest");
        Assert.assertEquals(userListTest, userListTestGot);

        verify(response).sendRedirect(request.getContextPath() + clientPath);
    }

    @Test
    public void doAppTestAdminRole() throws ServletException, IOException {
        //given
        when(service.getAllUsers()).thenReturn(userListTest);
        when(request.getSession()).thenReturn(session);
        when(request.getAttribute("userListTest")).thenReturn(userListTest);
        when(session.getAttribute("role")).thenReturn(Role.ADMIN);
        //when
        application.doGet(request, response);
        //then
        List<User> userListTestGot = (ArrayList<User>) request.getAttribute("userListTest");
        verify(request).getAttribute("userListTest");
        Assert.assertEquals(userListTest, userListTestGot);

        verify(response).sendRedirect(request.getContextPath() + adminPath);
    }
}