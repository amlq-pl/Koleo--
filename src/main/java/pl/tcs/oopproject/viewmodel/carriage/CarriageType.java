package pl.tcs.oopproject.viewmodel.carriage;

public enum CarriageType {
	SINGLE_COMPARTMENT_CARRIAGE {
		@Override
		void display() {
			System.out.println("Carriage Type: Single Compartment Carriage");
		}
	},
	COMPARTMENT_CARRIAGE {
		@Override
		void display() {
			System.out.println("Carriage Type: Compartment Carriage");
		}
	},
	
	WARS {
		@Override
		void display() {
			System.out.println("Carriage Type: Wars");
		}
	},
	
	SLEEPER {
		@Override
		void display() {
			System.out.println("Carriage Type: Sleeper");
		}
	};
	
	abstract void display();
}
