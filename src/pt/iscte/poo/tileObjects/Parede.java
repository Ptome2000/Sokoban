package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Parede extends GameElement {

	public Parede(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public int getLayer() {
		return 2;
	}

}
