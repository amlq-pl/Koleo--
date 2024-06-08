package pl.tcs.oopproject.model.carriage;

public enum CarriageType {
	SINGLE_COMPARTMENT_CARRIAGE {
		@Override
		public String toString() {
			return "wagon bezprzedziałowy";
		}
	}, COMPARTMENT_CARRIAGE {
		@Override
		public String toString() {
			return "wagon przedziałowy";
		}
	},
	
	WARS {
		@Override
		public String toString() {
			return "wars";
		}
	},
	
	SLEEPER {
		@Override
		public String toString() {
			return "wagon sypialny";
		}
	}
}
