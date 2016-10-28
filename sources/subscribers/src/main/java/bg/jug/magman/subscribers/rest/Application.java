package bg.jug.magman.subscribers.rest;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dmitry Alexandrov on 28.10.16.
 */
@ApplicationPath("/")
public class Application extends javax.ws.rs.core.Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet();

        resources.add(ResourceSubscribers.class);
        return resources;
    }
}