<img src="http://dashboard.smscentral.vn/static/images/smscentral1.png" />

## Object Oriented Wrapper of SMSCentral API
More details are here: [smscentral.vn](http://smscentral.vn/).
Java 7 or higher is required.

Set of classes in `com.minhhop.sms` package is
an object oriented API of SMSCentral:

```java
public class Main {
  public static void main(String[] args) throws IOException {
    SMS sms = new SMSCentral("<email>", "<password>", "<token>");
    Iterator<Campaign> iterable = sms.campaigns().iterate("1").iterator();
    while (iterable.hasNext()) {
        System.out.println(new Campaign.Smart(iterable.next()).name());
    }
  }
}
```

## How to create a campaign with JSONObject?

```java
JsonObject object = Json.createObjectBuilder().add("sender_id", "SMSCENTRAL")
    .add("type", 1)
    .add("name", "Campaign for 20012015")
    .add("messages",
        Json.createArrayBuilder().add(
            Json.createObjectBuilder()
                .add("content", "The tin dung HSBC cua quy khac vua thuc hien giao dich nhu sau: XXXXX3952-VND 17300041 on 03/01/2016")
                .add("receiver", Json.createArrayBuilder().add("0908008726")
            )
        )
    ).build();
SMS sms = new SMSCentral("<email>", "<password>", "<token>");
Campaign.Smart campaign = new Campaign.Smart(sms.campaigns().create(object));
```

## How to create a campaign with Type = CSKH?
```java
SMS sms = new SMSCentral("<email>", "<password>", "<token>");
List<Campaign.Message> messages = new ArrayList<Campaign.Message>();
messages.add(new Campaign.Message("0908008726", "The tin dung HSBC cua quy khac vua thuc hien giao dich nhu sau: XXXXX3952-VND 17300041 on 03/01/2016"));
Campaign campaign = sms.campaigns().sendCSKH("senderId", messages);
```

## How to schedule a campaign?
```java
SMS sms = new SMSCentral("<email>", "<password>", "<token>");
List<Campaign.Message> messages = new ArrayList<Campaign.Message>();
messages.add(new Campaign.Message("0908008726", "The tin dung HSBC cua quy khac vua thuc hien giao dich nhu sau: XXXXX3952-VND 17300041 on 03/01/2016"));
Date schedule = DateUtils.addDays(new Date(), 5);
Campaign campaign = sms.campaigns().sendCSKH("senderId", messages, schedule);
```

## How to create a campaign with Type = QC?
```java
SMS sms = new SMSCentral("<email>", "<password>", "<token>");
List<Campaign.Message> messages = new ArrayList<Campaign.Message>();
messages.add(new Campaign.Message("0908008726", "The tin dung HSBC cua quy khac vua thuc hien giao dich nhu sau: XXXXX3952-VND 17300041 on 03/01/2016"));
Date schedule = DateUtils.addDays(new Date(), 5);
Campaign campaign = sms.campaigns().sendQC("senderId", messages, schedule);
```

Note: SMS QC have to send to SMS Central API on 2 days before of task schedule date.

## How to get a campaign with given transaction id?
```java
SMS sms = new SMSCentral("<email>", "<password>", "<token>");
Campaign.Smart campaign = new Campaign.Smart(sms.campaigns().get("1452853698794FvGTW5"));
System.out.println(campaign.json());
```

## How to iterate all campaigns?
```java
SMS sms = new SMSCentral("<email>", "<password>", "<token>");
Iterator<Campaign> iterable = sms.campaigns().iterate("1").iterator();
while (iterable.hasNext()) {
    System.out.println(new Campaign.Smart(iterable.next()).name());
}
```

## Questions?

If you have any questions about the framework, or something doesn't work as expected,
please [submit an issue here](https://github.com/minhhoptech/smscentral/issues/new).