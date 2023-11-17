package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.MovableElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends MovableElement {
	
	private int Energy;
	private boolean hasHammer;

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

	private void setImageName(String imageName) {
		super.setName(imageName);
	}

	public void setFacing(Direction d) {
		switch (d) {
		case LEFT: setImageName("Empilhadora_L"); break;
		case RIGHT: setImageName("Empilhadora_R"); break;
		case UP: setImageName("Empilhadora_U"); break;
		case DOWN: setImageName("Empilhadora_D"); break;

		default: throw new IllegalArgumentException();
		}
	}

	@Override
	public void move(Point2D newPosition) {
		super.move(newPosition);
		Energy--;
	}

}
