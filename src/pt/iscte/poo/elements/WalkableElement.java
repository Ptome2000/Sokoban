package pt.iscte.poo.elements;

import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class WalkableElement extends GameElement implements Walkable {

	public WalkableElement(Point2D position, String name) {
		super(position, name);
	}
	
	@Override
	public int getLayer() {
		return 0;
	}
	
	public boolean isWalkable() {
		return true;
	}
	
	public boolean verifyTarget(GameElement Element) {
		return GameEngine.getInstance().compObject(this.getPosition(), Element); 
	}

}
