package pl.tcs.oopproject.model.carriage;

public enum CarriageType {
	SINGLE_COMPARTMENT_CARRIAGE {
		@Override
		public String toString() {
			return "bezprzedziałowy\n";
		}
	}, COMPARTMENT_CARRIAGE {
		@Override
		public String toString() {
			return "przedziałowy\n";
		}
	},
	
	WARS {
		@Override
		public String toString() {
			return "wars\n";
		}
	},
	
	SLEEPER {
		@Override
		public String toString() {
			return "sypialny\n";
		}
	}
}
