package cn.kc.sample.home;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return SecurityUtils.getSubject().isAuthenticated() ? "home/homeSignedIn" : "home/homeNotSignedIn";
    }
}
