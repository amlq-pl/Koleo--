package pl.tcs.oopproject.viewmodel.discount;

public class PricePLN implements PriceInterface {
    private final Double price;
    
    public PricePLN(Double val) {
        this.price = val;
    }

    @Override
    public Double getPriceValue() {
        return price;
    }

    @Override
    public String toString() {
        String s = price.toString();
        
        return String.format("%.2f", price) + " zł";
    }
}
