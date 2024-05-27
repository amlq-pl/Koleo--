package pl.tcs.oopproject.model.seat;

public enum SeatType {
	WINDOW {
		@Override
		void display() {
			System.out.println("Seat Type: Window");
		}
	},
	MIDDLE {
		@Override
		void display() {
			System.out.println("Seat Type: Middle");
		}
	},
	CORRIDOR {
		@Override
		void display() {
			System.out.println("Seat Type: Corridor");
		}
	},
	
	BERTH {
		@Override
		void display() {
			System.out.println("Seat Type: Berth");
		}
	};
	
	abstract void display();
}
