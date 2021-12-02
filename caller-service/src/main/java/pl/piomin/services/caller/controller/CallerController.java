package pl.piomin.services.caller.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.piomin.services.caller.domen.Route;
import pl.piomin.services.caller.services.DBService;
import svb.inkass.data.bag.Bag;
import svb.inkass.data.bag.Currency;
import svb.inkass.dto.BagDTO;
import svb.inkass.utils.ConverterJson;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/caller")
public class CallerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallerController.class);

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

    @RequestMapping(value = "/takeBag", method = RequestMethod.POST)
    @ResponseBody
    public String takeBag(
            @RequestParam("bagID") final String bagID,
            @RequestParam("sum") final BigDecimal sum,
            @RequestParam("descr") final String descr,
            @RequestParam("cur") final Currency cur
    ) {
        LOGGER.info("Start takeBag, bagID={}, sum= {}, descr={}, cur={}", bagID, sum, descr, cur);
        final Bag bag = new Bag(bagID, sum, descr, cur);
        final BagDTO bagDTO = new BagDTO(bag, convertLocalDateToString(LocalDate.now()));
        final String url = "http://callme-service:8081/callme/handleBag";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(convertDtoToJson(bagDTO), headers);
        String answer = restTemplate.postForObject(url, entity, String.class);

        LOGGER.info("Finish takeBag, response={}", answer);
        return String.format("success take bag [bagID=%s, sum=%s, descr=%s]", bagID, sum, descr);
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
        String response = restTemplate.getForObject("http://callme-service:8081/callme/ping222", String.class);
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

    private String convertLocalDateToString(final LocalDate now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }

    private String convertDtoToJson(final BagDTO bagDTO) {
        String requestJson;
        try {
            requestJson = ConverterJson.convertObjectToJsonString(bagDTO);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(String.format("convertDtoToJson error bagDTO = [%s]", bagDTO), e);
        }

        return requestJson;
    }


}
