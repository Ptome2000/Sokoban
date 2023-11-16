package pt.iscte.poo.engine;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.tileObjects.Alvo;

public class Status {

	private String name;
	private int moves;
	private int level;
	
	private List<Alvo> targets;
	
	public Status(int level) {
		this.name = setName();
		this.moves = 0;
		this.level = level;
		this.targets = new ArrayList<>();
	}
	
	public void addTarget(Alvo alvo) {
		targets.add(alvo);
	}
	
	private String setName() {
		return ImageMatrixGUI.getInstance().askUser("Player's Name: ");
	}
	
	public void addMove() {
		this.moves++;
	}
	
	private void isGameOver() {
		if (GameEngine.getInstance().getBobcat().getEnergy() == 0) {
			//gui.setMessage("Bobcat energy ran out");
			System.exit(0);
		}
	}
	
	public boolean isGameWon() {
		for (Alvo a :  targets) {
			if (!a.verifyTarget()) return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Level: " + level + " - Player: " + name + " - Moves: " + moves;
	}
	
	
	
}
