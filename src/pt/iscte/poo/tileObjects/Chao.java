package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.utils.Point2D;

public class Chao extends WalkableElement {

	public Chao(Point2D position) {
		super(position);
	}
	
	@Override
	public String getName() {
		return "Chao";
	}

}
