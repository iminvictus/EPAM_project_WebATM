package filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {

    private String contextPath = "/localhost:8080";
    private String loginURI = contextPath + "/login";

    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    FilterChain filterChain;

    @InjectMocks
    SecurityFilter securityFilter;

    @SneakyThrows
    @Test
    public void doFilterTest() {
        //given
        when(request.getContextPath()).thenReturn(contextPath);
        when(request.getRequestURI()).thenReturn(loginURI);
        //when
        securityFilter.doFilter(request, response, filterChain);
        //then
        verify(request, times(1)).getContextPath();
        verify(request, times(1)).getRequestURI();
        verify(filterChain, times(1)).doFilter(request, response);

    }
}