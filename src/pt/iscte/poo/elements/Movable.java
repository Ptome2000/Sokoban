package pt.iscte.poo.elements;

import pt.iscte.poo.utils.Point2D;

public interface Movable {

	public void move(Point2D newPosition);

	public boolean inBounds(Point2D newPosition);

}
