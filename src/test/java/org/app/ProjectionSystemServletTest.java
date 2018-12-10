package org.app;


import org.app.datamapping.json.outgoing.ProjectionSystemResponseBody;
import org.app.web.ProjectionSystemServlet;
import org.junit.Before;
import org.junit.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ProjectionSystemServletTest {

    private String path;

    @Before
    public void setUp() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        path = new File(classLoader.getResource("schema.json").getFile()).getCanonicalPath();
    }

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
        assertTrue(ValidationUtils.isJsonValid(readJsonSchema(path), stringWriter.toString()));
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
        assertTrue(ValidationUtils.isJsonValid(readJsonSchema(path), stringWriter.toString()));
    }

    private List<ProjectionSystemResponseBody> prepareData() {
        List<ProjectionSystemResponseBody> preparedData = new ArrayList<>();
        ProjectionSystemResponseBody projectionSystemData1 = new ProjectionSystemResponseBody("some name", "some uid");

        ProjectionSystemResponseBody projectionSystemData2 = new ProjectionSystemResponseBody("some name 2", "some uid 2");

        preparedData.add(projectionSystemData1);
        preparedData.add(projectionSystemData2);
        return preparedData;
    }

    private String readJsonSchema(String fileName) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        reader.close();

        String content = stringBuilder.toString();
        return content;
    }
}
