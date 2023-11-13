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

	//		Mensagem da barra		//
	private String[] statusMessage;
	private int moves;
	// ---------------------------	//

	// 		Gestão de Niveis		//
	private File[] levels;
	private int levelPointer;
	// ---------------------------	//

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
		generateLevel(levelPointer);

		gui.addImages(tileList);

	}

	public void loadLevels() {
		String execPath = System.getProperty("user.dir");
		File dir = new File(execPath + "/levels");
		this.levels = dir.listFiles();
	}

	public void generateLevel(int LevelID) {
		Scanner scan;
		try {
			scan = new Scanner(levels[LevelID]);
			for (int y = 0; y < GRID_HEIGHT; y++) {
				String pixelLine = scan.nextLine();
				for (int x = 0; x < GRID_WIDTH; x++) {
					GameElement object = generatePixel(pixelLine.charAt(x), new Point2D(x, y));
					if (object.getLayer() == 1) {
						tileList.add(new Chao(new Point2D(x,y)));		
						tileList.add(object);
					} else {
						tileList.add(object);
					}
				}
			}
			this.moves = 0;
			createStatusMessage(levels[LevelID], "");			//POR FAZER (NOME)
			gui.update();
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String generateTitle(File name) {
		String title = name.getName();
		title = title.replace(".txt", "");
		return title.replace("level", "Level ");
	}

	private void createStatusMessage(File fileName, String nome) {
		statusMessage = new String[4];
		statusMessage[0] = generateTitle(fileName);
		statusMessage[1] = "Player " + gui.askUser("Player's Name: ");
		updateStatusMessage();
	}

	private void updateStatusMessage() {
		statusMessage[2] = "Moves: " + moves;
		statusMessage[3] = "Energy: " + bobcat.getEnergy();
		generateStatus();
	}

	private void generateStatus() {
		String header = statusMessage[0] + " - " + statusMessage[1] + " - " + statusMessage[2] + " - " + statusMessage[3];
		gui.setStatusMessage(header);
	}


	private GameElement generatePixel (char sym, Point2D point) {
		switch(sym) {
		case ' ': return new Chao(point);
		case '=': return new Vazio(point);
		case '#': return new Parede(point);
		case 'X': return new Alvo(point);
		case 'E': return this.bobcat = new Empilhadora(point, "Empilhadora_U");
		case 'C': return new Caixote(point);
		case 'B': return new Bateria(point);

		default: throw new IllegalArgumentException();
		}
	}

	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();
		if (Direction.isDirection(key)) {
			Direction direction = Direction.directionFor(key);
			Point2D newPosition = bobcat.getPosition().plus(direction.asVector());

			GameElement gE = getGameElementAtPosition(newPosition, 3);
			if (gE == null) {
				gE = getGameElementAtPosition(newPosition, 1);
				
				if (gE != null && gE.getName().equals("Bateria")) { bobcat.chargeEnergy(); gui.removeImage(gE); }

				moves += bobcat.move(newPosition, direction);
				isGameOver(); //Verificar se perdeu
				updateStatusMessage();
				gui.update();
			}
		}
	}

	private GameElement getGameElementAtPosition(Point2D newPosition, int layer) {
		for (ImageTile tile : tileList) {
			GameElement gE = (GameElement) tile;
			if (gE.getPosition().equals(newPosition) && layer == gE.getLayer()) {
				return gE;
			}
		}
		return null;
	}

	private void isGameOver() {
		if (bobcat.getEnergy() == 0) {
			gui.setMessage("Bobcat energy ran out");
			System.exit(0);
		}
	}

}
