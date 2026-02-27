package com.abissell.yeoman.functions;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public sealed interface ImageTs {
    record Utc(ZonedDateTime dateTime) implements ImageTs {
        public Utc {
            if (!dateTime.getZone().equals(ZoneOffset.UTC)) {
                throw new IllegalArgumentException(
                        "Expected UTC zone but got: " + dateTime.getZone());
            }
        }
    }

    record Unzoned(LocalDateTime dateTime) implements ImageTs {}
}
