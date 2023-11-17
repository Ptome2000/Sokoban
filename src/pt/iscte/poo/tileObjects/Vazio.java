package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.utils.Point2D;

public class Vazio extends WalkableElement {

	public Vazio(Point2D position, String name) {
		super(position, name);
	}
	
	
	@Override
	public boolean isWalkable() {
		return false;
	}

}
