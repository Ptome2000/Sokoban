package pt.iscte.poo.engine;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.elements.ConsumableElement;
import pt.iscte.poo.elements.GameElement;
import pt.iscte.poo.elements.MovableElement;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
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
	private String playerName;

	private Status statusManager;
	private Level levelManager;
	private HighScores scores;

	private GameEngine() {
		tileList = new ArrayList<>();   
	}

	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}
	
	public ImageMatrixGUI getGUI() {
		return gui;
	}
	
	public Level getLevel() {
		return levelManager;
	}
	
	public HighScores getScores() {
		return scores;
	}
	
	public List<ImageTile> getImages() {
		return tileList;
	}

	public void startGame() {

		gui = ImageMatrixGUI.getInstance();	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);
		gui.registerObserver(this);
		gui.go();      
		
		playerName = gui.askUser("Player's Name: ");
		scores = new HighScores(playerName);
		
		levelManager = new Level();
		generateStatus();
		levelManager.generateLevel();
		updateStatus();
		
		generateImages();

	}
	
	public boolean compObject(Point2D point, GameElement Element1) {
		GameElement[] Elements = getGameElementAtPosition((point));
		for (GameElement g : Elements) {
			if (g != null && g.getClass() == Element1.getClass()) return true;
		}
		return false;
	}

	// Esta classe pode ser também utilizada para tratar do HighScore (Visto que terá os moves e o nome do jogador)
	public void updateStatus() {
		String header = statusManager.toString() + " - Energy: " + bobcat.getEnergy();
		gui.setStatusMessage(header);
	}
	
	public void generateStatus() {
		statusManager = new Status(levelManager.getLevelPointer());
	}
	
	public void generateImages() {
		gui.addImages(tileList);
		gui.update();
	}
	
	public void clearLevel() {
		gui.clearImages();
		tileList.clear();
	}

	@Override
	public void update(Observed source) {
		int key = gui.keyPressed();
		if (Direction.isDirection(key)) {

			move(key);
			updateStatus();
			gui.update();
			statusManager.verifyGame();
		}
	}

	//Implementar esta validação na classe Empilhadora
	private void move(int key) {
		Direction direction = Direction.directionFor(key);
		if (bobcat.inBounds(getPoint(direction, bobcat))) {
			GameElement[] gE = getGameElementAtPosition(getPoint(direction, bobcat));

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
	
	public Status getStatus() {
		return statusManager;
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

}
