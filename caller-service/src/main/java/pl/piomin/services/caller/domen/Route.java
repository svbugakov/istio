package pl.piomin.services.caller.domen;

import java.time.LocalDate;

public class Route {
    private String id;
    private LocalDate date;
    private String inkassName;

    public Route(String id, LocalDate date, String inkassName) {
        this.id = id;
        this.date = date;
        this.inkassName = inkassName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInkassName() {
        return inkassName;
    }

    public void setInkassName(String inkassName) {
        this.inkassName = inkassName;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", inkassName='" + inkassName + '\'' +
                '}';
    }
}
