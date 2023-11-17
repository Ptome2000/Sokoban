package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends WalkableElement {

	private boolean walkable;
	
	public Buraco(Point2D position, String name) {
		super(position, name);
		this.walkable = false;
	}
	
	@Override
	public boolean isWalkable() {
		return walkable;
	}
	
	public void closeHole() {
		this.walkable = true;
	}

}
