package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.MovableElement;
import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.utils.Point2D;

public class Teleporte extends WalkableElement {
	
	private Teleporte destination; //Other teleport of the pair

	public Teleporte(Point2D position, String name) {
		super(position, name);
	}
	
	public void setDestination(Teleporte destination) {
		//Adicionar exceçao para verificar se o objeto é criado corretamente
		this.destination = destination;
	}
	
	//Transports the Element to the position of the other Teleport
	@Override
	public void action(MovableElement element) {
		if (!destination.teleportOccupied()) {
			element.move(destination.getPosition());
		}
	}
	
	//Checks if the teleport has an Element on top
	private boolean teleportOccupied() {
		return (verifyTarget("Caixote") || verifyTarget("Palete"));
	}

}
