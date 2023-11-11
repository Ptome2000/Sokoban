package pt.iscte.poo.engine;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

abstract class GameElement implements ImageTile{
	
	private Point2D position;
	
	public GameElement(Point2D position) {
		this.setPosition(position);
	}
	
	@Override
	public int getLayer() {
		return 0;
	}
	
	@Override
	public Point2D getPosition() {
		return position;
	}
	
	@Override
	public String getName() {
		return "Vazio";
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
	
}
