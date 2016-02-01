package com.minhhop.sms;

import com.jcabi.aspects.Immutable;
import com.jcabi.aspects.Loggable;
import com.jcabi.http.Request;
import lombok.EqualsAndHashCode;

import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * SMS Central reports.
 *
 * @author Anh Vu (vunguyen@minhhop.net)
 * @version $Id$
 * @since 0.1
 */
@Immutable
@Loggable(Loggable.DEBUG)
@EqualsAndHashCode(of = { "sms", "entry" })
public final class RtReports implements Reports {
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
    RtReports(final SMS sms, final Request req) {
        this.sms = sms;
        this.entry = req;
    }

    @NotNull(message = "toString is never NULL")
    public String toString() { return this.entry.uri().get().toString(); }

    @NotNull(message = "SMS is never NULL")
    public SMS sms() { return this.sms; }

    @Override
    @NotNull(message = "Report is never NULL")
    public Report get(@NotNull(message = "id can't be NULL") String id) {
        return new RtReport(this.sms, this.entry, id);
    }

    @Override
    @NotNull(message = "Report is never NULL")
    public Report get(@NotNull(message = "id can't be NULL") Report.Type type, Date startTime, Date endTime) {
        return new RtReport(this.sms, this.entry, type, startTime, endTime);
    }

    @Override
    @NotNull(message = "Report is never NULL")
    public Report get(@NotNull(message = "id can't be NULL") Report.Type type, Report.Period period, Date startTime, Date endTime) {
        return new RtReport(this.sms, this.entry, type, period, startTime, endTime);
    }

    public Iterable<Report> iterate(@NotNull(message = "identifier can't be NULL") String identifier) {
        return new RtPagination<Report>(
                this.entry.uri().path("/1.0/reports").queryParam("page", identifier).back(),
                new RtValuePagination.Mapping<Report, JsonObject>() {
                    @Override
                    public Report map(final JsonObject object) {
                        return RtReports.this.get(
                                object.getString("id")
                        );
                    }
                }
        );
    }

}
