package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.MovableElement;
import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.utils.Point2D;

public class Palete extends MovableElement {

	public Palete(Point2D position, String name) {
		super(position, name);
	}
	
	//Creates a WALKABLE Palete Element from a MOVABLE Palete Element
	public PaleteChao transformPalete() {
		return new PaleteChao(this.getPosition(), "Palete");
	}
	
	public class PaleteChao extends WalkableElement {

		private PaleteChao(Point2D position, String name) {
			super(position, name);
		}

	}

}


