package pl.tcs.oopproject.model.ticket;

public class Addition {
	private final String name;
	private final double cost;
	private boolean active = false;
	
	Addition(String name, double cost) {
		this.name = name;
		this.cost = cost;
	}
	
	Addition(Addition addition, boolean active) {
		this.name = addition.name;
		this.cost = addition.cost;
		this.active = active;
	}
	
}
