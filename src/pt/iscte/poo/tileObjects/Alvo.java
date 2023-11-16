package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends WalkableElement {

	public Alvo(Point2D position) {
		super(position);
	}
	
	@Override
	public String getName() {
		return "Alvo";
	}
	
	public boolean verifyTarget() {
		return GameEngine.getInstance().compObject(this.getPosition(), new Caixote(new Point2D(0,0))); 
	}

}
