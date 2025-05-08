package operations;

import interfaces.Port;

import java.util.ArrayList;
import java.util.List;

public class PortOperations {
	private final List<Port> portList = new ArrayList<>();

	public PortOperations() {
	}

	public void addPort(Port port) {
		portList.add(port);
	}

	public Port findPortById(int id) {
		for (Port port : portList) {
			if (port.getPortID() == id)
				return port;
		}
		return null;
	}

	public Port findPortByLatitudeAndLongitude(double latitude, double longitude) {
		for (Port port : portList) {
			if (port.getLatitude() == latitude && port.getLongitude() == longitude)
				return port;
		}
		return null;
	}
	
	public boolean removePortById(int id) { // To remove port from the port list
		return portList.removeIf(port -> port.getPortID() == id);
	}

	public List<Port> findAllPorts() {
		return this.portList;
	}
}