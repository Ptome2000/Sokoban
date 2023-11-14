package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Point2D;

public class ConsumableElement extends GameElement implements Consumable{

	public ConsumableElement(Point2D position) {
		super(position);
	}
	
	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void consumed(Empilhadora bobcat) {
		GameEngine.getInstance().removeElement(this);
	}
	
}
