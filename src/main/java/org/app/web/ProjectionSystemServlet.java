package org.app.web;

import org.app.datamapping.json.outgoing.ProjectionSystemData;
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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
            response.setContentType("application/json;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                List<ProjectionSystemData> dataList = getData();
                if (dataList.isEmpty()) {
                    out.print("{");
                    out.print("response: [");
                    out.print("]");
                    out.print("}");
                } else {

                    out.print("{");
                    out.print("response: [");
                    int i = 0;

                    for (ProjectionSystemData data : dataList) {
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

    public List<ProjectionSystemData> getData() {
        DataManager dataManager = new DataManager();
        return dataManager.extractData();
    }
}
