package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public interface Movable {

	public int move(Point2D newPosition, Direction d);

	public boolean emtpyPosition(Vector2D vector);

	public boolean inBounds(Point2D newPosition);

}
