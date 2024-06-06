package pl.tcs.oopproject.model.discount;

public class PricePLN implements Price {
	private final Double price;
	
	public PricePLN(Double val) {
		this.price = val;
	}
	
	@Override
	public Double value() {
		return price;
	}
	
	@Override
	public String toString() {
		return String.format("%.2f", price) + " zł";
	}
}
