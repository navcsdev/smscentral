package com.minhhop.sms;

import com.jcabi.aspects.Immutable;
import com.jcabi.http.Request;
import java.util.Iterator;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;

/**
 * SMSCentral pagination.
 *
 * <p>This class is a convenient iterator over multiple JSON objects
 * returned by SMSCentral API.
 *
 * @author Giang Le (giangle@minhhop.net)
 * @version $Id$
 * @since 0.4
 * @param <T> Type of iterable objects
 * @see <a href="http://developer.smscentral.vn/v1/#pagination">Pagination</a>
 * @since 0.11
 */
@Immutable
public final class RtPagination<T> implements Iterable<T> {

    /**
     * Mapping that just copies JsonObject.
     * @checkstyle LineLength (3 lines)
     */
    public static final RtValuePagination.Mapping<JsonObject, JsonObject> COPYING =
            new RtValuePagination.Mapping<JsonObject, JsonObject>() {
                @Override
                public JsonObject map(final JsonObject value) {
                    return value;
                }
            };

    /**
     * Encapsulated paging.
     */
    private final transient RtValuePagination<T, JsonObject> pages;

    /**
     * Public ctor.
     * @param req Request
     * @param mpp Mapping
     */
    public RtPagination(
            @NotNull(message = "request can't be NULL") final Request req,
            @NotNull(message = "mapping can't be NULL")
            final RtValuePagination.Mapping<T, JsonObject> mpp
    ) {
        this.pages = new RtValuePagination<T, JsonObject>(req, mpp);
    }

    @Override
    public Iterator<T> iterator() {
        return this.pages.iterator();
    }

    /**
     * Entry.
     * @return Entry point
     */
    @NotNull(message = "Request is never NULL")
    public Request request() {
        return this.pages.request();
    }

    /**
     * Mapping.
     * @return Mapping
     */
    @NotNull(message = "map is never NULLs")
    public RtValuePagination.Mapping<T, JsonObject> mapping() {
        return this.pages.mapping();
    }
}