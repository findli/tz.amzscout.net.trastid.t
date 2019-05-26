package tz.amzscout.net.trastid.t.throttler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class RateLimitAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleConflict(RateLimiterAspect.ExceedRequestException e, HttpServletResponse response) {
        return new ResponseEntity(HttpStatus.BAD_GATEWAY);
    }
}
