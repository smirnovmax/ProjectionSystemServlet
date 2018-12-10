package org.app.web;

import org.app.datamapping.json.outgoing.ProjectionSystemResponseBody;
import org.app.datamapping.json.translation.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@WebServlet(name = "ProjectionSystemServlet", urlPatterns = {"/api/spxtags"})
public class ProjectionSystemServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ProjectionSystemServlet.class);

    /**
     *
     * @param request
     * @param response json data
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        StringWriter stWriter = new StringWriter();
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        try (PrintWriter out = response.getWriter()) {
            List<ProjectionSystemResponseBody> dataList = getData();
            for (ProjectionSystemResponseBody data : dataList) {
                jsonArray.add(Json.createObjectBuilder().add("uid", data.getUid()).add("name", data.getName()));
            }
            JsonWriter jsonWriter = Json.createWriter(stWriter);
            JsonObject model = Json.createObjectBuilder().add("response", jsonArray).build();
            jsonWriter.writeObject(model);
            jsonWriter.close();
            out.print(stWriter.toString());
        } catch (Exception exc) {
            log.error("Can't getresponse {}", exc);
        }
    }

    /**
     * TODO: In future we will not be able to manage only get requests. And when is required more parameters for our api we will use post
     * TODO: For example it can be request with json. And we can use tools for serialization in json objects. And fill org.app.datamapping.json.incoming.ProjectionSystemRequestBody;
     *
     * @return
     */
//    @Override
//    public void doPost(HttpServletRequest request, HttpServletResponse response) {
//
//    }

    public List<ProjectionSystemResponseBody> getData() {
        DataManager dataManager = new DataManager();
        return dataManager.extractData();
    }
}
