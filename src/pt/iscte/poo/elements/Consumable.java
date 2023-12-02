package pt.iscte.poo.elements;

public interface Consumable {

	//Checks if the Element can be consumed
	public boolean isConsumable();
	
	//Consumes the object by removing it from the Game
	public void consumed();
	
}
