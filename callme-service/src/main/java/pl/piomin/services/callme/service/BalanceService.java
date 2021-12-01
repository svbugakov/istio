package pl.piomin.services.callme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piomin.services.callme.db.DB;
import pl.piomin.services.callme.model.Balance;
import svb.inkass.data.bag.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Service
public class BalanceService {

    @Autowired
    private DB db;

    public Collection<Balance> getBalanceOnDate(final LocalDate date) {
        return db.getDbBalance().get(date);
    }

    public Balance getBalanceOnCurrencyAndDate(final LocalDate date, final Currency currency) {
        final Optional<Balance> balance = db.getDbBalance().get(date).stream().filter(t -> t.getCur() == currency).findFirst();
        if (balance.isEmpty()) {
            final String error = String.format("Balance on %s - %s absent!", date.toString(), currency);
            throw new RuntimeException(error);
        }
        return balance.get();
    }

    public void increaseBalanceOnCurrencyAndDate(final LocalDate date, final Currency currency, BigDecimal sum) {
        final Balance balance = getBalanceOnCurrencyAndDate(date, currency);
        balance.setBal(balance.getBal().add(sum));
        db.getDbBalance().put(date, balance);
    }
}
