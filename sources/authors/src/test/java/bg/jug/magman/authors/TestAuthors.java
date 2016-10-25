package bg.jug.magman.authors;

import bg.jug.magman.authors.domain.Author;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by Dmitry Alexandrov on 25.10.16.
 */
public class TestAuthors {

    private final Logger log = Logger.getLogger(TestAuthors.class.getName());

    @Test
    public void readSpeakers() throws Exception {
        final ObjectMapper om = new ObjectMapper();
        final InputStream is = this.getClass().getResourceAsStream("/authors.json");
        final Set<Author> authors = om.readValue(is, new TypeReference<Set<Author>>() {
        });

        Assert.assertFalse("Failed to get any Authors", authors.isEmpty());

        for (final Author author : authors) {
            Assert.assertNotNull(author.getFirstName());
            this.log.info(author.toString());
        }
    }
}
