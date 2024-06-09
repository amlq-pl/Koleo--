package pl.tcs.oopproject.model.discount;

public class Discount implements GeneralDiscount {
    private final String name;
    private final double value;

    public Discount(String name, double value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String discount() {
        return name;
    }

    @Override
    public double value() {
        return value;
    }

    @Override
    public String toString() {
        return name + " " + String.format("%.2f", value) + "%";
    }
}
