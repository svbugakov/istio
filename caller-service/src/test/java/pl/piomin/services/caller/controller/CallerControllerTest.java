package pl.piomin.services.caller.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.piomin.services.caller.services.DBService;


@RunWith(MockitoJUnitRunner.class)
public class CallerControllerTest {

    @Spy
    @InjectMocks
    private CallerController callerController;

    @Mock
    private DBService dbService;

    @Test
    public void whenAddRoute_RouteShouldRegister() {
        final String idRoute = "test";
        final String nameInkass = "Ivanov";

        callerController.registerRoute(idRoute, nameInkass);

        Mockito.verify(dbService, Mockito.times(1))
                .addRoute(Mockito.eq(idRoute), Mockito.eq(nameInkass));
    }

    @Test
    public void whenGettingRoutes_ShouldCallDbService() {
        callerController.getAllRoute();

        Mockito.verify(dbService, Mockito.times(1))
                .getAllRoute();
    }

}
