package pt.iscte.poo.engine;

import pt.iscte.poo.elements.*;
import pt.iscte.poo.utils.Point2D;

public enum ElementCategory {

	WALKABLE_SLOT(new WalkableElement(new Point2D(0, 0), ""), null),
	CONSUMABLE_SLOT(new WalkableElement(new Point2D(0, 0), ""), new ConsumableElement(new Point2D(0,0), "")),
	PUSHABLE_SLOT(new WalkableElement(new Point2D(0, 0), ""), new MovableElement(new Point2D(0,0), ""));

	private GameElement[] elements;

	private ElementCategory(GameElement gameElement0, GameElement gameElement1) {
		this.elements = new GameElement[]{gameElement0, gameElement1};
	}

	public GameElement[] getElements() {
		return elements;
	}

	public boolean contains(GameElement[] element) {
		if (this == WALKABLE_SLOT) {
			WalkableElement walkableElement = (WalkableElement) element[0];
			return walkableElement.isWalkable();
		}
		if (elements[1].getClass().isInstance(element[1])) return true;
		return false;
	}

}
