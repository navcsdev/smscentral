package com.minhhop.sms;

import com.jcabi.http.Request;

import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Campaign JSON data
 * @author Giang Le (giangle@minhhop.net)
 */
public class RtCampaign implements Campaign {
    /**
     * Github.
     */
    private final transient SMS sms;

    /**
     * RESTful entry.
     */
    private final transient Request entry;

    /**
     * RESTful request.
     */
    private final transient Request request;

    /**
     * Transaction ID
     */
    private final transient String id;

    /**
     * Public ctor.
     * @param sms Github
     * @param req Request
     * @param txn Coordinate of the repo
     */
    RtCampaign(final SMS sms, final Request req, final String txn) {
        this.sms = sms;
        this.entry = req;
        this.id = txn;
        this.request = this.entry.uri()
                .path("/1.0/campaigns")
                .path(txn)
                .back();
    }

    @Override
    public void patch(
            @NotNull(message = "JSON is never NULL") final JsonObject json)
            throws IOException {
        new RtJson(this.request).patch(json);
    }

    @Override
    @NotNull(message = "JSON is never NULL")
    public JsonObject json() throws IOException {
        return new RtJson(this.request).fetch();
    }

    @Override
    public int compareTo(final Campaign repo) {
        return 0;
    }

    /**
     * @return Transaction ID
     */
    public String id() {
        return id;
    }

    @Override
    public SMS sms() {
        return this.sms;
    }
}
