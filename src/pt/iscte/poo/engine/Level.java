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

	private File[] levels;
	private int levelPointer;
	private final String folder = super.getPath() + "/levels";


	private Scores scores;





	public Level() {
		loadLevels();
		this.levelPointer = 0;
	}

	//Ordenação na pasta importa?
	private void loadLevels() {
		if (!super.checkFolder(folder)) throw new IllegalStateException();
		File dir = new File(folder);
		this.levels = dir.listFiles();
	}

	public File[] getLevels() {
		return levels;
	}

	public int getLevelPointer() {
		return levelPointer;
	}

	public void levelCleared(GameEngine game) {
		game.getGUI().setMessage("Level " + levelPointer + " cleared");

		this.levelPointer++;
		constructLevel(game);
	}

	public void constructLevel(GameEngine game) {
		game.clearLevel();
		game.generateStatus();
		this.generateLevel(game);
		game.generateImages();
		game.updateStatus();
	}

	public void generateLevel(GameEngine game) {
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
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
