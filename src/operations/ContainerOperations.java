package operations;

import container.Container;

import java.util.ArrayList;
import java.util.List;

public class ContainerOperations {
	private final List<Container> containerList = new ArrayList<>();

	public ContainerOperations() {
	}

	public void addContainer(Container container) {
		containerList.add(container);
	}

	public Container findContainerById(int id) {
		for (Container container : containerList) {
			if (id == container.getID())
				return container;
		}
		return null;
	}

	public boolean deleteContainerById(int id) { // To remove container from the container list
		return containerList.removeIf(container -> id == container.getID());
	}

	public List<Container> findAll() {
		return this.containerList;
	}
}