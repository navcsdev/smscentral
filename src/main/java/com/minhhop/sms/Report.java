package com.minhhop.sms;

import com.jcabi.aspects.Immutable;
import com.jcabi.aspects.Loggable;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import netscape.javascript.JSObject;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Report JSON data
 * @author Anh Vu (vunguyen@minhhop.net)
 */
public interface Report extends JsonReadable, JsonPatchable {
    /**
     * Get its owner.
     * @return SMS
     */
    @NotNull(message = "SMS is never NULL")
    SMS sms();

    /**
     * Smart Report with extra features.
     */
    @Immutable
    @ToString
    @Loggable(Loggable.DEBUG)
    @EqualsAndHashCode(of = { "report", "jsn" })
    final class Smart implements Report {
        /**
         * Encapsulated Report.
         */
        private final transient Report report;
        /**
         * SmartJson object for convenient JSON parsing.
         */
        private final transient SmartJson jsn;

        public Smart(@NotNull(message = "Report can not null") final Report report) {
            this.report = report;
            this.jsn = new SmartJson(report);
        }

        public JsonArray details() throws IOException {
            return this.json().getJsonArray("details");
        }

        public JsonObject CSKH() throws IOException {
            return this.json().getJsonObject("CSKH");
        }

        public JsonObject QC() throws IOException {
            return this.json().getJsonObject("QC");
        }

        public JsonArray summarys() throws IOException {
            return this.json().getJsonArray("summarys");
        }

        public JsonArray campaigns() throws IOException {
            return this.json().getJsonArray("campaigns");
        }

        @Override
        public void patch(@NotNull(message = "JSON is never NULL") JsonObject json) throws IOException {
        }

        @Override
        public JsonObject json() throws IOException {
            return this.report.json();
        }

        @Override
        public SMS sms() {
            return this.report.sms();
        }

    }

    public static enum Type {
        DETAIL("detail"),
        SUMMARY("summary"),
        CAMPAIGN("campaign");
        String type;
        Type(String type) {
            this.type = type;
        }
        String getValue() {
            return type;
        }

    }

    public static enum Period {
        DAILY("daily"),
        WEEKLY("weekly"),
        MONTHLY("monthly");
        String period;
        Period (String period) {
            this.period = period;
        }
        String getValue() {
            return period;
        }

    }
}
