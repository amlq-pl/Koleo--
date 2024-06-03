package pl.tcs.oopproject.model.seat;

public enum SeatType {
	WINDOW {
		@Override
		public String toString() {
			return "Seat Type: Window";
		}
	},
	MIDDLE {
		@Override
		public String toString() {
			return "Seat Type: Middle";
		}
	},
	CORRIDOR {
		@Override
		public String toString() {
			return "Seat Type: Corridor";
		}
	},
	
	BERTH {
		@Override
		public String toString() {
			return "Seat Type: Berth";
		}
	}
}
