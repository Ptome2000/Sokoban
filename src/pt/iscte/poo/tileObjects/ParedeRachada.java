package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.ConsumableElement;
import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends ConsumableElement {

	public ParedeRachada(Point2D position, String name) {
		super(position, name);
	}
	
	@Override
	public boolean isConsumable() {
		return GameEngine.getInstance().getBobcat().hasHammer(); //Checks if bobcat has Hammer Equipped
	}

}
