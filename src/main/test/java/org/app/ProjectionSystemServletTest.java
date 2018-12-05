package org.app;


import org.app.datamapping.json.outgoing.ProjectionSystemData;
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
        List<ProjectionSystemData> testData = prepareData();

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
        assertTrue(stringWriter.toString().contains("{response: [{uid: some uid,name: some name},{uid: some uid 2,name: some name 2}]}"));
    }

    private List<ProjectionSystemData> prepareData() {
        List<ProjectionSystemData> preparedData = new ArrayList<>();
        ProjectionSystemData projectionSystemData1 = new ProjectionSystemData();
        projectionSystemData1.setName("some name");
        projectionSystemData1.setUid("some uid");

        ProjectionSystemData projectionSystemData2 = new ProjectionSystemData();
        projectionSystemData2.setName("some name 2");
        projectionSystemData2.setUid("some uid 2");

        preparedData.add(projectionSystemData1);
        preparedData.add(projectionSystemData2);
        return preparedData;
    }
}
