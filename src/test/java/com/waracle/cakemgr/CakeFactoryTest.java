package com.waracle.cakemgr;

import static org.junit.Assert.*;
import com.fasterxml.jackson.core.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CakeFactoryTest {

    private static final String TEST_TITLE = "test-title";
    private static final String TEST_DESCRIPTION = "test-description";
    private static final String TEST_IMAGE = "test-image";
    private CakeFactory testee;
    @Mock
    private JsonParser mockParser;
    @Mock
    private HttpServletRequest mockRequest;

    @Before
    public void setUp() throws Exception {
        testee = new CakeFactory();
        when(mockParser.nextFieldName()).thenReturn("title").thenReturn("description").thenReturn("image");
        when(mockParser.nextTextValue()).thenReturn(TEST_TITLE).thenReturn("test-description").thenReturn("test-image");
        when(mockRequest.getParameter("title")).thenReturn(TEST_TITLE);
        when(mockRequest.getParameter("description")).thenReturn(TEST_DESCRIPTION);
        when(mockRequest.getParameter("image")).thenReturn(TEST_IMAGE);
    }

    @Test
    public void cakeTitleIsSetCorrectlyWhenCakeIsMadeFromServletRequest() throws IOException {
        Cake cake = testee.makeCake(mockRequest);
        assertThat(cake.getTitle(), is(TEST_TITLE));
    }

    @Test
    public void cakeDescriptionIsSetCorrectlyWhenCakeIsMadeFromServletRequest() throws IOException {
        Cake cake = testee.makeCake(mockRequest);
        assertThat(cake.getDescription(), is(TEST_DESCRIPTION));
    }

    @Test
    public void cakeImageIsSetCorrectlyWhenCakeIsMadeFromServletRequest() throws IOException {
        Cake cake = testee.makeCake(mockRequest);
        assertThat(cake.getImage(), is(TEST_IMAGE));
    }

}