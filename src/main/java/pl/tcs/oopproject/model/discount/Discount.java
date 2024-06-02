package pl.tcs.oopproject.model.discount;

public class Discount implements DiscountInterface {
	private final String name;
	private final double value;
	
	public Discount(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
		@Override
		public String getDiscount() {
			return name;
		}
		
		@Override
		public double getValue() {
			return value;
		}
}
