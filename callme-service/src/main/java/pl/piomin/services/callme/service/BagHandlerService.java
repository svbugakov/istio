package pl.piomin.services.callme.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piomin.services.callme.db.DB;
import svb.inkass.data.bag.Bag;

import java.time.LocalDate;

@Service
public class BagHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BagHandlerService.class);
    private final DB db;
    private final BalanceService balanceService;

    @Autowired
    public BagHandlerService(
            final DB db,
            final BalanceService balanceService
    ) {
        this.db = db;
        this.balanceService = balanceService;
    }

    public void handleBag(final Bag bag, final LocalDate ld) {
        LOGGER.info("db routers:");
        db.getDbRouteBags().forEach(t -> LOGGER.info("h " + t.hashCode()));
        System.out.println("hash finish.................");
        db.getDbRouteBags().forEach(t -> LOGGER.info(t.toString()));

        if (db.getDbRouteBags().contains(bag)) {
            throw new RuntimeException(String.format("Error, bag %s already handled!", bag));
        }

        db.getDbRouteBags().add(bag);
        balanceService.increaseBalanceOnCurrencyAndDate(ld, bag.getCur(), bag.getSum());
    }
}
