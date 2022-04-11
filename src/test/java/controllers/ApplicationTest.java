package controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import models.User;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {
    private List<User> userListTest;

    private final String path = "view/index.jsp";

    @Before
    public void initTest() {
        userListTest = new ArrayList<>();
        User user1 = new User("Mike", "Mayers", new BigDecimal(1000));
        User user2 = new User("Spike", "Spayers", new BigDecimal(2000));
        userListTest.add(user1);
        userListTest.add(user2);
    }

    @Test
    public void doAppTest() throws ServletException, IOException {
        //make a servlet
        final Application servlet = new Application();
        //mocking entities
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        servlet.init();

        request.setAttribute("userListTest", userListTest);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        /*when we invoke getRequestDispatcher Mockito returns a mocked object "dispatcher"*/
        when(request.getAttribute("userListTest")).thenReturn(userListTest);
        servlet.doGet(request, response);

        List<User> userListTestGot = (ArrayList<User>)request.getAttribute("userListTest");

        verify(request).setAttribute("userListTest", userListTest); //check whether a method was invoked
        verify(request).getAttribute("userListTest");
        verify(request, times(1)).setAttribute("userListTest", userListTest);//check a method was invoked 1 time
        verify(request, times(1)).getRequestDispatcher(path);
        verify(dispatcher).forward(request, response); //check whether a method "forward" was invoked
        Assert.assertEquals(userListTest, userListTestGot);
    }
}