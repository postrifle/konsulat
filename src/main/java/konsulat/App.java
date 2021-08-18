package konsulat;


import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//import javax.mail.*;

/**
 * Hello world!
 */

//TODO: password Twilio Twili@Twili@2021
//    TODO: password Vonage2021
public class App {

    public static void main(String[] args) throws IOException, InterruptedException {

        Boolean flag = true;
        int count = 0;
//        URL url = new URL("https://registration.mfa.gov.ua/qmaticwebbooking/rest/schedule/branches/5a78cad444c63e9ad53b3f14e4049dd60c73c591361544be919e9689c4472dc3/dates;servicePublicId=c34f6d374f8893ca21a2ec10cd0f0bb35b52366a82ac773fc695c72663cdf45e;customSlotLength=5");
//                             https://registration.mfa.gov.ua/qmaticwebbooking/rest/schedule/branches/5a78cad444c63e9ad53b3f14e4049dd60c73c591361544be919e9689c4472dc3/dates;servicePublicId=550e746a8227c1b1b02af059911a6d5cd9d433578ddaf1b807a2ca2207d1c55c;customSlotLength=10
//        URL url = new URL("https://registration.mfa.gov.ua/qmaticwebbooking/rest/schedule/branches/5a78cad444c63e9ad53b3f14e4049dd60c73c591361544be919e9689c4472dc3/dates;servicePublicId=550e746a8227c1b1b02af059911a6d5cd9d433578ddaf1b807a2ca2207d1c55c;customSlotLength=10");
        URL url = new URL("https://registration.mfa.gov.ua/qmaticwebbooking/rest/schedule/branches/5a78cad444c63e9ad53b3f14e4049dd60c73c591361544be919e9689c4472dc3/services");
        while (flag) {
            TimeUnit.SECONDS.sleep(10);
//            System.out.println("TEST PRINT");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            if (content.toString().equals("[]")){
                System.out.println(content.toString());
            }
            in.close();
            con.disconnect();

//            if (!content.toString().equals("[]")) {
            if (content.toString().contains("date")){
                System.out.println("NOT EMPTY CONTENT: "+content.toString());
                ++count;
                if (count > 2) {
                    flag = false;
                }
                VonageClient client = VonageClient.builder().apiKey("9cd984a0").apiSecret("qp6DWBlq05yDRxlo").build();

                TextMessage message = new TextMessage("Vonage APIs",
                        "48532270218",
                        "KONSULAT ALARM"
                );

                SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

                if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
                    System.out.println("Message sent successfully.");
                } else {
                    System.out.println("Message failed with error: " + response.getMessages().get(0).getErrorText());
                }
            }
        }
    }
}
