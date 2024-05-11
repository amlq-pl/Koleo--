package pl.tcs.oopproject.viewmodel.carriage;

public enum CarriageClassType {
	FIRST_CLASS {
		@Override
		void display() {
			System.out.println("Carriage Class: First Class");
		}
	},
	SECOND_CLASS {
		@Override
		void display() {
			System.out.println("Carriage Class: Second Class");
		}
	};
	
	abstract void display();
}
