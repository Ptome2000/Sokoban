package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends MovableElement {

	private String imageName;
	private int Energy;

	public Empilhadora(Point2D position, String image) {
		super(position);
		this.Energy = 100;
		setImageName(image);
	}

	public int getEnergy() {
		return Energy;
	}

	public void chargeEnergy() {
		Energy += 50;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public String getName() {
		return imageName;
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
