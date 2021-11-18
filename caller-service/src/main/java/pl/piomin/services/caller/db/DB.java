package pl.piomin.services.caller.db;

import pl.piomin.services.caller.domen.Route;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class DB {
    public static Map<String, Route> dbRoute = new ConcurrentSkipListMap<>();
}
