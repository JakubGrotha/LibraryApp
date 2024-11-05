package com.example.libraryapp.utils;

import java.time.Instant;
import java.time.ZoneId;

public interface TimeProvider {

    Instant now();

    ZoneId zoneId();
}
