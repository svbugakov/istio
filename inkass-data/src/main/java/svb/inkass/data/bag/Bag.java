package svb.inkass.data.bag;

import java.math.BigDecimal;

public class Bag {
    final String id;
    final BigDecimal sum;
    final String descr;

    public Bag(String id, BigDecimal sum, String descr) {
        this.id = id;
        this.sum = sum;
        this.descr = descr;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public String getDescr() {
        return descr;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "id='" + id + '\'' +
                ", sum=" + sum +
                ", descr='" + descr + '\'' +
                '}';
    }
}
