package org.app;


import com.github.fge.jsonschema.main.JsonSchema;
import org.apache.openjpa.persistence.validation.ValidationUtils;
import org.app.datamapping.json.outgoing.ProjectionSystemResponseBody;
import org.app.web.ProjectionSystemServlet;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ProjectionSystemServletTest {

    @Test
    public void testServletWithData() throws Exception {

        //given
        List<ProjectionSystemResponseBody> testData = prepareData();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ProjectionSystemServlet projectionSystemServlet = spy(new ProjectionSystemServlet());

        //when
        when(projectionSystemServlet.getData()).thenReturn(testData);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        projectionSystemServlet.doGet(request, response);

        writer.flush();
        //then
        assertTrue(stringWriter.toString().contains("{\"response\":[{\"uid\":\"some name\",\"name\":\"some uid\"},{\"uid\":\"some name 2\",\"name\":\"some uid 2\"}]}"));
    }

    @Test
    public void testServletEmptyData() throws Exception {
        //given
        List<ProjectionSystemResponseBody> testData = new ArrayList<>();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ProjectionSystemServlet projectionSystemServlet = spy(new ProjectionSystemServlet());

        //when
        when(projectionSystemServlet.getData()).thenReturn(testData);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        projectionSystemServlet.doGet(request, response);

        writer.flush();
        //then
        assertTrue(stringWriter.toString().contains("{\"response\":[]}"));
    }

    private List<ProjectionSystemResponseBody> prepareData() {
        List<ProjectionSystemResponseBody> preparedData = new ArrayList<>();
        ProjectionSystemResponseBody projectionSystemData1 = new ProjectionSystemResponseBody("some name", "some uid");

        ProjectionSystemResponseBody projectionSystemData2 = new ProjectionSystemResponseBody("some name 2", "some uid 2");

        preparedData.add(projectionSystemData1);
        preparedData.add(projectionSystemData2);
        return preparedData;
    }
}
