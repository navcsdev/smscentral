package com.minhhop.sms;

import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * SMS Central campaigns.
 *
 * @author Giang Le (giangle@minhhop.net)
 * @version $Id$
 * @since 0.1
 */
public interface Campaigns {
    /**
     * Get its owner.
     * @return Github
     */
    @NotNull(message = "github is never NULL")
    SMS sms();

    /**
     * Create campaign.
     * @param json Campaign creation JSON
     * @return Campaign
     * @throws IOException If there is any I/O problem
     * @since 0.5
     * @see <a href="#create">Create Campaign</a>
     */
    @NotNull(message = "Campaign is never NULL")
    Campaign create(@NotNull(message = "JSON can't be NULL") JsonObject json)
            throws IOException;

    /**
     * Create campaign.
     * @param senderId Sender ID
     * @param messages list of messages and its number
     * @return
     * @throws IOException
     */
    Campaign sendCSKH(String senderId, List<Campaign.Message> messages)
            throws IOException;

    /**
     * Create campaign.
     * @param senderId Sender ID
     * @param messages list of messages and its number
     * @param schedule Date
     * @return
     * @throws IOException
     */
    Campaign sendCSKH(String senderId, List<Campaign.Message> messages, Date schedule)
            throws IOException;

    /**
     * Create campaign.
     * @param senderId Sender ID
     * @param messages list of messages and its number
     * @param schedule Date. SMS QC have to send to SMS Central API on 2 days before of task schedule date.
     * @return
     * @throws IOException
     */
    Campaign sendQC(String senderId, List<Campaign.Message> messages, Date schedule)
            throws IOException;

    /**
     * Get campaign by name.
     * @param id Campaign id
     * @return Campaign
     * @see <a href="#get">Get Campaign</a>
     */
    @NotNull(message = "Campaign is never NULL")
    Campaign get(@NotNull(message = "coordinates can't be NULL") String id);




    /**
     * Iterate all public campaigns, starting with the one you've seen already.
     * @param identifier The integer ID of the last Campaign that you’ve seen.
     * @return Iterator of repo
     * @see <a href="#list-all-campaigns>List all campaigns</a>
     */
    @NotNull(message = "iterable is never NULL")
    Iterable<Campaign> iterate(
            @NotNull(message = "identifier can't be NULL") String identifier
    );
}
