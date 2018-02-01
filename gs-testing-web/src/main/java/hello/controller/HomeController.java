package hello.controller;

import hello.service.impl.GreetingServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private GreetingServiceImpl service;

    public HomeController(GreetingServiceImpl homeService) {
        this.service = homeService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String greeting() {
        return service.greet();
    }
}
