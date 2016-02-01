package com.minhhop.sms;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * SMS Central reports.
 *
 * @author Anh Vu (vunguyen@minhhop.net)
 * @version $Id$
 * @since 0.1
 */
public interface Reports {
    /**
     * Get it owner
     * @return Github
     */
    @NotNull(message = "github is never null")
    SMS sms();

    /**
     * Get detail report by id.
     * @param id report id
     * @return detail report
     * @see <a href="#get">Get Report</a>
     */
    @NotNull(message = "Report is never NULL")
    Report get(@NotNull(message = "coordinates can't be NULL") String id);

    /**
     * Get detail report by id.
     * @param type report id
     * @param startTime start time taking report
     * @param endTime end time taking report
     * @return detail report
     * @see <a href="#get">Get Report</a>
     */
    @NotNull(message = "Report is never NULL")
    Report get(@NotNull(message = "coordinates can't be NULL") Report.Type type, Date startTime, Date endTime);

    /**
     * Get detail report by id.
     * @param type report id
     * @param period period taking report
     * @param startTime start time taking report
     * @param endTime end time taking report
     * @return detail report
     * @see <a href="#get">Get Report</a>
     */
    @NotNull(message = "Report is never NULL")
    Report get(@NotNull(message = "coordinates can't be NULL") Report.Type type, Report.Period period, Date startTime, Date endTime);

    /**
     * Iterate all public reports, starting with the one you're seen already.
     * @param identifier The integer ID of the last Report that you’ve seen.
     * @return Iterator repo
     * @see <a href="#list-all-reports>List all reports</a>
     */
    @NotNull(message = "iterable is never NULL")
    Iterable<Report> iterate (
            @NotNull(message = "identifier can't be NULL") String identifier
    );
}
