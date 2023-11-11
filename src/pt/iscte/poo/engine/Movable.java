package pt.iscte.poo.engine;

import pt.iscte.poo.utils.Direction;

public interface Movable {

	public void move(Direction d);
	
	public boolean headbuttingWall();
	
}
