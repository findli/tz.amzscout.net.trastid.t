package tz.amzscout.net.trastid.t.throttler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tz.amzscout.net.trastid.t.config.AppConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Slf4j
public class RateLimiterAspect {
    private final ConcurrentHashMap<String, CounterRequest> limiters = new ConcurrentHashMap<>();
    @Value("${rateLimitPeriod}")
    String rateLimitPeriod;

    @Before("@annotation(rateLimit)")
    public void rateLimit(JoinPoint jp, RateLimit rateLimit) {
        final HttpServletRequest request = AppConfig.currentRequest.get();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (Objects.isNull(ipAddress)) {
            final String remoteAddr = request.getRemoteAddr();
            ipAddress = remoteAddr.contains(",") ? remoteAddr.split(",")[0] : remoteAddr;
        }

        final CounterRequest counter = limiters.get(ipAddress);
        if (Objects.isNull(counter)) {
            limiters.put(ipAddress, new CounterRequest());
        } else {
            counter.getVisitTimes().addLast(System.currentTimeMillis() / 1000);
            if (counter.getCounter() > rateLimit.value())
                throw new ExceedRequestException();
        }
    }

    class ExceedRequestException extends RuntimeException {

    }

    @Data
    class CounterRequest {
        private LinkedList<Long> visitTimes = new LinkedList() {{
            add(System.currentTimeMillis() / 1000);
        }};
        private int counter = 1;

        public int getCounter() {
            for (Iterator<Long> iterator = visitTimes.iterator(); iterator.hasNext(); ) {
                if (iterator.next() < (System.currentTimeMillis() / 1000) - Integer.valueOf(rateLimitPeriod)) {
                    try {
                        iterator.remove();
                    } catch (NoSuchElementException e) {

                    }
                } else {
                    break;
                }
            }
            return visitTimes.size();
        }
    }
}
