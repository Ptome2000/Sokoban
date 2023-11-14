package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Point2D;

public class Chao extends GameElement {

	public Chao(Point2D position) {
		super(position);
	}
	
	@Override
	public String getName() {
		return "Chao";
	}

}
