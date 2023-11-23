package pt.iscte.poo.engine;

import java.io.File;

public class HighScores extends FileManager{
	
	private File[] scores;
	private String playerName;

	public HighScores(String name) {
		loadScores();
		this.playerName = name;
	}
	
	public String getPlayer() {
		return playerName;
	}
	
	public void addScore() {
		
	}
	
	//Tornar Abstracto para utilizar também na classe Level
	private void loadScores() {
		String execPath = System.getProperty("user.dir");
		File dir = new File(execPath + "/scores");
		createFolder(dir);
		this.scores = dir.listFiles();
	}
	
	private void createFolder(File file) {
		file.mkdir();
	}

}
