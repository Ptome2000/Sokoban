package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends WalkableElement {

	public Alvo(Point2D position, String name) {
		super(position, name);
	}
	
	public boolean verifyTarget() {
		return GameEngine.getInstance().compObject(this.getPosition(), new Caixote(new Point2D(0,0), "Caixote")); 
	}

}
