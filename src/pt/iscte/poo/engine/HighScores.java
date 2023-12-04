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

public class HighScores extends FileManager{

	private final String folder = super.getPath() + "/scores";
	private final String playerName;
	private GameEngine game = GameEngine.getInstance();
	private static TreeSet<Entry<String, Integer>> sorted;


	public HighScores(String name) {
		if (!super.checkFolder(folder)) createFolder(folder);
		createFiles();
		this.playerName = name;
	}

	public void calculateScores() {
		MapComparator comp = new MapComparator();
		sorted = new TreeSet<Entry<String, Integer>>(comp);
		sorted.addAll(readFile().entrySet());
		if (sorted.size() > 3) sorted.pollLast();
		writeScoreFile();
	}

	public String getPlayer() {
		return playerName;
	}

	private void createFiles() {
		File[] levels = game.getLevel().getLevels();
		for (File l : levels) {
			File fileName = new File(folder + "/" + l.getName());
			if (!super.checkFile(fileName)) super.generateFile(fileName);
		}
	}

	private Map<String, Integer> readFile() {
		Map<String, Integer> scoreMap = new HashMap<String, Integer>();
		try {
			Scanner scan = new Scanner(new File(folder + "/" + "level" + game.getLevel().getLevelPointer() + ".txt"));
			while(scan.hasNextLine()) {
				String[] line = scan.nextLine().split(";");
				scoreMap.put(line[0], Integer.parseInt(line[1]));
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error while reading the file");
		}
		int score = game.getBobcat().getEnergy() - game.getStatus().getMoves();
		scoreMap.put(playerName, score);
		return scoreMap;
	}

	private void writeScoreFile() {
		PrintWriter writer;
		try {
			System.out.println(folder + "/" + "level" + game.getLevel().getLevelPointer() + ".txt");
			writer = new PrintWriter(new File(folder + "/" + "level" + game.getLevel().getLevelPointer() + ".txt"));
			for (Entry<String, Integer> score : sorted) {
				writer.print(score.getKey() + ";" + score.getValue() + "\n");
				
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error while writing in the file");
		}
	}

}

class MapComparator implements Comparator<Entry<String, Integer>> {

	@Override
	public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
		return e2.getValue() - e1.getValue();
	}

}
