package pl.piomin.services.caller;

import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.json;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class CallerCallmeTest {

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode();
    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void callmeIntegration() {
        hoverflyRule.simulate(
            dsl(service("http://callme-service:8081")
                .get("/callme/ping")
                .willReturn(success().body("I'm callme-service ")))
        );
        String response = restTemplate.getForObject("http://localhost:" + port + "/caller/ping", String.class);
        Assert.assertEquals("I'm caller-service Calling... I'm callme-service ", response);
    }
}
