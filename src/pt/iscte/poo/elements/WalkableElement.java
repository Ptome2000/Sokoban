package pt.iscte.poo.elements;

import pt.iscte.poo.utils.Point2D;

public class WalkableElement extends GameElement implements Walkable {

	public WalkableElement(Point2D position) {
		super(position);
	}
	
	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public boolean isWalkable() {
		return true;
	}

}
