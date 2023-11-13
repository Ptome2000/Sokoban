package pt.iscte.poo.engine;

import java.util.Objects;

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
	public int hashCode() {
		return Objects.hash(position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameElement other = (GameElement) obj;
		return Objects.equals(position, other.position);
	}

	@Override
	public String getName() {
		return "";
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
	
}
