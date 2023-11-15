package pt.iscte.poo.engine;

import pt.iscte.poo.gui.ImageMatrixGUI;

public class Status {

	private String name;
	private int moves;
	private int level;
	
	public Status(int level) {
		this.name = setName();
		this.moves = 0;
		this.level = level;
	}
	
	private String setName() {
		return ImageMatrixGUI.getInstance().askUser("Player's Name: ");
	}
	
	public void addMove() {
		this.moves++;
	}

	@Override
	public String toString() {
		return "Level: " + level + " - Player: " + name + " - Moves: " + moves;
	}
	
	
	
}
