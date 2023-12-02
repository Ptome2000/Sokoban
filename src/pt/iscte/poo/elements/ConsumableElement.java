package pt.iscte.poo.elements;

import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class ConsumableElement extends GameElement implements Consumable{

	public ConsumableElement(Point2D position, String name) {
		super(position, name);
	}
	
	@Override
	public boolean isConsumable() {
		return true;
	}
	
	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void consumed() {
		GameEngine.getInstance().removeElement(this);
	}
	
}
