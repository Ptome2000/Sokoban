package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Empilhadora extends GameElement implements Movable{

	private String imageName;
	private int Energy;

	public Empilhadora(Point2D p2, String image) {
		super(p2);
		this.Energy = 100;
		setImageName(image);
	}

	@Override
	public int getLayer() {
		return 1;
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

	private void setFacing(Direction d) {
		switch (d) {
		case LEFT: setImageName("Empilhadora_L"); break;
		case RIGHT: setImageName("Empilhadora_R"); break;
		case UP: setImageName("Empilhadora_U"); break;
		case DOWN: setImageName("Empilhadora_D"); break;

		default: throw new IllegalArgumentException();
		}
	}

	public int move(Point2D newPosition, Direction d) {
		
		if (inBounds(newPosition)) {
			setPosition(newPosition);
			setFacing(d);
			Energy -= 1;
			return 1;
		}
		return 0;
	}

	@Override
	public boolean emtpyPosition(Vector2D vec) {
		
		//return vec.equals();
		return true;
	}

	@Override
	public boolean inBounds(Point2D newPosition) {
		if (newPosition.getX()>=0 && newPosition.getX()<10 &&
				newPosition.getY()>=0 && newPosition.getY()<10) return true;
		return false;
	}

}
