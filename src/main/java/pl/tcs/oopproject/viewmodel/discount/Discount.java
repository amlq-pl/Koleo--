package pl.tcs.oopproject.viewmodel.discount;

public enum Discount implements DiscountInterface {
	DISCOUNT1 {
		@Override
		public String getDiscount() {
			return null;
		}
		
		@Override
		public int getValue() {
			return 0;
		}
	}
}
