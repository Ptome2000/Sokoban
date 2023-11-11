package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

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

	public void move(Direction d) {
		Point2D newPosition = getPosition().plus(d.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && 
				newPosition.getY()>=0 && newPosition.getY()<10){
			setPosition(newPosition);
			setFacing(d);
			Energy -= 1;
		}
	}

	@Override
	public boolean headbuttingWall() {
		
		return true;							//POR FAZER
	}

}
