package pl.piomin.services.caller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.piomin.services.caller.domen.Route;
import pl.piomin.services.caller.services.DBService;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/caller")
public class CallerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallerController.class);

    //    @Autowired
    BuildProperties buildProperties;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private DBService dbService;

    @RequestMapping(value = "/registerRoute", method = RequestMethod.GET)
    @ResponseBody
    public String registerRoute(
            @RequestParam("route") final String route,
            @RequestParam("inkassator") final String inkassator
    ) {
        LOGGER.info("Start register route {} {}", route, inkassator);
        dbService.addRoute(route, inkassator);
        LOGGER.info("Success register route {} {}", route, inkassator);
        return String.format("success registered [route=%s, inkassator=%s]", route, inkassator);
    }

    @RequestMapping(value = "/getAllRoute", method = RequestMethod.GET)
    @ResponseBody
    public String getAllRoute() {
        return dbService.getAllRoute().stream()
                .map(Route::toString)
                .collect(Collectors.joining(","));
    }

    @GetMapping("/ping")
    public String ping() {
        LOGGER.info("Ping start");
        String response = restTemplate.getForObject("http://callme-service:8081/callme/ping", String.class);
        LOGGER.info("Calling: response={}", response);
        return "I'm caller-service Calling... " + response;
    }

    @GetMapping("/ping-with-random-error")
    public ResponseEntity<String> pingWithRandomError() {
        LOGGER.info("Ping with random error start");
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity("http://callme-service:8081/callme/ping-with-random-error", String.class);
        LOGGER.info("Calling: responseCode={}, response={}", responseEntity.getStatusCode(), responseEntity.getBody());
        return new ResponseEntity<>("I'm caller-serviceCalling... " + responseEntity.getBody(), responseEntity.getStatusCode());
    }

    @GetMapping("/ping-with-random-delay")
    public String pingWithRandomDelay() {
        LOGGER.info("Ping with random delay start ");
        String response = restTemplate.getForObject("http://callme-service:8081/callme/ping-with-random-delay", String.class);
        LOGGER.info("Calling: response={}", response);
        return "I'm caller-service Calling... " + response;
    }

}
