package pt.iscte.poo.elements;

import pt.iscte.poo.engine.ElementCategory;
import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.tileObjects.*;
import pt.iscte.poo.utils.Point2D;

public class MovableElement extends GameElement implements Movable{

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
				foundExtra(gE[0]);
				return true;
			}
		}
		return false;
	}

	@Override
	public void foundExtra(GameElement walkable) {
		String name = walkable.getName();
		switch (name) {
		case "Teleporte": Teleporte teleport = (Teleporte) walkable; teleport.teleportAction(this); break;
		case "Buraco": Buraco hole = (Buraco) walkable; hole.holeAction(this); break;
		
		}
	}

	@Override
	public int getLayer() {
		return 1;
	}

}
