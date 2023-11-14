package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Point2D;

public class MovableElement extends GameElement implements Movable{

	public MovableElement(Point2D position) {
		super(position);
	}

	@Override
	public void move(Point2D newPosition) {
		setPosition(newPosition);
	}

	@Override
	public boolean inBounds(Point2D newPosition) {
		if (newPosition.getX()>=0 && newPosition.getX()<10 &&
				newPosition.getY()>=0 && newPosition.getY()<10) return true;
		return false;
	}
	
	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void setPosition(Point2D position) {
		super.position = position;
	}

}
