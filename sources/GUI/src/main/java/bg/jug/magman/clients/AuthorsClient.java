package bg.jug.magman.clients;

import bg.jug.magman.domain.Author;
import bg.jug.magman.domain.Subscriber;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Dmitry Alexandrov on 13.11.16.
 */
@ApplicationScoped
public class AuthorsClient {

    private static String ENDPOINT_URL = "http://localhost:8082/authors";

    public List<Author> getAllAuthors() {

        try {
            URL obj = new URL(ENDPOINT_URL + "/findAll");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("Response code -> " + responseCode);

            final ObjectMapper om = new ObjectMapper();
            om.findAndRegisterModules();
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            final InputStream is = con.getInputStream();
            final Set<Author> authors = om.readValue(is, new TypeReference<Set<Author>>() {
            });

            return new ArrayList<>(authors);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }
}
