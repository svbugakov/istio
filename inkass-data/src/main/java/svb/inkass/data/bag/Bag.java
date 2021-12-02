package svb.inkass.data.bag;

import java.math.BigDecimal;
import java.util.Objects;

public class Bag implements Comparable<Bag> {
    private String id;
    private BigDecimal sum;
    private String descr;
    private Currency cur;

    public Bag(String id, BigDecimal sum, String descr, Currency cur) {
        this.id = id;
        this.sum = sum;
        this.descr = descr;
        this.cur = cur;
    }

    public Bag() {
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

    public Currency getCur() {
        return cur;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setCur(Currency cur) {
        this.cur = cur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bag bag = (Bag) o;
        return id.equals(bag.id) && sum.equals(bag.sum) && descr.equals(bag.descr) && cur == bag.cur;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sum, descr, cur);
    }

    @Override
    public String toString() {
        return "Bag{" +
                "id='" + id + '\'' +
                ", sum=" + sum +
                ", descr='" + descr + '\'' +
                ", cur=" + cur +
                '}';
    }

    @Override
    public int compareTo(Bag bag) {
        return bag.getId().compareTo(this.getId());
    }
}
