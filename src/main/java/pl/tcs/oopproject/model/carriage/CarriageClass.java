package pl.tcs.oopproject.model.carriage;

public enum CarriageClass {
	FIRST_CLASS {
		@Override
		void display() {
			System.out.println("Carriage Class: First Class");
		}
	}, SECOND_CLASS {
		@Override
		void display() {
			System.out.println("Carriage Class: Second Class");
		}
	};
	
	abstract void display();
}
