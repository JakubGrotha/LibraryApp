package com.example.libraryapp.utils;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Component
public class UtcTimeProvider implements TimeProvider {

    private final Clock clock = Clock.systemUTC();

    @Override
    public Instant now() {
        return Instant.now(clock);
    }

    @Override
    public ZoneId zoneId() {
        return clock.getZone();
    }
}
