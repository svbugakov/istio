package pl.piomin.services.callme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/callme")
public class CallmeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallmeController.class);
    private static final String INSTANCE_ID = UUID.randomUUID().toString();
    private Random random = new Random();


    @RequestMapping(value = "/ping-test", method = RequestMethod.GET)
    @ResponseBody
    public String pingtest() {
        LOGGER.info("PingTest  start");
        return "I'm caller-me test v2...";
    }


    @GetMapping("/ping")
    public String ping() {
        LOGGER.info("Ping callme");
        return "I'm callme-service ";
    }

    @GetMapping("/ping-with-random-error")
    public ResponseEntity<String> pingWithRandomError() {
        int r = random.nextInt(100);
        if (r % 2 == 0) {
            LOGGER.info("Ping with random error:  httpCode={}",HttpStatus.GATEWAY_TIMEOUT);
            return new ResponseEntity<>("Surprise " + INSTANCE_ID , HttpStatus.GATEWAY_TIMEOUT);
        } else {
            LOGGER.info("Ping with random error: httpCode={}",
                     HttpStatus.OK);
            return new ResponseEntity<>("I'm callme-service" + INSTANCE_ID , HttpStatus.OK);
        }
    }

    @GetMapping("/ping-with-random-delay")
    public String pingWithRandomDelay() throws InterruptedException {
        int r = new Random().nextInt(3000);
        LOGGER.info("Ping with random delay1:{}",r);
        Thread.sleep(r);
        return "I'm callme-service " ;
    }

}
