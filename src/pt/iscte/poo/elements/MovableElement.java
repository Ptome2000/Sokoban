package pt.iscte.poo.elements;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.engine.ElementCategory;
import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class MovableElement extends GameElement implements Movable{

	private GameEngine game = GameEngine.getInstance();

	public MovableElement(Point2D position, String name) {
		super(position, name);
	}

	@Override
	public void move(Point2D newPosition) {
		this.position = newPosition;
	}

	@Override
	public boolean inBounds(Point2D newPosition) {
		if (newPosition.getX()>=0 && newPosition.getX()<GameEngine.GRID_WIDTH &&
				newPosition.getY()>=0 && newPosition.getY()<GameEngine.GRID_HEIGHT) return true;
		return false;
	}

	@Override
	public boolean canMove(Point2D newPosition, GameElement[] gE) {
		if (this.inBounds(newPosition) && gE[1] == null) {
			if (ElementCategory.WALKABLE_SLOT.contains(gE)) {
				this.move(newPosition);
				WalkableElement floor = (WalkableElement) gE[0];
				floor.action(this);
				return true;
			}
		}
		return false;
	}

	//Check if box has valid moves by verifying the adjacent points
	public boolean hasMovesOptimized() {
		List<Point2D> possiblePositions = this.getPosition().getNeighbourhoodPoints();
		

		//If the position has an Obstacle, add the opposite result to the List (hasObstacle TRUE -> PossibleMove FALSE)
		boolean canMoveLeft = !hasObstacle(possiblePositions.get(0));	//LEFT
		boolean canMoveUp = !hasObstacle(possiblePositions.get(1));		//UP
		boolean canMoveRight = !hasObstacle(possiblePositions.get(2));	//RIGHT
		boolean canMoveDown = !hasObstacle(possiblePositions.get(3));	//DOWN

		/** //Removes the moves that are not possible (Inefficient for some levels)
		List<Boolean> PossibleMoves = new ArrayList<>();
		PossibleMoves.removeIf(m -> !m); 
		**/
		//if (canMoveLeft && canMoveRight || canMoveUp && canMoveDown) return true;
		//Can only move Horizontally or Vertically
		if((!canMoveUp  && (!canMoveLeft ||!canMoveRight))||(!canMoveDown && (!canMoveLeft || !canMoveRight)))
			return false;
		return true;
	}

	//Verifies if there is an obstacle in the given Position
	private boolean hasObstacle(Point2D position) {
		GameElement[] element = game.getGameElementsAtPosition(position);
		if (element[1] != null && element[1] != game.getBobcat()) return true;
		return false;
	}

	@Override
	public int getLayer() {
		return 1;
	}

}
