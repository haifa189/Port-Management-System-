package container;

public class Container {
	private int ID;
	private int weight;
	private boolean isOnShip;
	private boolean isOnPort;

	public Container(int ID, int weight,  boolean isOnShip, boolean isOnPort) {
		this.ID = ID;
		this.weight = weight;
		this.isOnShip = isOnShip;
		this.isOnPort = isOnPort;
	}

	public Container() {
	}

	public boolean getOnShip() {
		return isOnShip;
	}

	public void setOnShip(boolean onShip) {
		isOnShip = onShip;
	}

	public boolean getOnPort() {
		return isOnPort;
	}

	public void setOnPort(Boolean onPort) {
		this.isOnPort = isOnPort;
	}

	public double consumption() {
		return 0;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public boolean equals(Object o) {
		// Check if the object being compared is the same as this object
		if (this == o)
			return true;
		// Check if the object being compared is null or of a different class
		if (o == null || getClass() != o.getClass())
			return false;
		// Cast the object being compared to a Container object
		Container container = (Container) o;
		// Compare the ID and weight of this container with the ID and weight of the other container
		return ID == container.ID && weight == container.weight;
	}

	public double fuelConsumption() {
		return 0;
	}

	@Override
	public String toString() {
		return "{ " + "\nID: " + ID + "\nWeight: " + weight + "\nisOnShip: " + isOnShip + "\nisOnPort: " + isOnPort;
	}
}