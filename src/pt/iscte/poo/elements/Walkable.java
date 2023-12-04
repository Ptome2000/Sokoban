package pt.iscte.poo.elements;

public interface Walkable {

	//Checks if the Element can be walked on by a MOVABLE
	public boolean isWalkable();
	
	//Checks if the given Element exists on top of the WALKABLE
	public boolean verifyTarget(String Element);
	
	//Action to be made if an Element is on top of the WALKABLE
	public void action(MovableElement element); 
	
}
