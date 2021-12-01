package pl.piomin.services.callme.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.piomin.services.callme.service.BagHandlerService;
import svb.inkass.data.bag.Bag;
import svb.inkass.data.bag.Currency;
import svb.inkass.dto.BagDTO;
import svb.inkass.utils.ConverterJson;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CallMeControllerIntegrationTest {

    @Autowired
    private CallmeController callmeController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BagHandlerService bagHandlerService;

    @Test
    public void whenhandleNewBag_thenShouldSuccessRegister() throws Exception {
        final Bag bag = new Bag("1", new BigDecimal(10000), "true bag", Currency.USD);
        final LocalDate now = LocalDate.now();
        final BagDTO bagDTO = new BagDTO(bag, "2021-12-01");

        mockMvc.perform(MockMvcRequestBuilders.post("/callme/handleBag")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ConverterJson.convertObjectToJsonString(bagDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().
                        string(org.hamcrest.Matchers.containsString("success handle bag")))
                .andExpect(MockMvcResultMatchers.content().
                        string(org.hamcrest.Matchers.containsString("descr='" + bag.getDescr() + "'")));
    }


}
