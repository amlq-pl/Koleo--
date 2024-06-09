package pl.tcs.oopproject.model.discount;

public class Voucher implements GeneralDiscount {
    private final String name;
    private final double value;

    public Voucher(String name, double value) {
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
