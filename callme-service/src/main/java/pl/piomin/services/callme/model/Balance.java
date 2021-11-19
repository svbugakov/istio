package pl.piomin.services.callme.model;

import svb.inkass.data.bag.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Balance {
    private final LocalDate dt;
    private final Currency cur;
    private final BigDecimal bal;

    public Balance(LocalDate dt, Currency cur, BigDecimal bal) {
        this.dt = dt;
        this.cur = cur;
        this.bal = bal;
    }

    public LocalDate getDt() {
        return dt;
    }

    public Currency getCur() {
        return cur;
    }

    public BigDecimal getBal() {
        return bal;
    }
}
