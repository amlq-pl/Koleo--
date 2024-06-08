package pl.tcs.oopproject.model.seat;

public enum SeatType {
	WINDOW {
		@Override
		public String toString() {
			return "okno ";
		}
	},
	MIDDLE {
		@Override
		public String toString() {
			return "środek ";
		}
	},
	CORRIDOR {
		@Override
		public String toString() {
			return "korytarz ";
		}
	},
	
	BERTH {
		@Override
		public String toString() {
			return "prycza ";
		}
	}
}
