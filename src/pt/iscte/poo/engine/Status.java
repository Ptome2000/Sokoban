package pt.iscte.poo.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.tileObjects.Alvo;
import pt.iscte.poo.tileObjects.Empilhadora;

public class Status {

	private int moves;
	private int level;
	private String levellFilename = "level0.txt";
	private Scores scores;
	private String playerName;

	private List<Alvo> targets;

	public Status() {
		this.moves = 0;
		this.level = 0;
		this.targets = new ArrayList<>();

	}
	public void setGetPlayerName(String name){
		this.playerName = name;
	}
	public String getPlayerName(){
		return this.playerName;
	}

	public String getLevellFilename(){
		return this.levellFilename= "level"+level+".txt";
	}
	public void genrateScores(File[] levels){
		this.scores = new Scores(getLevellFilename(), levels);
	}
	public Scores getScores(File[] levels) {
		if(scores==null)
			genrateScores(levels);
		return scores;
	}

	public void addTarget(Alvo alvo) {
		targets.add(alvo);
	}

	public void addMove() {
		this.moves++;
	}

	private void isGameOver(GameEngine game, Empilhadora bobCat, ImageMatrixGUI gui, Level l,File[] levels) {
		if (bobCat.getEnergy() == 0) {
			getScores(levels).setScoreZero();
			gui.setMessage("Bobcat energy ran out");
			l.constructLevel(game);
		}
	}

	private boolean isGameWon() {
		for (Alvo a :  targets) {
			if (!a.verifyTarget()) return false;
		}
		return true;
	}

	public void verifyGame(GameEngine game, Empilhadora bobCat, ImageMatrixGUI gui, Level l, File[] levels) {
		isGameOver(game,bobCat,gui,l, levels);
		if (isGameWon()) {
			genrateScores(levels);
			getScores(levels).setScore(getPlayerName(), moves);
			getScores(levels).getHighScores().updateFileScore(getLevellFilename(), getScores(levels).getSortedNamesSortedByScores(),getScores(levels).getTopScores());
			l.levelCleared(game);
		}
	}



	@Override
	public String toString() {
		return "Level: " + level + " - Player: " + playerName+ " - Moves: " + moves;
	}



}
