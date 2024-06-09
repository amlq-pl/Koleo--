package pl.tcs.oopproject.model.ticket;

public class Addition {
    private final String name;
    private final double cost;
    private boolean active = false;

    public Addition(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public Addition(Addition addition, boolean active) {
        this.name = addition.name;
        this.cost = addition.cost;
        this.active = active;
    }

    public boolean active() {
        return active;
    }

    public double cost() {
        return cost;
    }

    public String name() {
        return name;
    }
}
