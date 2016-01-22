<img src="http://dashboard.smscentral.vn/static/images/smscentral1.png" />

More details are here: [smscentral.vn](http://smscentral.vn/).
Java 7 or higher is required.

Set of classes in `com.minhhop.sms` package is
an object oriented API of Github:

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


## Questions?

If you have any questions about the framework, or something doesn't work as expected,
please [submit an issue here](https://github.com/jcabi/jcabi-github/issues/new).