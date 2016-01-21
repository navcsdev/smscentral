package com.minhhop.sms;

import com.jcabi.http.Request;

import javax.validation.constraints.NotNull;

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
public interface SMS {
    /**
     * RESTful request, an entry point to the Github API.
     * @return Request
     */
    @NotNull(message = "request is never NULL")
    Request entry();

    /**
     * Get campaigns.
     * @return Campaigns
     */
    @NotNull(message = "list of campaigns is never NULL")
    Campaigns campaigns();

    /**
     * API token
     * @return API token
     */
    @NotNull(message = "token is never NULL")
    String token();
}