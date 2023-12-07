package pt.iscte.poo.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.elements.GameElement;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.tileObjects.Chao;
import pt.iscte.poo.utils.Point2D;

public class Level extends FileManager{

	private final File[] levels;
	private int levelPointer;
	private GameEngine game = GameEngine.getInstance();
	private final String folder = super.getPath() + "/levels";

	public Level() {
		this.levels = loadLevels();
		this.levelPointer = 0;
	}

	//Loads Levels into an Array of Files
	private File[] loadLevels() {
		if (!super.checkFolder(folder)) {
			game.getGUI().setErrorMessage("Levels Folder not found!");
			throw new IllegalStateException();
		}
		File dir = new File(folder);
		return dir.listFiles();
	}

	public File[] getLevels() {
		return levels;
	}

	public int getLevelPointer() {
		return levelPointer;
	}

	//Process to be done when a level is cleared (Scores calculated, and new Level Generated)
	public void levelCleared() {
		if (this.levelPointer >= levels.length - 1){ //Last Level Cleared?
			game.getScores().calculateScores();
			game.getGUI().setMessage("All Levels completed! Congrats!");
			if (game.getGUI().askUserYesNo("Restart Game?") == 0) {
				this.levelPointer = 0; 
				constructLevel();
			} else System.exit(0); //Terminates the Game
		} else if (this.levelPointer < levels.length){
			game.getScores().calculateScores();
			game.getGUI().setMessage("Level " + levelPointer + " cleared");
			this.levelPointer++; 
			constructLevel();
		}
	}

	public void constructLevel() {
		game.clearLevel();
		game.generateStatus();
		generateLevel();
		game.generateImages();
		game.updateStatus();
	}

	//Generates Level based on a File
	public void generateLevel() {
		Scanner scan;
		try {
			scan = new Scanner(levels[levelPointer]);
			List<ImageTile> tileList = game.getImages();
			for (int y = 0; y < GameEngine.GRID_HEIGHT; y++) {
				String pixelLine = scan.nextLine();
				for (int x = 0; x < GameEngine.GRID_WIDTH; x++) {
					GameElement object = GameElement.generatePixel(pixelLine.charAt(x), new Point2D(x, y));
					if (object.getLayer() == 1) {
						tileList.add(new Chao(new Point2D(x,y), "Chao"));		
						tileList.add(object);
					} else {
						tileList.add(object);
					}
				}
			}
			game.getStatus().validateLevel();
			scan.close();
		} catch (FileNotFoundException E1) {
			game.getGUI().setErrorMessage("Level " + levelPointer + " not found!");
			E1.printStackTrace();
		} catch (NullPointerException E2)  {	game.getGUI().setErrorMessage("Item is missing!");
			if (game.getGUI().askUserYesNo("Do you want to skip to the next level?") == 0) {	levelPointer++; constructLevel(); return; }
			System.exit(1);
		}

	}

}