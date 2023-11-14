package pt.iscte.poo.engine;

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
	public void consumed(Empilhadora bobcat) {
		super.consumed(bobcat);
		bobcat.chargeEnergy();
	}

}
