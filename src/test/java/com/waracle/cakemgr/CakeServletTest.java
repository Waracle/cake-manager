package com.waracle.cakemgr;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CakeServletTest {

    private static final String ACCEPT = "accept";
    private static final String APPLICATION_JSON = "application/json";
    private CakeServlet testee;
    @Mock
    private CakeFactory mockCakeFactory;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private CakeStore mockStore;
    @Mock
    private PrintWriter mockWriter;
    @Mock
    private ServletContext mockServletContext;
    @Mock
    private RequestDispatcher mockDispatcher;


    @Before
    public void setUp() throws IOException {
        testee = new TestCakeServlet(mockCakeFactory, mockStore);
        when(mockResponse.getWriter()).thenReturn(mockWriter);
        Cake fruitCake = new Cake();
        fruitCake.setTitle("Fruit Cake");
        fruitCake.setDescription("Full of fruity goodness");
        fruitCake.setImage("http://fruit-cake-pictures/pic1.jpeg");
        when(mockStore.allCakes()).thenReturn(Arrays.asList(fruitCake));
        when(mockServletContext.getRequestDispatcher("/index.jsp")).thenReturn(mockDispatcher);
    }

    @Test
    public void cakesAreStoredToDatabaseDuringInitialisation() throws ServletException, IOException {
        testee.init();
        verify(mockStore, times(20)).store(any(Cake.class));
    }

    @Test
    public void jsonIsReturnedWhenDoGetIsCalledAndAcceptHeaderIsAppJson() throws ServletException, IOException {
        when(mockRequest.getHeader(ACCEPT)).thenReturn(APPLICATION_JSON);
        testee.doGet(mockRequest, mockResponse);
        verify(mockWriter).print("[{\"cakeId\":null,\"title\":\"Fruit Cake\",\"image\":\"http://fruit-cake-pictures/pic1.jpeg\",\"desc\":\"Full of fruity goodness\"}]");
    }

    @Test
    public void htmlIsReturnedWhenDoGetIsCalledAndAcceptHeaderIsTextHTML() throws ServletException, IOException {
        when(mockRequest.getHeader(ACCEPT)).thenReturn("text/html");
        testee.doGet(mockRequest, mockResponse);
        verify(mockDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void cakeIsMadeWhenPosted() throws ServletException, IOException {
        when(mockRequest.getHeader(ACCEPT)).thenReturn(APPLICATION_JSON);
        testee.doPost(mockRequest, mockResponse);
        verify(mockCakeFactory).makeCake(mockRequest);
    }

    @Test
    public void cakeIsStoredWhenPosted() throws ServletException, IOException {
        when(mockRequest.getHeader(ACCEPT)).thenReturn(APPLICATION_JSON);
        testee.doPost(mockRequest, mockResponse);
        verify(mockStore).store(any(Cake.class));
    }

    @Test
    public void jsonIsReturnedWhenCakePosted() throws ServletException, IOException {
        when(mockRequest.getHeader(ACCEPT)).thenReturn(APPLICATION_JSON);
        testee.doPost(mockRequest, mockResponse);
        verify(mockWriter).print(anyString());
    }

    /**
     ** private wrapper class used to inject mock servletContext
     **/
    private class TestCakeServlet extends CakeServlet {
        TestCakeServlet(CakeFactory cakeFactory, CakeStore cakeStore) {
            super(cakeFactory, cakeStore);
        }
        public ServletContext getServletContext() {
            return mockServletContext;
        }
    }
}
