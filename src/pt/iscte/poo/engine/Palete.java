package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Point2D;

public class Palete extends MovableElement {

	public Palete(Point2D position) {
		super(position);
	}
	
	@Override
	public String getName() {
		return "Palete";
	}

}
