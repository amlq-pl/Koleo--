package pl.tcs.oopproject.model.carriage;

public enum CarriageClass {
	FIRST_CLASS {
		@Override
		public String toString() {
			return "Carriage Class: First Class";
		}
	}, SECOND_CLASS {
		@Override
		public String toString() {
			return "Carriage Class: Second Class";
		}
	};
	

}
