package pt.iscte.poo.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HighScores extends FileManager{

	private final String folder = super.getPath() + "/scores";
	private String playerName;
	private GameEngine game = GameEngine.getInstance();
	private Map<String, Integer> scores = new HashMap<>();

	public HighScores(String name) {
		if (!super.checkFolder(folder)) createFolder(folder);
		createFiles();
		this.playerName = name;
	}

	public String getPlayer() {
		return playerName;
	}
	
	private void createFiles() {
		File[] levels = game.getLevel().getLevels();
		for (File l : levels) {
			File fileName = new File(folder + "/" + l.getName());
			if (!super.checkFile(fileName)) generateFile(fileName);
			readFile(l);
		}
	}
	
	private void generateFile(File file) {
		try {
			PrintWriter writer = new PrintWriter(file);
			for (int i = 1; i <= 3; i++) {
				writer.println("Player" + i + " - " + "000");
			}
			writer.close();
		} 
		catch (FileNotFoundException e) {
			System.err.println("Problem writing in file " + file);
		}
	}
	
	private void readFile(File file) {
		
	}


}
