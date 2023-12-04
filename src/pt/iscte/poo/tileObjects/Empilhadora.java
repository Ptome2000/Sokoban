package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.ConsumableElement;
import pt.iscte.poo.elements.GameElement;
import pt.iscte.poo.elements.MovableElement;
import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.engine.ElementCategory;
import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends MovableElement {

	private int Energy;
	private boolean hasHammer;
	private GameEngine game = GameEngine.getInstance();

	public Empilhadora(Point2D position, String image) {
		super(position, image);
		this.Energy = 100;
		this.hasHammer = false;
	}

	public int getEnergy() {
		return Energy;
	}

	public void chargeEnergy() {
		Energy += 50;
	}

	public void equipHammer() {
		hasHammer = true;
	}

	public void decreaseEnergy() {
		Energy--;
	}

	public boolean hasHammer() {
		return hasHammer;
	}

	//Sets the image corresponding to the direction
	public void setFacing(Direction d) {
		switch (d) {
		case LEFT: super.setName("Empilhadora_L"); break;
		case RIGHT: super.setName("Empilhadora_R"); break;
		case UP: super.setName("Empilhadora_U"); break;
		case DOWN: super.setName("Empilhadora_D"); break;

		default: throw new IllegalArgumentException();
		}
	}

	//Checks if the bobcat moved successfully
	public boolean actionTurn(int key) {
		Direction direction = Direction.directionFor(key);
		this.setFacing(direction);
		Point2D newPosition = game.getNextPoint(direction, this);
		GameElement[] gE = game.getGameElementAtPosition(newPosition);
		if (!this.canMove(newPosition, gE)) {
			return otherMovements(newPosition, gE, direction);
		}
		return true;
	}

	//Checks if the Elements in the given position are CONSUMABLE or PUSHABLE (SPECIFIC FOR BOBCAT)
	private boolean otherMovements(Point2D newPosition, GameElement[] gE, Direction direction) {
		if (ElementCategory.CONSUMABLE_SLOT.contains(gE) && this.consumeItem(gE[1], newPosition)) {
			this.move(newPosition);
			return true;
		}
		else if (ElementCategory.PUSHABLE_SLOT.contains(gE)) {
			if (pushObject(direction, gE[1])) {
				this.move(newPosition); this.decreaseEnergy();
				WalkableElement floor = (WalkableElement) gE[0];
				floor.action(this);
				return true;
			}
		}
		return false;
	}

	//Checks if the Element can be pushed by trying to move it
	private boolean pushObject(Direction direction, GameElement element) {
		MovableElement object = (MovableElement) element;
		Point2D newPosition = game.getNextPoint(direction, object);
		GameElement[] gE = game.getGameElementAtPosition(newPosition);
		return object.canMove(newPosition, gE);
	}

	//Consumes Element if it can be consumed
	private boolean consumeItem(GameElement element, Point2D position) {
		ConsumableElement object = (ConsumableElement) element;
		if (object.isConsumable()) { object.consumed(); return true; }
		return false;
	}

	@Override
	public void move(Point2D newPosition) {
		super.move(newPosition);
		Energy--;
	}

}
