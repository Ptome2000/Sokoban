package pt.iscte.poo.engine;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.elements.*;
import pt.iscte.poo.gui.*;
import pt.iscte.poo.observer.*;
import pt.iscte.poo.tileObjects.*;
import pt.iscte.poo.utils.*;

public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private ImageMatrixGUI gui;
	private Empilhadora bobcat;	
	private static GameEngine INSTANCE;
	private List<ImageTile> tileList;

	private Status statusManager;
	private Level levelManager;
	private HighScores scoresManager;

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
		return scoresManager;
	}

	public List<ImageTile> getImages() {
		return tileList;
	}

	public Status getStatus() {
		return statusManager;
	}

	public void setBobcat(Empilhadora bobcat) {
		this.bobcat = bobcat;
	}

	public Empilhadora getBobcat() {
		return bobcat;
	}

	public void startGame() {

		gui = ImageMatrixGUI.getInstance();	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);
		gui.registerObserver(this);
		gui.go();      

		levelManager = new Level();
		scoresManager = new HighScores(gui.askUser("Player's Name: "));

		generateStatus();
		levelManager.generateLevel();
		updateStatus();
		generateImages();

	}

	//Updates Window Header with current level stats
	public void updateStatus() {
		try {
			String header = statusManager.toString() + " - Energy: " + bobcat.getEnergy();
			gui.setStatusMessage(header);
		} catch (NullPointerException E) {
			gui.setErrorMessage("Bobcat is missing!");
			throw new NullPointerException("Bobcat is missing!");
		}
	}

	//Generates the current level Status to be displayed on the Window Header
	public void generateStatus() {
		statusManager = new Status(levelManager.getLevelPointer());
	}

	//Generates the Images on the Image Matrix
	public void generateImages() {
		gui.addImages(tileList); gui.update();
	}

	//Clears both the Image Interface and the Elements List
	public void clearLevel() {
		gui.clearImages(); tileList.clear();
	}

	//Verifies if entered key is valid and proceeds to validate the movement from the bobcat class
	@Override
	public void update(Observed source) {
		int key = gui.keyPressed();
		if (Direction.isDirection(key)) {
			//If the actionTurn returns true, adds a move to the count
			if (bobcat.actionTurn(key)) { statusManager.addMove(); }
			gui.update();
			statusManager.verifyGame();
			updateStatus();	
		}
	}

	//Removes the given element from the Image interface and Image List 
	public void removeElement(ImageTile element) throws IllegalArgumentException {
		gui.removeImage(element); tileList.remove(element); gui.update();
	}

	//Adds the given element to the Image interface and Image List
	public void addElement(ImageTile element) {
		gui.addImage(element); tileList.add(element); gui.update();
	}

	//Gets the point in front of the passed object (Intended for MovableElements)
	public Point2D getNextPoint(Direction direction, GameElement object) {
		return object.getPosition().plus(direction.asVector());
	}

	//Compares the Elements's names in the given position, to the given Name
	public boolean compObject(Point2D position, String Element) {
		GameElement[] Elements = getGameElementsAtPosition(position);
		for (GameElement g : Elements) {
			if (g != null && g.getName() == Element) return true;
		}
		return false;
	}

	//Retrieves all of the Elements from the given position and inserts them in order of their layer
	public GameElement[] getGameElementsAtPosition(Point2D newPosition) {
		GameElement[] elemList = new GameElement[2]; //Size depending on the max layers
		for (ImageTile tile : tileList) {
			GameElement gE = (GameElement) tile;
			if (gE.getPosition().equals(newPosition)) {
				elemList[gE.getLayer()] = gE;
			}
		}
		return elemList;
	}

}
