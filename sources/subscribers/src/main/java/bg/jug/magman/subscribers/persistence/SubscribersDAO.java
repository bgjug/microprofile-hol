package bg.jug.magman.subscribers.persistence;

import bg.jug.magman.subscribers.domain.Subscriber;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by daleksan on 20.10.2016.
 */
@ApplicationScoped
public class SubscribersDAO {

    private AtomicLong sequence = new AtomicLong(0);

    private Map<Long, Subscriber> subscribers = new HashMap<>();

    @PostConstruct
    public void init() {
        try {
            final ObjectMapper om = new ObjectMapper();
            om.findAndRegisterModules();
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            final InputStream is = this.getClass().getResourceAsStream("/subscribers.json");
            final Set<Subscriber> result = om.readValue(is, new TypeReference<Set<Subscriber>>() {
            });
            result.forEach(e -> addSubscriber(e));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Subscriber> getSubscribers() {
        return subscribers.values().stream().collect(Collectors.toList());
    }

    public Subscriber findSubscriberById(Long subscriberId) {
        return subscribers.get(subscriberId);
    }

    public List<Subscriber> findExpiringSubscriptions(Integer afterDays) {
        return subscribers.values().stream().filter(e -> Duration.between(LocalDate.now(), e.getSubscribedUntil()).toDays() > afterDays).collect(Collectors.toList());
    }

    public void addSubscriber(Subscriber subscriber) {
        Long id = sequence.addAndGet(1);
        subscriber.setId(id);
        subscribers.put(id, subscriber);
    }

    public void updateSubscriber(Subscriber subscriber) {
        subscribers.replace(subscriber.getId(), subscriber);
    }

    public void prolongSubscription(Subscriber subscriber, final LocalDate untilDay) {
        subscribers.get(subscriber.getId()).setSubscribedUntil(untilDay);
    }

    public void deleteSubscriber(Long subscriberId) {
        subscribers.remove(subscriberId);
    }
}
