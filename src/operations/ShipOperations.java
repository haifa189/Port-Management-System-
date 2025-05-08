package operations;

import interfaces.Ship;

import java.util.ArrayList;
import java.util.List;

public class ShipOperations {
	private final List<Ship> shipList = new ArrayList<>();

	public ShipOperations() {
	}

	public void addShip(Ship ship) {
		shipList.add(ship);
	}

	public Ship findShipById(int id) {
		if (!shipList.isEmpty()) {
			for (Ship ship : shipList) {
				if (ship.getID() == id)
					return ship;
			}
			return null;
		}
		return null;
	}

	public boolean removeShipById(int id) { // To remove ship from the ship list
		return shipList.removeIf(ship -> ship.getID() == id);
	}

	public List<Ship> findAll() {
		return shipList;
	}
}