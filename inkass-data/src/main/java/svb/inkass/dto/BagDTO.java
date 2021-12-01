package svb.inkass.dto;

import svb.inkass.data.bag.Bag;

public class BagDTO {
    private Bag bag;
    private String ld;

    public BagDTO(Bag bag, String ld) {
        this.bag = bag;
        this.ld = ld;
    }

    public BagDTO() {
    }


    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public String getLd() {
        return ld;
    }

    public void setLd(String ld) {
        this.ld = ld;
    }

    @Override
    public String toString() {
        return "BagDTO{" +
                "bag=" + bag +
                ", ld=" + ld +
                '}';
    }
}
