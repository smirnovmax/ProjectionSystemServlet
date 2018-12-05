package org.app.datamapping.json.outgoing;

/**
 * TODO: It is not used. But it would be quite good, in case of incorrect request to our api to generate response about error
 */
public class ProjectionSystemResponseError {
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
