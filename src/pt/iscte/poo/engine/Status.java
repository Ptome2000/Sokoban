package pt.iscte.poo.engine;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.tileObjects.*;

public class Status {

	private int moves;
	private int level;
	private GameEngine game = GameEngine.getInstance();

	//Important Elements that require validation
	private List<Alvo> targets;
	private List<Teleporte> teleports;
	private List<Caixote> crates;

	public Status(int level) {
		this.moves = 0;
		this.level = level;
		this.targets = new ArrayList<>();
		this.teleports = new ArrayList<>();
		this.crates = new ArrayList<>();
	}

	public int getMoves() {
		return moves;
	}

	public void addBox(Caixote caixote) {
		crates.add(caixote);
	}

	public void removeBox(Caixote caixote) {
		crates.remove(caixote);
	}

	public void addTarget(Alvo alvo) {
		targets.add(alvo);
	}

	public void addTeleport(Teleporte portal) {
		teleports.add(portal);
	}
	
	public void setTeleports() {
		teleports.get(0).setDestination(teleports.get(1));
		teleports.get(1).setDestination(teleports.get(0));
	}

	public void addMove() {
		this.moves++;
	}

	//Verification of the BobCat's state to see if the user lost
	private void isGameOver() {
		if (game.getBobcat() == null || game.getBobcat().getEnergy() == 0 
				|| targets.size() > crates.size() || validateBoxMoves() == false ) {
			game.getGUI().setWarningMessage("Game Over!");
			if (game.getGUI().askUserYesNo("Restart Level?") == 0) {
				game.getLevel().constructLevel();
			} else System.exit(0);
		}
	}

	//Verification of Targets's State
	private boolean isGameWon() {
		for (Alvo a :  targets) {
			if (!a.verifyTarget("Caixote")) return false;
		}
		return true;
	}

	//Verification of Game progress state
	public void verifyGame() {
		if (!isGameWon()){	isGameOver();
		} else game.getLevel().levelCleared();
	}

	//Validation of number of Teleports and setting of their destinations
	public void validateTeleports() {
		if (teleports.size() != 2 && teleports.size() != 0) {
			game.getGUI().setErrorMessage("There should only be 2 teleports!");
			throw new IllegalStateException("There should only be 2 teleports!");
		}
	}
	
	//Validate if boxes have valid moves
		public boolean validateBoxMoves() {
			for (Caixote caixote : crates) {
				boolean valid = caixote.hasMovesOptimized();
				caixote.setHasMoves(valid);
				if(valid) return true;
			}
			return false;
		}

	@Override
	public String toString() {
		return "Level: " + level + " - Player: " + game.getScores().getPlayer() + " - Moves: " + moves;
	}

}
