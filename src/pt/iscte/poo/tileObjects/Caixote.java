package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.MovableElement;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends MovableElement {

	public Caixote(Point2D position) {
		super(position);
	}

	@Override
	public String getName() {
		return "Caixote";
	}

}
