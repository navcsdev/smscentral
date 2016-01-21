package com.minhhop.sms;

import com.jcabi.aspects.Immutable;
import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.response.RestResponse;
import com.jcabi.http.response.WebLinkingResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;

/**
 * SMS Central value pagination.
 *
 * @author Giang Le (giangle@minhhop.net)
 * @version $Id$
 * @since 0.8
 * @param <T> Type of iterable objects
 * @param <P> Type of source objects
 * @see <a href="http://developer.smscentral.vn/v1/#pagination">Pagination</a>
 */
@Immutable
@EqualsAndHashCode(of = { "entry", "map" })
public final class RtValuePagination<T, P extends JsonValue> implements
        Iterable<T> {

    /**
     * Mapping to use.
     */
    private final transient RtValuePagination.Mapping<T, P> map;

    /**
     * Start entry to use.
     */
    private final transient Request entry;

    /**
     * Public ctor.
     * @param req Request
     * @param mpp Mapping
     */
    public RtValuePagination(
            @NotNull(message = "req can't be NULL") final Request req,
            @NotNull(message = "map can't be null")
            final RtValuePagination.Mapping<T, P> mpp
    ) {
        this.entry = req;
        this.map = mpp;
    }

    @Override
    @NotNull(message = "toString is never NULL")
    public String toString() {
        return this.entry.uri().get().toString();
    }

    @Override
    @NotNull(message = "Iterator is never NULL")
    public Iterator<T> iterator() {
        return new RtValuePagination.Items<T, P>(this.entry, this.map);
    }

    /**
     * Entry.
     * @return Entry point
     */
    @NotNull(message = "Request is never NULL")
    public Request request() {
        return this.entry;
    }

    /**
     * Mapping.
     * @return Mapping
     */
    @NotNull(message = "map is never NULLs")
    public RtValuePagination.Mapping<T, P> mapping() {
        return this.map;
    }

    /**
     * Mapping from JsonValue successor to the destination type.
     * @param <X> Type of custom object
     * @param <P> Type of source object
     */
    @Immutable
    public interface Mapping<X, P extends JsonValue> {
        /**
         * Map JsonValue successor to the type required.
         * @param value Extends JsonValue
         * @return Custom object
         */
        X map(P value);
    }

    /**
     * Iterator.
     */
    @EqualsAndHashCode(of = { "mapping", "request", "objects", "more" })
    private static final class Items<X, P extends JsonValue> implements
            Iterator<X> {
        /**
         * Mapping to use.
         */
        private final transient RtValuePagination.Mapping<X, P> mapping;
        /**
         * Next entry to use.
         */
        private transient Request request;
        /**
         * Available objects.
         */
        private transient Queue<P> objects;
        /**
         * Current entry can be used to fetch objects.
         */
        private transient boolean more = true;
        /**
         * Ctor.
         * @param entry Entry
         * @param mpp Mapping
         */
        Items(final Request entry, final RtValuePagination.Mapping<X, P> mpp) {
            this.request = entry;
            this.mapping = mpp;
            this.objects = new LinkedList<P>();
        }
        @Override
        public X next() {
            synchronized (this.mapping) {
                if (!this.hasNext()) {
                    throw new NoSuchElementException(
                            "no more elements in pagination, use #hasNext()"
                    );
                }
                return this.mapping.map(this.objects.remove());
            }
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException("#remove()");
        }
        @Override
        public boolean hasNext() {
            synchronized (this.mapping) {
                if ((this.objects == null || this.objects.isEmpty())
                        && this.more) {
                    try {
                        this.fetch();
                    } catch (final IOException ex) {
                        throw new IllegalStateException(ex);
                    }
                }
                return !this.objects.isEmpty();
            }
        }
        /**
         * Fetch the next portion, if available.
         * @throws IOException If there is any I/O problem
         */
        @SuppressWarnings("unchecked")
        private void fetch() throws IOException {
            final RestResponse response = this.request.fetch()
                    .as(RestResponse.class)
                    .assertStatus(HttpURLConnection.HTTP_OK);
            final WebLinkingResponse.Link link = response
                    .as(WebLinkingResponse.class)
                    .links()
                    .get("next");
            if (link == null) {
                this.more = false;
            } else {
                this.request = response.jump(link.uri());
            }
            final JsonArray arr = response.as(JsonResponse.class).json()
                    .readArray();
            final Queue<P> list = new LinkedList<P>();
            for (final JsonValue value : arr) {
                list.add((P) value);
            }
            this.objects = list;
        }
    }

}
