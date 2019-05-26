package tz.amzscout.net.trastid.t.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tz.amzscout.net.trastid.t.throttler.RateLimit;

@Controller
public class DefaultController {
    @RequestMapping("/")
    @RateLimit
    @ResponseBody
    public String test() {
        return "";
    }
}
