package org.app.web;

import org.app.datamapping.json.outgoing.ProjectionSystemResponseBody;
import org.app.datamapping.json.translation.DataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ProjectionSystemServlet", urlPatterns = {"/api/spxtags/"})
public class ProjectionSystemServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(ProjectionSystemServlet.class);

    /**
     * TODO: unfortunately, I did not find something suitable in TomEE for json objects serialization
     * TODO: jackson-databind can help to simplify serialization/deserialization of json objects
     * @param request
     * @param response json data
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
            response.setContentType("application/json;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                List<ProjectionSystemResponseBody> dataList = getData();
                if (dataList.isEmpty()) {
                    out.print("{");
                    out.print("response: [");
                    out.print("]");
                    out.print("}");
                } else {

                    out.print("{");
                    out.print("response: [");
                    int i = 0;

                    for (ProjectionSystemResponseBody data : dataList) {
                        i++;
                        out.print("{");
                        out.print("uid: ".concat(data.getUid()));
                        out.print(",name: ".concat(data.getName()));
                        out.print("}");
                        if (i < dataList.size()) {
                            out.print(",");
                        }
                    }
                    out.print("]");
                    out.print("}");
                }
            } catch (Exception exc) {
                log.error("Can't getresponse {}", exc);
            }
    }

    /**
     * TODO: In future we will not be able to manage only get requests. And when is required more parameters for our api we will use post
     * TODO: For example it can be request with json. And we can use tools for serialization in json objects. And fill org.app.datamapping.json.incoming.ProjectionSystemRequestBody;
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
