package pl.piomin.services.caller.services;

import org.springframework.stereotype.Service;
import pl.piomin.services.caller.db.DB;
import pl.piomin.services.caller.domen.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DBService {
    public void addRoute(final String routeID, String inkassator) {
        Route route = new Route(routeID, LocalDate.now(), inkassator);
        DB.dbRoute.put(route.getId(), route);
    }

    public Route getRoute(final String idRoute) {
        return DB.dbRoute.get(idRoute);
    }

    public List<Route> getAllRoute() {
        return new ArrayList<>(DB.dbRoute.values());
    }
}
