package pt.iscte.poo.elements;

import pt.iscte.poo.utils.Point2D;

public interface Movable {

	//Moves the Element to the position
	public void move(Point2D newPosition);

	//Checks if the Element will be inside Level's boundaries when moving to the given position
	public boolean inBounds(Point2D newPosition);
	
	//Checks if the given position is WALKABLE and calls the move method
	public boolean canMove(Point2D newPosition, GameElement[] elements);
	
	//Validates the given Element on a switch and its specific action
	public void foundExtra(GameElement walkable);

}
