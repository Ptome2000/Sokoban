package pt.iscte.poo.engine;

import java.util.ArrayList;
import java.util.List;
import pt.iscte.poo.tileObjects.Alvo;

public class Status {

	private int moves;
	private int level;
	private GameEngine game = GameEngine.getInstance();

	private List<Alvo> targets;

	public Status(int level) {
		this.moves = 0;
		this.level = level;
		this.targets = new ArrayList<>();
	}
	
	public int getMoves() {
		return moves;
	}

	public void addTarget(Alvo alvo) {
		targets.add(alvo);
	}

	public void addMove() {
		this.moves++;
	}

	private void isGameOver() {
		if (game.getBobcat().getEnergy() == 0) {
			game.getGUI().setMessage("Bobcat energy ran out");
			game.getLevel().constructLevel();
		}
	}

	private boolean isGameWon() {
		for (Alvo a :  targets) {
			if (!a.verifyTarget()) return false;
		}
		return true;
	}

	public void verifyGame() {
		isGameOver();
		if (isGameWon()) {
			game.getLevel().levelCleared();
		}

	}

	@Override
	public String toString() {
		return "Level: " + level + " - Player: " + game.getScores().getPlayer() + " - Moves: " + moves;
	}



}
