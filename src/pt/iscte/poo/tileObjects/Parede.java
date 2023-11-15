package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement {

	public Parede(Point2D p2) {
		super(p2);
	}
	
	@Override
	public String getName() {
		return "Parede";
	}
	
	@Override
	public int getLayer() {
		return 2;
	}

}
