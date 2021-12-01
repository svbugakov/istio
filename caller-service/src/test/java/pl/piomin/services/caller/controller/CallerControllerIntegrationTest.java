package pl.piomin.services.caller.controller;

import io.specto.hoverfly.junit.rule.HoverflyRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.piomin.services.caller.domen.Route;
import pl.piomin.services.caller.services.DBService;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CallerControllerIntegrationTest {

    @Autowired
    private CallerController callerController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DBService dbService;

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode();

    @Test
    public void whenAddRoute_RouteShouldRegister() throws Exception {
        final String idRoute = "test";
        final String nameInkass = "Ivanov";


        mockMvc.perform(MockMvcRequestBuilders.get("/caller/registerRoute")
                        .param("route", idRoute)
                        .param("inkassator", nameInkass)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().
                        string(org.hamcrest.Matchers.containsString("success registered")))
                .andExpect(MockMvcResultMatchers.content().
                        string(org.hamcrest.Matchers.containsString("inkassator=" + nameInkass)));
    }

    @Test
    public void whenCallGetAllRoute_thenShouldReturnRouters() throws Exception {
        final String idRoute = "test";
        final String nameInkass = "Ivanov";
        final List<Route> routes = new ArrayList<>();
        routes.add(new Route(idRoute, LocalDate.of(2021, Month.NOVEMBER, 18), nameInkass));
        Mockito.when(dbService.getAllRoute()).thenReturn(routes);

        mockMvc.perform(MockMvcRequestBuilders.get("/caller/getAllRoute"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().
                        string(org.hamcrest.Matchers.containsString("Route{id='test', date=2021-11-18, inkassName='Ivanov'}")));
    }

    @Test
    public void whenCallTakeBag_thenShouldBagSuccessTake() throws Exception {
        hoverflyRule.simulate(
                dsl(service("http://callme-service:8081")
                        .post("/callme/handleBag")
                        .anyBody()
                        .willReturn(success().body("success handle bag testID1")))
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/caller/takeBag")
                        .param("bagID", "testID1")
                        .param("sum", "10000")
                        .param("descr", "true bag")
                        .param("cur", "USD")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().
                        string(org.hamcrest.Matchers.containsString("success take bag [bagID=testID1, sum=10000, descr=true bag]")));

    }

}
