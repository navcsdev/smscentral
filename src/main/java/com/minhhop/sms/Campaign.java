package com.minhhop.sms;

import com.jcabi.aspects.Immutable;
import com.jcabi.aspects.Loggable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import java.io.IOException;


/**
 * Campaign JSON data
 * @author Giang Le (giangle@minhhop.net)
 */
public interface Campaign extends JsonReadable, JsonPatchable, Comparable<Campaign> {
    /**
     * Get its owner.
     * @return SMS
     */
    @NotNull(message = "SMS is never NULL")
    SMS sms();

    /**
     * Message need to send
     */
    final class Message {
        /**
         * Receiver
         */
        private final transient String number;

        /**
         * Message body
         */
        private final transient String content;

        /**
         * Public ctor.
         * @param phone
         * @param message
         */
        public Message(@NotNull(message = "Phone number can not be NULL") final String phone,
                       @NotNull(message = "Phone number can not be NULL") final String message) {
            this.content = message;
            this.number = phone;
        }

        /**
         * @return its number
         */
        public String number() {
            return this.number;
        }

        /**
         * @return its content
         */
        public String content() {
            return this.content;
        }
    }

    /**
     * Smart Campaign with extra features.
     */
    @Immutable
    @ToString
    @Loggable(Loggable.DEBUG)
    @EqualsAndHashCode(of = { "camp", "jsn" })
    final class Smart implements Campaign {
        /**
         * Encapsulated Campaign.
         */
        private final transient Campaign camp;
        /**
         * SmartJson object for convenient JSON parsing.
         */
        private final transient SmartJson jsn;

        /**
         * Public ctor.
         * @param camp Campaign
         */
        public Smart(@NotNull(message = "Campaign can't be NULL") final Campaign camp) {
            this.camp = camp;
            this.jsn = new SmartJson(camp);
        }

        /**
         * Get its name.
         * @return Name
         * @throws IOException If there is any I/O problem
         */
        @NotNull(message = "name is never NULL")
        public String name() throws IOException {
            return this.jsn.text("name");
        }

        /**
         * Get its id.
         * @return Id
         * @throws IOException If there is any I/O problem
         */
        @NotNull(message = "id is never NULL")
        public String id() throws IOException {
            return this.jsn.text("id");
        }

        /**
         * Get its total.
         * @return Total sms
         * @throws IOException If there is any I/O problem
         */
        @NotNull(message = "id is never NULL")
        public int total() throws IOException {
            return this.jsn.number("total_sms");
        }

        @Override
        public int compareTo(Campaign o) {
            return 0;
        }

        @Override
        public void patch(@NotNull(message = "JSON is never NULL") JsonObject json) throws IOException {
        }

        @Override
        public JsonObject json() throws IOException {
            return this.camp.json();
        }

        @Override
        public SMS sms() {
            return this.camp.sms();
        }
    }
}
