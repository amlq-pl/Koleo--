package pl.tcs.oopproject.viewmodel.tickets;

import java.time.Period;

public enum LongTermTicketType {
	ONE_DAY {
		@Override
		void display() {
			System.out.println("One day");
		}
		
		@Override
		Period period() {
			return Period.ofDays(1);
		}
		
		@Override
		int getCost() {
			return 0;
		}
	},
	THREE_DAYS {
		@Override
		void display() {
			System.out.println("Three days");
		}
		
		@Override
		Period period() {
			return Period.ofDays(3);
		}
		
		@Override
		int getCost() {
			return 0;
		}
	},
	WEEK {
		@Override
		void display() {
			System.out.println("Week");
		}
		
		@Override
		Period period() {
			return Period.ofDays(7);
		}
		
		@Override
		int getCost() {
			return 0;
		}
	},
	ONE_MONTH {
		@Override
		void display() {
			System.out.println("One month");
		}
		
		@Override
		Period period() {
			return Period.ofMonths(1);
		}
		
		@Override
		int getCost() {
			return 0;
		}
	},
	THREE_MONTHS {
		@Override
		void display() {
			System.out.println("Three months");
		}
		
		@Override
		Period period() {
			return Period.ofMonths(3);
		}
		
		@Override
		int getCost() {
			return 0;
		}
	},
	SIX_MONTHS {
		@Override
		void display() {
			System.out.println("Six months");
		}
		
		@Override
		Period period() {
			return Period.ofMonths(6);
		}
		
		@Override
		int getCost() {
			return 0;
		}
	},
	ONE_YEAR {
		@Override
		void display() {
			System.out.println("One year");
		}
		
		@Override
		Period period() {
			return Period.ofYears(1);
		}
		
		@Override
		int getCost() {
			return 0;
		}
	};
	
	abstract void display();
	abstract Period period();
	abstract int getCost(); //TO CODE
}
