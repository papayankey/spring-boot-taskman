package io.papayankey.taskman.security.constant;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Constant {
    public static String JWT_PREFIX = "Bearer ";
    public static String JWT_AUTHORIZATION_HEADER = "Authorization";
    public static String JWT_SECRET = "springBoot";
    public static Date JWT_EXPIRATION = Date.from(Instant.now().plus(15, ChronoUnit.MINUTES));
    public static Date JWT_ISSUED = Date.from(Instant.now());
}
