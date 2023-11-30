package pt.iscte.poo.engine;

import java.io.*;
import java.util.*;

public class
HighScores extends FileManager{

	private final String folder = super.getPath() + "/scores";

	public HighScores(String filename, File[] levels) {
		if (!super.checkFolder(folder)) createFolder(folder);
		createFile(filename,levels);
	}

	private void createFile(String filename, File[] levels) {
			File fileName = new File(folder + "/" + filename);
			if (!super.checkFile(fileName)){
				generateFile(fileName);
			}

	}



	public Map<String, Integer> getTopScores(String levelFileName) {
		File f = new File(folder + "/" + levelFileName);
		return readFile(f);
	}
	private void writeNumTopPlayers(File file, Integer num, String name, Integer score) {
		try {

			PrintWriter writer = new PrintWriter(new FileWriter(file, false));
			for (int i = 1; i <= num; i++) {
				writer.println(i + ": " + name + " - " + score);
			}
			writer.close();
		}
		catch (IOException e) {
			System.err.println("Problem writing in file " + file);
		}
	}

	private void generateFile(File file) {
		writeNumTopPlayers(file,3, "Player", 000);
	}
	
	private Map<String, Integer> readFile(File file) {
		Map<String, Integer> topScores = new HashMap<>();
		try {
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				String[] line = reader.nextLine().split("-");
				String playername = line[0].replaceAll("\\s", "");
				Integer score = Integer.parseInt(line[1].replaceAll("\\s", ""));
				topScores.put(playername, score);
			}

		} catch (FileNotFoundException e) {
			System.err.println("Problem reading file " + file);
		}
		return topScores;
	}

	private void updateFile(File f, List<String> namesByscore, Map<String ,Integer> topScores){
		for (String name : namesByscore) {
			writeNumTopPlayers(f, 3, name, topScores.get(name));
		}
	}

	public void updateFileScore(String levelFileName, List<String> namesByscore, Map<String,Integer> topScores){
		File f = new File(folder + "/" + levelFileName);

		updateFile(f,namesByscore, topScores);
		System.out.println(toString());
	}

	@Override
	public String toString() {
		return "HighScores{" +
				"folder='" + folder + '\'' +
				'}';
	}
}
