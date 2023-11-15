package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends WalkableElement {
	
	private boolean filled;

	public Alvo(Point2D position) {
		super(position);
		this.filled = false;
	}
	
	@Override
	public String getName() {
		return "Alvo";
	}
	
	public boolean withCrate() {
		return filled;
	}
	
	public boolean verifyTarget() {
		
		//Verificar se existe caixa por cima
		return false;
	}

}
