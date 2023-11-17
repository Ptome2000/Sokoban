package pt.iscte.poo.engine;

import pt.iscte.poo.elements.ConsumableElement;
import pt.iscte.poo.elements.GameElement;
import pt.iscte.poo.elements.MovableElement;
import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.utils.Point2D;

public enum ElementCategory {

	WALKABLE_SLOT(new WalkableElement(new Point2D(0, 0), ""), null, null),
	CONSUMABLE_SLOT(new WalkableElement(new Point2D(0, 0), ""), new ConsumableElement(new Point2D(0,0), ""), null),
	PUSHABLE_SLOT(new WalkableElement(new Point2D(0, 0), ""), new MovableElement(new Point2D(0,0), ""), null);

	private GameElement[] elements;

	private ElementCategory(GameElement gameElement0, GameElement gameElement1, GameElement gameElement2) {
		this.elements = new GameElement[]{gameElement0, gameElement1, gameElement2};
	}

	public GameElement[] getElements() {
		return elements;
	}

	public boolean contains(GameElement element) {
		for (GameElement categoryElement : elements) {
			if (categoryElement != null && categoryElement.getClass().isInstance(element)) {
				if (this == WALKABLE_SLOT) {
					WalkableElement walkableElement = (WalkableElement) element;
					return walkableElement.isWalkable();
				} else {
					return true;
				}
			}
		}
		return false;
	}

}
