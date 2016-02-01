package com.minhhop.sms;

import com.jcabi.http.Request;

import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by navcs on 2/1/16.
 */
public class RtReport implements Report {
    static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
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
    RtReport (final SMS sms, final Request req, final String txn) {
        this.sms = sms;
        this.entry = req;
        this.id = txn;
        this.request = this.entry.uri()
                .path("/1.0/reports")
                .path(txn)
                .back();
    }

    RtReport (final SMS sms, final Request req, final Report.Type type, final Date startTime, final Date endTime) {
        this.sms = sms;
        this.entry = req;
        this.id = "";
        this.request = this.entry.uri()
                .path("/1.0/reports")
                .queryParam("type", type.getValue())
                .queryParam("start_time", format.format(startTime))
                .queryParam("end_time", format.format(endTime))
                .back();
    }

    RtReport (final SMS sms, final Request req, final Report.Type type, final Report.Period period, final Date startTime, final Date endTime) {
        this.sms = sms;
        this.entry = req;
        this.id = "";
        this.request = this.entry.uri()
                .path("/1.0/reports")
                .queryParam("type", type.getValue())
                .queryParam("period", period.getValue())
                .queryParam("start_time", format.format(startTime))
                .queryParam("end_time", format.format(endTime))
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
    public SMS sms() {
        return this.sms;
    }
}
