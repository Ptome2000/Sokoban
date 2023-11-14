package pt.iscte.poo.engine;

import java.io.File;

import pt.iscte.poo.gui.ImageMatrixGUI;

public class Level {

	private File[] levels;
	private int levelPointer;

	public Level() {
		loadLevels();
		this.levelPointer = 0;
	}

	private void loadLevels() {
		String execPath = System.getProperty("user.dir");
		File dir = new File(execPath + "/levels");
		this.levels = dir.listFiles();
	}

	public File[] getLevels() {
		return levels;
	}

	public int getLevelPointer() {
		return levelPointer;
	}

	public void levelCleared() {
		this.levelPointer++;
		ImageMatrixGUI.getInstance().setMessage("Level " + levelPointer + " cleared");
		GameEngine.getInstance().generateLevel();
	}

}
