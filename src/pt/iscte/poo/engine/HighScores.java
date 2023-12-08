package pt.iscte.poo.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.Map.Entry;

public class HighScores extends FileManager {

	private final String folder = super.getPath() + "/scores";
	private final String playerName;
	private GameEngine game = GameEngine.getInstance();
	private TreeSet<Entry<String, Integer>> sortedScores;
	private Map<String, Integer> scores;
	private final MapComparator comp;

	public HighScores(String name) {
		if (!super.checkFolder(folder)) createFolder(folder);
		checkScoreFiles();
		this.playerName = name;
		this.comp = new MapComparator();
	}

	//Calculate the Scores corresponding to the completed Level
	public void calculateScores() {
		sortedScores = new TreeSet<Entry<String, Integer>>(comp);
		scores = readFile();
		if (scores == null) { 
			System.err.println("Due to an Error, this level's Scores will not be updated.");
			return;
		}
		sortedScores.addAll(scores.entrySet());
		if (sortedScores.size() > 3) { sortedScores.pollLast(); }
		writeScoreFile();
	}

	public String getPlayer() {
		return playerName;
	}

	//Checks if the score file exists to the corresponding level
	public void checkScoreFiles() {
		File[] levels = game.getLevel().getLevels();
		for (File l : levels) {
			File fileName = new File(folder + "/" + l.getName());
			if (!super.checkFile(fileName)) generateScoreFile(fileName);
		}
	}

	//Returns a map with the Players recorded in the Score file
	private Map<String, Integer> readFile() {
		Map<String, Integer> scoreMap = new HashMap<String, Integer>();
		try {
			Scanner scan = new Scanner(new File(folder + "/" + "level" + game.getLevel().getLevelPointer() + ".txt"));
			while(scan.hasNextLine()) {
				String[] line = scan.nextLine().split(";");
				scoreMap.put(line[0], Integer.parseInt(line[1]));
			}
		} catch (FileNotFoundException E1) {
			game.getGUI().setErrorMessage("Error while reading the file");
			return null;
		} catch(NumberFormatException E2) {
			System.err.println("Score values with incorrect format found");
			return null;
		}
		int newScore = 500 - game.getStatus().getMoves();
		if (scoreMap.get(playerName) == null) scoreMap.put(playerName, newScore); //Player does not exist
		else if (scoreMap.get(playerName) != null && newScore > scoreMap.get(playerName)) scoreMap.put(playerName, newScore); //Player Exists
		return scoreMap;
	}

	//Writes the new TOP 3 Players on the Score file
	private void writeScoreFile() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(new File(folder + "/" + "level" + game.getLevel().getLevelPointer() + ".txt"));
			for (Entry<String, Integer> score : sortedScores) {
				writer.print(score.getKey() + ";" + score.getValue() + "\n");

			}
			writer.close();
		} catch (FileNotFoundException error) {
			game.getGUI().setErrorMessage("Error while writing the file");
			return;
		}
	}


	//Generates a HighScore File
	private void generateScoreFile(File file) {
		try {
			PrintWriter writer = new PrintWriter(file);
			for (int i = 1; i <= 3; i++) {
				writer.println("Player" + i + ";" + "000");
			}
			writer.close();
		} 
		catch (FileNotFoundException err) {
			game.getGUI().setErrorMessage("Problem generating Score file " + file);
		}
	}
	
	class MapComparator implements Comparator<Entry<String, Integer>> {

		@Override
		public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
			int scoreValue = e2.getValue() - e1.getValue();
			if (scoreValue == 0) return e1.getKey().compareTo(e2.getKey()); //If Value is the same, then check the Name
			return scoreValue;
		}

	}

}


