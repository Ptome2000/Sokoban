package pt.iscte.poo.engine;

import java.util.ArrayList;
import java.util.List;
import pt.iscte.poo.tileObjects.*;
import pt.iscte.poo.utils.Point2D;

public class Status {

	private int moves;
	private int level;
	private GameEngine game = GameEngine.getInstance();

	private List<Alvo> targets;
	private List<Teleporte> teleports;
	private List<Caixote> caixotes;
	private int BoxNum;

	public Status(int level) {
		this.moves = 0;
		this.level = level;
		this.targets = new ArrayList<>();
		this.teleports = new ArrayList<>();
		this.caixotes = new ArrayList<>();
		this.BoxNum = 0;
	}
	
	public int getMoves() {
		return moves;
	}
	
	public void addBox(Caixote caixote) {
		BoxNum++;
		caixotes.add(caixote);
	}
	
	public void removeBox(Caixote caixote) {
		BoxNum--;
		caixotes.remove(caixote);
	}

	public void addTarget(Alvo alvo) {
		targets.add(alvo);
	}
	
	public void addTeleport(Teleporte portal) {
		teleports.add(portal);
	}
	

	
	public void addMove() {
		this.moves++;
	}

	private void isGameOver() {
		if (game.getBobcat() == null || game.getBobcat().getEnergy() == 0 
				|| targets.size() > BoxNum || validateBoxMoves() == false ) {
			game.getGUI().setMessage("GAME OVER");
			game.getLevel().constructLevel();
		}
	}

	private boolean isGameWon() {
		for (Alvo a :  targets) {
			if (!a.verifyTarget("Caixote")) return false;
		}
		return true;
	}
	
	public void verifyGame() {
		isGameOver();
		if (isGameWon()) game.getLevel().levelCleared();
	}
	
	public void validateTeleports() {
		if (teleports.size() > 0){
			if (teleports.size() != 2) {
				System.out.println("teleport size "+ teleports.size());
				throw new IllegalStateException("There should only be 2 teleports!");
			}
			teleports.get(0).setDestination(teleports.get(1));
			teleports.get(1).setDestination(teleports.get(0));
		}
	}

	@Override
	public String toString() {
		return "Level: " + level + " - Player: " + game.getScores().getPlayer() + " - Moves: " + moves;
	}

	public boolean validateBoxMoves() {
		boolean[] validMoves = new boolean[caixotes.size()];
		int i = 0;
		while(i < caixotes.size()) {
			for(Caixote caixote : caixotes) {
				boolean valid = game.hasMoves(caixote);
				validMoves[i] = valid;
				if(valid) {
					return true;
				}
				i++;
			}
		}
		return false;
	}





}
