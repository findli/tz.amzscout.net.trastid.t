package tz.amzscout.net.trastid.t.throttler;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * @return rate limit in queries per {rateLimitPeriod config value}
     */
    int value() default 50;

}
