package com.iason.events.services;

import com.iason.events.domain.Event;
import com.iason.events.domain.Violation;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ViolationService {
    ConcurrentMap<UUID, Violation> violations = new ConcurrentHashMap<>();
    AtomicLong amountPaid = new AtomicLong(0);
    AtomicLong amountNotPaid = new AtomicLong(0);

    @Async
    @EventListener
    void handleNewEvent(Event event) {
        if (event.getType().equalsIgnoreCase("speed") && Double.compare(event.getSpeed(), event.getLimit()) > 0) {
            Violation violation = new Violation();
            violation.setEventId(event.getId());
            violation.setId(UUID.randomUUID());
            violation.setFine(Violation.SPEED_FINE);
            violation.setPaid(false);
            amountNotPaid.addAndGet(Violation.SPEED_FINE);
            violations.put(violation.getId(), violation);
        }
        if (event.getType().equalsIgnoreCase("red light")) {
            Violation violation = new Violation();
            violation.setEventId(event.getId());
            violation.setId(UUID.randomUUID());
            violation.setFine(Violation.RED_LIGHT_FINE);
            violation.setPaid(false);
            amountNotPaid.addAndGet(Violation.RED_LIGHT_FINE);
            violations.put(violation.getId(), violation);
        }
        System.out.println("Event is processed");
    }

    public Set<UUID> getViolations() {
        return violations.keySet();
    }

    public void payViolation(UUID id) {
        violations.computeIfPresent(id, (uuid, violation) ->
        {
            Violation v = violations.get(uuid);
            v.setPaid(true);
            long fine = (long) v.getFine();
            amountPaid.getAndAdd(fine);
            amountNotPaid.getAndAdd(-fine);
            return v;
        });
    }

    public String getSummary() {
        return "TotalPaid: " + amountPaid.get() + ", NotPaidYet: " + amountNotPaid.get();
    }
}
