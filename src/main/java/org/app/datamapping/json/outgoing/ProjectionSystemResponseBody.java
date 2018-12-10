package org.app.datamapping.json.outgoing;

/**
 * Response body.
 * TODO: jackson-databind can help to simplify serialization/deserialization of json objects
 * TODO: this body easily can be used for jackson-databind
 */
public class ProjectionSystemResponseBody {

    /**
     * uid value
     */
    private String uid;
    /**
     * name
     */
    private String name;

    public ProjectionSystemResponseBody(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

}
