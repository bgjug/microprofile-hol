package bg.jug.magman.clients;

import bg.jug.magman.domain.Author;
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
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }

    public Author getAuthorById(long id) {
        try {
            URL obj = new URL(ENDPOINT_URL + "/findById/" + id);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println("Response code -> " + responseCode);

            final ObjectMapper om = new ObjectMapper();
            om.findAndRegisterModules();
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            final InputStream is = con.getInputStream();
            final Author author = om.readValue(is, new TypeReference<Author>() {
            });
            return author;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removeAuthor(long id) {
        try {
            URL obj = new URL(ENDPOINT_URL + "/delete/" + id);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("DELETE");
            int responseCode = con.getResponseCode();
            System.out.println("Response code -> " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAuthor(Author author) {
        try {
            URL url = new URL(ENDPOINT_URL + "/add");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            os.write(author.toString().getBytes("UTF-8"));
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
