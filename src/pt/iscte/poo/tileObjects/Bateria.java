package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.ConsumableElement;
import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class Bateria extends ConsumableElement  {

	public Bateria(Point2D position) {
		super(position);
	}
	
	@Override
	public String getName() {
		return "Bateria";
	}
	
	@Override
	public void consumed() {
		super.consumed();
		GameEngine.getInstance().getBobcat().chargeEnergy();
	}

}
