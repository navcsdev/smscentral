package com.minhhop.sms;

import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.response.RestResponse;
import lombok.EqualsAndHashCode;

import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;

/**
 * REST JSON item.
 *
 * @author Giang Le (giangle@minhhop.net)
 * @version $Id$
 * @since 0.1
 */
@EqualsAndHashCode(of = "request")
final class RtJson {

    /**
     * RESTful request.
     */
    private final transient Request request;

    /**
     * Public ctor.
     *
     * @param req Request
     */
    RtJson(final Request req) {
        this.request = req;
    }

    /**
     * Fetch JSON object.
     * @return JSON object
     * @throws IOException If fails
     */
    @NotNull(message = "JSON is never NULL")
    public JsonObject fetch() throws IOException {
        return this.request.fetch()
                .as(RestResponse.class)
                .assertStatus(HttpURLConnection.HTTP_OK)
                .as(JsonResponse.class)
                .json().readObject();
    }

    /**
     * Patch it.
     * @param json JSON to use for patching
     * @throws IOException If fails
     */
    public void patch(
            @NotNull(message = "JSON is never NULL") final JsonObject json
    ) throws IOException {
        final StringWriter post = new StringWriter();
        Json.createWriter(post).writeObject(json);
        this.request.body().set(post.toString()).back()
                .method(Request.PATCH)
                .fetch().as(RestResponse.class)
                .assertStatus(HttpURLConnection.HTTP_OK);
    }

}
