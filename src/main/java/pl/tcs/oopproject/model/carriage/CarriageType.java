package pl.tcs.oopproject.model.carriage;

public enum CarriageType {
	SINGLE_COMPARTMENT_CARRIAGE {
		@Override
		public String toString() {
			return "Carriage Type: Single Compartment Carriage";
		}
	}, COMPARTMENT_CARRIAGE {
		@Override
		public String toString() {
			return "Carriage Type: Compartment Carriage";
		}
	},
	
	WARS {
		@Override
		public String toString() {
			return "Carriage Type: Wars";
		}
	},
	
	SLEEPER {
		@Override
		public String toString() {
			return "Carriage Type: Sleeper";
		}
	}
}
