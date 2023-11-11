package pt.iscte.poo.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private ImageMatrixGUI gui;
	private Empilhadora bobcat;	
	private static GameEngine INSTANCE;
	private List<ImageTile> tileList;
	//Adicionar array de levels???
	private File[] levels;
	private int levelPointer;

	private GameEngine() {
		tileList = new ArrayList<>();   
	}

	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	public void startGame() {

		gui = ImageMatrixGUI.getInstance();	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);
		gui.registerObserver(this);
		gui.go();                              

		loadLevels();
		generateLevel(levelPointer); //Adiciona os objetos corretamente
		
		gui.addImages(tileList); //Problema a gerar?
		
	}

	public void loadLevels() {
		String execPath = System.getProperty("user.dir");
		File dir = new File(execPath + "/levels");
		this.levels = dir.listFiles();
	}

	public void generateLevel(int LevelID) {
		try {
			Scanner scan = new Scanner(levels[LevelID]);
			for (int y = 0; y != GRID_HEIGHT; y++) {
				String pixelLine = scan.nextLine();
				for (int x = 0; x != GRID_WIDTH; x++) {
					GameElement object = generatePixel(pixelLine.charAt(x), new Point2D(x, y));
					if (object.getLayer() == 1) {
						tileList.add(new Chao(new Point2D(x, y)));
						tileList.add(object);
					} else {
						tileList.add(object);
					}
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error Reading Game Level File " + e);
		}
	}


	private GameElement generatePixel (char sym, Point2D point) {
		switch(sym) {
		case ' ': return new Chao(point);
		case '=': return new Vazio(point);
		case '#': return new Parede(point);
		case 'X': return new Alvo(point);
		case 'E': return new Empilhadora(point, "Empilhadora_U");
		case 'C': return new Caixote(point);

		default: throw new IllegalArgumentException();
		}
	}

	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();
		if (Direction.isDirection(key)) bobcat.move(Direction.directionFor(key));


		gui.update();
	}

}
