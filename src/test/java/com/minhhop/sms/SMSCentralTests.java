package com.minhhop.sms;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by giangle on 1/20/2016.
 */
public class SMSCentralTests {

    @Test
    public void testCampaigns() throws IOException {
        SMS sms = new SMSCentral("email", "password", "token");
//        Campaign.Smart campaign = new Campaign.Smart(sms.campaigns().get("1452853698794FvGTW5"));

        Iterator<Campaign> iterable = sms.campaigns().iterate("1").iterator();
        while (iterable.hasNext()) {
            System.out.println(iterable.next().json());
        }

//        System.out.println(campaign.total());
    }

    @Test
    public void testCreate() throws IOException {
        JsonObject object = Json.createObjectBuilder().add("sender_id", "SenderID")
            .add("type", 1)
            .add("name", "Campaign for 20012015")
            .add("messages",
                Json.createArrayBuilder().add(
                    Json.createObjectBuilder()
                        .add("content", "The tin dung HSBC cua quy khac vua thuc hien giao dich nhu sau: XXXXX3952-VND 17300041 on 03/01/2016")
                        .add("receiver", Json.createArrayBuilder().add("0908008726"))
                )
            ).build();


        SMS sms = new SMSCentral("email", "password", "token");
        Campaign.Smart campaign = new Campaign.Smart(sms.campaigns().create(object));
        System.out.println(campaign.json());
    }
}
