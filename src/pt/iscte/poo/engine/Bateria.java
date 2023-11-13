package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement implements Consumable {

	public Bateria(Point2D position) {
		super(position);
	}
	
	@Override
	public String getName() {
		return "Bateria";
	}
	
	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void consumed() {
		
	}

}
