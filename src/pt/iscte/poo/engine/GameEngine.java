package pt.iscte.poo.engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.elements.ConsumableElement;
import pt.iscte.poo.elements.GameElement;
import pt.iscte.poo.elements.MovableElement;
import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.tileObjects.Chao;
import pt.iscte.poo.tileObjects.Empilhadora;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private ImageMatrixGUI gui;
	private Empilhadora bobcat;	
	private static GameEngine INSTANCE;
	private List<ImageTile> tileList;

	private Status statusManager;
	private Level levelManager;

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

		levelManager = new Level(); 
		statusManager = new Status(levelManager.getLevelPointer());
		generateLevel();
		generateStatus();
		gui.update();

		gui.addImages(tileList);

	}

	public void generateLevel() {
		Scanner scan;
		try {
			scan = new Scanner(levelManager.getLevels()[levelManager.getLevelPointer()]);
			for (int y = 0; y < GRID_HEIGHT; y++) {
				String pixelLine = scan.nextLine();
				for (int x = 0; x < GRID_WIDTH; x++) {
					GameElement object = GameElement.generatePixel(pixelLine.charAt(x), new Point2D(x, y));
					if (object.getLayer() == 1) {
						tileList.add(new Chao(new Point2D(x,y)));		
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

	// Esta classe pode ser também utilizada para tratar do HighScore (Visto que terá os moves e o nome do jogador)
	private void generateStatus() {
		String header = statusManager.toString() + " - Energy: " + bobcat.getEnergy();
		gui.setStatusMessage(header);
	}

	@Override
	public void update(Observed source) {
		int key = gui.keyPressed();
		if (Direction.isDirection(key)) {

			move(key);
			isGameOver();
			generateStatus();
			gui.update();
		}
	}

	private void move(int key) {
		Direction direction = Direction.directionFor(key);
		if (bobcat.inBounds(getPoint(direction, bobcat))) {
			GameElement[] gE = getGameElementAtPosition(getPoint(direction, bobcat));

			//			if (gE[1] != null && gE[1].getName().equals("Bateria")) { consumeItem(gE[1]); moveBobcat(direction);}
			//			if (gE[2] == null && gE[1] == null && gE[0].getName().equals("Chao")) moveBobcat(direction);
			//			if (gE[2] == null && gE[0].getName().equals("Chao") && areMovable(gE[1])) {
			//				MovableElement crate = (MovableElement) gE[1];
			//				if (moveCrate(getPoint(direction, crate), crate)) moveBobcat(direction);
			//			}
			//		}

			if (ElementCategory.CONSUMABLE_SLOT.contains(gE[1])) consumeItem(gE[1], direction);

			if (gE[1] == null && ElementCategory.WALKABLE_SLOT.contains(gE[0])) moveBobcat(direction);

			if (ElementCategory.PUSHABLE_SLOT.contains(gE[1])) {
				MovableElement object = (MovableElement) gE[1];
				if (moveCrate(getPoint(direction, object), object)) { moveBobcat(direction); bobcat.decreaseEnergy(); }
			}
		}

	}

	//Consumes the Object given and may affect the bobcat
	private void consumeItem(GameElement element, Direction direction) {
		ConsumableElement object = (ConsumableElement) element;
		object.consumed();
		moveBobcat(direction);
	}

	//Removes the given element from the Image interface and Image List 
	public void removeElement(ImageTile element) throws IllegalArgumentException {
		gui.removeImage(element); tileList.remove(element);
	}

	//Gets the point 1 pixel in front of the passed object (Intended for MovableElements)
	private Point2D getPoint(Direction direction, GameElement object) {
		return object.getPosition().plus(direction.asVector());
	}

	private void moveBobcat(Direction direction) {
		bobcat.move(getPoint(direction, bobcat));
		statusManager.addMove();
		bobcat.setFacing(direction);
	}
	
	public void setBobcat(Empilhadora bobcat) {
		this.bobcat = bobcat;
	}

	public Empilhadora getBobcat() {
		return bobcat;
	}

	private boolean moveCrate(Point2D newPosition, MovableElement crate) {
		GameElement[] gE = getGameElementAtPosition(newPosition);
		if (crate.inBounds(newPosition) && gE[1] == null && ElementCategory.WALKABLE_SLOT.contains(gE[0])) {
			crate.move(newPosition);
			return true;
		} else {
			System.out.println("Cannot move crate to the next position"); //Mensagem debug
			return false;
		}
	}

	private GameElement[] getGameElementAtPosition(Point2D newPosition) {
		GameElement[] elemList = new GameElement[3]; //Size depending on the max layers
		for (ImageTile tile : tileList) {
			GameElement gE = (GameElement) tile;
			if (gE.getPosition().equals(newPosition)) {
				elemList[gE.getLayer()] = gE;
			}
		}
		return elemList;
	}

	private void isGameOver() {
		if (bobcat.getEnergy() == 0) {
			gui.setMessage("Bobcat energy ran out");
			System.exit(0);
		}
	}
	
	private void isLevelClear() {
		
	}

}
