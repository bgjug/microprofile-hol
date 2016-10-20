package bg.jug.magman.subscribers.persistence;

import bg.jug.magman.subscribers.domain.Subscriber;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Created by daleksan on 20.10.2016.
 */
@ApplicationScoped
public class SubscribersDAO {

    private AtomicLong sequence = new AtomicLong(0);

    private Map<Long, Subscriber> subscribers = new HashMap<>();

    public List<Subscriber> getSubscribers() {
        return subscribers.values().stream().collect(Collectors.toList());
    }

    public Subscriber findSubscriberById(Long subscriberId) {
        subscribers.get(subscriberId);
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
