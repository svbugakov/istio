package pl.piomin.services.callme.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.piomin.services.callme.db.DB;
import pl.piomin.services.callme.model.Balance;
import svb.inkass.data.bag.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
public class BalanceServiceTest {
    @Spy
    @InjectMocks
    private BalanceService balanceService;

    @Mock
    private DB db;

    @Test
    public void check_whenExistsBalanceOnDate_thenShouldGetIt() {
        final LocalDate ld = LocalDate.now();
        final BigDecimal sum = new BigDecimal(10);

        final Multimap<LocalDate, Balance> dbBalance = ArrayListMultimap.create();
        dbBalance.put(ld, new Balance(ld, Currency.USD, new BigDecimal(10)));

        Mockito.when(db.getDbBalance()).thenReturn(dbBalance);
        Optional<Balance> bal = balanceService.getBalanceOnDate(ld).stream().filter(t -> t.getDt() == ld).findFirst();
        if (bal.isEmpty()) {
            Assert.fail("Bal not found!");
        }
        Assert.assertEquals(bal.get().getBal(), sum);
    }

    @Test
    public void check_whenNotExistsBalanceOnDate_thenNotShouldGetIt() {
        final LocalDate ld = LocalDate.now();
        final LocalDate ld2 = LocalDate.now().minus(1L, ChronoUnit.DAYS);
        final BigDecimal sum = new BigDecimal(10);

        final Multimap<LocalDate, Balance> dbBalance = ArrayListMultimap.create();
        dbBalance.put(ld2, new Balance(ld2, Currency.USD, new BigDecimal(10)));

        Mockito.when(db.getDbBalance()).thenReturn(dbBalance);
        Collection<Balance> bal = balanceService.getBalanceOnDate(ld);

        if (!bal.isEmpty()) {
            Assert.fail("Bal must not found!");
        }
    }

}
