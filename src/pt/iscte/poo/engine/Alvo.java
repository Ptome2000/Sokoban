package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Point2D;

public class Alvo extends Chao {

	public Alvo(Point2D position) {
		super(position);
	}
	
	public String getName() {
		return "Alvo";
	}
	
	public boolean withCrate() {
		
		//Forma de validar se está preenchido?
		return false;
	}

}
