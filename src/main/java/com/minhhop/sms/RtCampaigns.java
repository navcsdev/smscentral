package com.minhhop.sms;

import com.jcabi.aspects.Immutable;
import com.jcabi.aspects.Loggable;
import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.response.RestResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;

/**
 * SMS Central campaigns.
 *
 * @author Giang Le (giangle@minhhop.net)
 * @version $Id$
 * @since 0.1
 */
@Immutable
@Loggable(Loggable.DEBUG)
@EqualsAndHashCode(of = { "sms", "entry" })
public final class RtCampaigns implements Campaigns {
    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * SMS Central .
     */
    private final transient SMS sms;

    /**
     * RESTful entry.
     */
    private final transient Request entry;

    /**
     * Public ctor.
     * @param sms SMS
     * @param req Request
     */
    RtCampaigns(final SMS sms, final Request req) {
        this.sms = sms;
        this.entry = req;
    }

    @Override
    @NotNull(message = "toString is never NULL")
    public String toString() {
        return this.entry.uri().get().toString();
    }

    @Override
    @NotNull(message = "SMS is never NULL")
    public SMS sms() {
        return this.sms;
    }

    @Override
    @NotNull(message = "Campaign is never NULL")
    public Campaign create(@NotNull(message = "JSON can't be NULL") JsonObject json) throws IOException {
        return this.get(
            this.entry.uri().path("/1.0/campaigns")
                .back().method(Request.POST)
                .body().set(json).back()
                .fetch().as(RestResponse.class)
                .assertStatus(HttpURLConnection.HTTP_CREATED)
                .as(JsonResponse.class)
                .json().readObject().getString("id")
        );
    }

    @Override
    @NotNull(message = "Campaign is never NULL")
    public Campaign sendCSKH(String senderId, List<Campaign.Message> messages) throws IOException {
        return this.send(senderId, 1, messages, null);
    }

    @Override
    public Campaign sendCSKH(String senderId, List<Campaign.Message> messages, Date schedule) throws IOException {
        return this.send(senderId, 1, messages, schedule);
    }

    @Override
    public Campaign sendQC(String senderId, List<Campaign.Message> messages, Date schedule) throws IOException {
        return this.send(senderId, 0, messages, schedule);
    }

    private Campaign send(String senderId, int type, List<Campaign.Message> messages, Date schedule) throws IOException {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Campaign.Message message : messages) {
            builder.add(Json.createObjectBuilder()
                    .add("content", message.content())
                    .add("receiver", Json.createArrayBuilder().add(message.number())));
        }
        JsonObjectBuilder object = Json.createObjectBuilder().add("sender_id", senderId)
                .add("type", type)
                .add("messages", builder);
        if (schedule != null) {
            object.add("schedule", format.format(schedule));
        }
        return this.create(object.build());
    }

    @Override
    @NotNull(message = "Campaign is never NULL")
    public Campaign get(@NotNull(message = "Id can't be NULL") String id) {
        return new RtCampaign(this.sms, this.entry, id);
    }

    public Iterable<Campaign> iterate(@NotNull(message = "identifier can't be NULL") String identifier) {
        return new RtPagination<Campaign>(
                this.entry.uri().path("/1.0/campaigns").queryParam("page", identifier).back(),
                new RtValuePagination.Mapping<Campaign, JsonObject>() {
                    @Override
                    public Campaign map(final JsonObject object) {
                        return RtCampaigns.this.get(
                                object.getString("id")
                        );
                    }
                }
        );
    }
}
