package io.papayankey.taskman.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Constant {

    private Constant() {
    }

    public static final String JWT_PREFIX = "Bearer ";
    public static final String JWT_AUTHORIZATION_HEADER = "Authorization";
    public static final String JWT_SECRET = "springBoot";
    public static final Date JWT_EXPIRATION = Date.from(Instant.now().plus(15, ChronoUnit.MINUTES));
    public static final Date JWT_ISSUED = Date.from(Instant.now());

}
