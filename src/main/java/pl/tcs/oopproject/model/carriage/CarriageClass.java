package pl.tcs.oopproject.model.carriage;

public enum CarriageClass {
	FIRST_CLASS {
		@Override
		public String toString() {
			return "pierwsza klasa ";
		}
	}, SECOND_CLASS {
		@Override
		public String toString() {
			return "druga klasa ";
		}
	};
	

}
