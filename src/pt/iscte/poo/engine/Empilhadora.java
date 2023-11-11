package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement {

	private String imageName;

	public Empilhadora(Point2D p2, String image) {
		super(p2);
		this.imageName = image;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public String getName() {
		return imageName;
	}

	public void move(Direction d) {
		Point2D newPosition = getPosition().plus(d.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && 
				newPosition.getY()>=0 && newPosition.getY()<10 ){
			setPosition(newPosition);
		}
	}

}
