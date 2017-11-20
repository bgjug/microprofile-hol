package bg.jug.magman.clients;

import bg.jug.magman.domain.Advertiser;
import bg.jug.magman.domain.Subscriber;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Dmitry Alexandrov on 14.11.16.
 */
@ApplicationScoped
public class AdvertisersClient {

    private static String ENDPOINT_URL = "http://localhost:9100/advertiser";

    public List<Advertiser> getAllAdvertisers() {
        try {
            URL obj = new URL(ENDPOINT_URL + "/");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("Response code -> " + responseCode);

            final ObjectMapper om = new ObjectMapper();
            om.findAndRegisterModules();
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            final InputStream is = con.getInputStream();
            final Set<Advertiser> advertisers = om.readValue(is, new TypeReference<Set<Advertiser>>() {
            });
            return new ArrayList<>(advertisers);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    public Advertiser getAdvertiserById(long id) {
        try {
            URL obj = new URL(ENDPOINT_URL + "/" + id);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("Response code -> " + responseCode);

            final ObjectMapper om = new ObjectMapper();
            om.findAndRegisterModules();
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            final InputStream is = con.getInputStream();
            final Advertiser advertiser = om.readValue(is, new TypeReference<Advertiser>() {
            });
            return advertiser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removeAdvertiser(long id) {
        try {
            URL obj = new URL(ENDPOINT_URL + "/" + id);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("DELETE");
            int responseCode = con.getResponseCode();
            System.out.println("Response code -> " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAdvertiser(Advertiser advertiser) {
        try {
            URL url = new URL(ENDPOINT_URL + "/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(advertiser.toString().getBytes("UTF-8"));
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
