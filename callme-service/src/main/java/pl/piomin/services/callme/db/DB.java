package pl.piomin.services.callme.db;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.stereotype.Component;
import pl.piomin.services.callme.model.Balance;
import svb.inkass.data.bag.Bag;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Component
public class DB {
    private Set<Bag> dbRouteBags = new ConcurrentSkipListSet<>();
    private Multimap<LocalDate, Balance> dbBalance = ArrayListMultimap.create();


    public Set<Bag> getDbRouteBags() {
        return dbRouteBags;
    }

    public void setDbRouteBags(Set<Bag> dbRouteBags) {
        this.dbRouteBags = dbRouteBags;
    }

    public Multimap<LocalDate, Balance> getDbBalance() {
        return dbBalance;
    }

    public void setDbBalance(Multimap<LocalDate, Balance> dbBalance) {
        this.dbBalance = dbBalance;
    }
}
