package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement {

	public Parede(Point2D p2) {
		super(p2);
	}
	
	@Override
	public String getName() {
		return "Parede";
	}

}
