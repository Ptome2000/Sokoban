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
	//private String[] statusMessage;
	//private int moves;
	private Status statusManager;
	// ---------------------------	//

	// 		Gestão de Niveis		//
	//private File[] levels;
	//private int levelPointer;
	private Level levelManager;
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
					GameElement object = generatePixel(pixelLine.charAt(x), new Point2D(x, y));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Criar um Objecto to tipo Status com um ToString para atualizar a barra de status
	// Esta classe pode ser também utilizada para tratar do HighScore (Visto que terá os moves e o nome do jogador)
	private void generateStatus() {
		String header = statusManager.toString() + "- Energy: " + bobcat.getEnergy();
		gui.setStatusMessage(header);
	}

	//Substituir por fábrica
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

			if (gE[1] != null && gE[1].getName().equals("Bateria")) { consumeItem(gE[1]); moveBobcat(direction);}
			if (gE[2] == null && gE[1] == null && gE[0].getName().equals("Chao")) moveBobcat(direction);
			if (gE[2] == null && gE[0].getName().equals("Chao") && areMovable(gE[1])) {
				MovableElement crate = (MovableElement) gE[1];
				if (moveCrate(getPoint(direction, crate), crate)) moveBobcat(direction);
			}
		}
	}

	private void consumeItem(GameElement element) {
		if (element instanceof pt.iscte.poo.engine.ConsumableElement) {
			ConsumableElement object = (ConsumableElement) element;
			object.consumed();
		}

	}

	//Adicionar método para validar a instanceof do GameElement (Switch?) e chama a função devida dependendo de qual seja

	//Removes the given element from the Image interface and Image List 
	protected void removeElement(ImageTile element) throws IllegalArgumentException {
		gui.removeImage(element); tileList.remove(element);
	}

	private boolean areMovable(GameElement element) {
		return element instanceof pt.iscte.poo.engine.MovableElement;	
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

	public Empilhadora getBobcat() {
		return bobcat;
	}

	private boolean moveCrate(Point2D newPosition, MovableElement crate) {
		GameElement[] gE = getGameElementAtPosition(newPosition);
		//	!!!	Refazer esta linha para verificar se o tile é movable
		if (!crate.inBounds(newPosition) || gE[0] == null) return false; 
		if (gE[2] == null && gE[1] == null && gE[0].getName().equals("Chao") || gE[0].getName().equals("Alvo")) {
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

}
