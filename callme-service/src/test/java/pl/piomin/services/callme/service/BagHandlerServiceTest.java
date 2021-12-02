package pl.piomin.services.callme.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import pl.piomin.services.callme.db.DB;
import svb.inkass.data.bag.Bag;
import svb.inkass.data.bag.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class BagHandlerServiceTest {

    @Spy
    @InjectMocks
    private BagHandlerService bagHandlerService;

    @Mock
    private DB db;
    @Mock
    private BalanceService balanceService;

    @Test
    public void check_whenExistsBag_thenShouldException() {
        final LocalDate ld = LocalDate.now();
        final Set<Bag> bagsHandled = new HashSet<>();
        final BigDecimal sum = new BigDecimal(100);
        final Bag bagAlreadyExists = new Bag("1", sum, "test1", Currency.USD);
        bagsHandled.add(bagAlreadyExists);
        Mockito.when(db.getDbRouteBags()).thenReturn(bagsHandled);

        final Exception exception = Assert.assertThrows(RuntimeException.class, () -> {
            bagHandlerService.handleBag(bagAlreadyExists, ld);
        });
        final String expectedMessage = String.format("Error, bag %s already handled!", bagAlreadyExists);
        final String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    public void shouldBagHandledSuccess() {
        final LocalDate ld = LocalDate.now();
        final Set<Bag> bagsHandled = new HashSet<>();
        final BigDecimal sum = new BigDecimal(100);
        final Bag bag = new Bag("1", sum, "test1", Currency.USD);
        Mockito.when(db.getDbRouteBags()).thenReturn(bagsHandled);

        bagHandlerService.handleBag(bag, ld);

        Mockito.verify(balanceService, Mockito.times(1))
                .increaseBalanceOnCurrencyAndDate(Mockito.eq(ld), Mockito.eq(bag.getCur()), Mockito.eq(sum));
    }


    @Test
    public void shouldBagHandledSuccess1() {
        final LocalDate ld = LocalDate.now();
        final Set<Bag> bagsHandled = new HashSet<>();
        final BigDecimal sum = new BigDecimal(100);
        final Bag bag = new Bag("1", sum, "test1", Currency.USD);
        final Bag bag2 = new Bag("2", sum, "test1", Currency.USD);
       // Mockito.when(db.getDbRouteBags()).thenReturn(bagsHandled);

        bagHandlerService.handleBag(bag, ld);

        Mockito.verify(balanceService, Mockito.times(1))
                .increaseBalanceOnCurrencyAndDate(Mockito.eq(ld), Mockito.eq(bag.getCur()), Mockito.eq(sum));
    }
}
