package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.MovableElement;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends MovableElement {

	public Caixote(Point2D position, String name) {
		super(position, name);
	}
	
	private boolean hasMoves = true;
	
	public void setHasMoves(boolean hasMoves){
		this.hasMoves = hasMoves;
	}
	
	public boolean getHasMoves(){
		return this.hasMoves;
	}
	
}
