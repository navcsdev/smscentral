package com.minhhop.sms;

import com.jcabi.http.Request;
import com.jcabi.http.request.ApacheRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.io.Charsets;

/**
 * SMSCentral client, starting point to the entire library.
 *
 * <p>This is how you start communicating with SMS Central API:
 *
 * <pre> SMS sms = new SMSCentral("<email>", "<password>", "<token>");
 * Campaign.Smart campaign = new Campaign.Smart(sms.campaigns().get("1452853698794FvGTW5"));</pre>
 *
 * <p>The interfaces in this packages are trying to cover as much
 * as possible of SMS API.
 *
 *  @author Giang Le (giangle@minhhop.net)
 * @version $Id$
 * @since 0.1
 */
public final class SMSCentral implements SMS {
    private static final String API_KEY = "X-API-TOKEN";
    /**
     * Default request to start with.
     */
    private static final Request REQUEST =
            new ApacheRequest("http://localhost:8085/smscentral-api/")
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

    /**
     * REST request.
     */
    private final transient Request request;

    /**
     * SMSCentral token
     */
    private final transient String token;

    /**
     * Public ctor, for HTTP Basic Authentication.
     *
     * @param user User name
     * @param pwd  Password
     * @since 0.4
     */
    public SMSCentral(
            @NotNull(message = "user name can't be NULL") final String user,
            @NotNull(message = "password can't be NULL") final String pwd,
            @NotNull(message = "token can't be NULL") final String token) {
        this(
                SMSCentral.REQUEST.header(
                        HttpHeaders.AUTHORIZATION,
                        String.format(
                                "Basic %s",
                                DatatypeConverter.printBase64Binary(
                                        String.format("%s:%s", user, pwd)
                                                .getBytes(Charsets.UTF_8)
                                )
                        )
                ).header(
                    API_KEY, token
                ), token
        );
    }


    /**
     * Public ctor, with a custom request.
     *
     * @param req Request to start from
     * @since 0.4
     */
    public SMSCentral(
            @NotNull(message = "request can't be NULL") final Request req,
            @NotNull(message = "token can't be NULL") final String token) {
        this.token = token;
        this.request = req;
    }

    @Override
    @NotNull(message = "request can't be NULL")
    public Request entry() {
        return this.request;
    }

    @Override
    @NotNull(message = "Campaigns is never NULL")
    public Campaigns campaigns() {
        return new RtCampaigns(this, this.request);
    }

    @Override
    @NotNull(message = "request can't be NULL")
    public Reports reports() { return new RtReports(this, this.request); }

    public String token() {
        return this.token;
    }
}
