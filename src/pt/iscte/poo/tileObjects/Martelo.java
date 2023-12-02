package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.ConsumableElement;
import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class Martelo extends ConsumableElement {

	public Martelo(Point2D position, String name) {
		super(position, name);
	}
	
	@Override
	public void consumed() {
		super.consumed();
		GameEngine.getInstance().getBobcat().equipHammer(); //Consumes itself and Bobcat equips Hammer 
	}

}
