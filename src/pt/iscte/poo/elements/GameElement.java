package pt.iscte.poo.elements;

import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.tileObjects.*;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{

	protected Point2D position;
	private String name;
	private static GameEngine game = GameEngine.getInstance();

	public GameElement(Point2D position, String name) {
		this.position = position;
		this.name = name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public String getName() {
		return name;
	}

	//Factory Function to obtain the corresponding Element by the given symbol
	public static GameElement generatePixel (char sym, Point2D point) {
		switch(sym) {

		case ' ': return new Chao(point, "Chao");
		case '=': return new Vazio(point, "Vazio");
		case '#': return new Parede(point, "Parede");
		case 'X': Alvo a = new Alvo(point, "Alvo"); game.getStatus().addTarget(a); return a;
		case 'E': Empilhadora bobcat = new Empilhadora(point, "Empilhadora_U"); game.setBobcat(bobcat); return bobcat;
		case 'C': Caixote box =  new Caixote(point, "Caixote");game.getStatus().addBox(box); return box;
		case 'B': return new Bateria(point, "Bateria");
		case 'O': return new Buraco(point, "Buraco");
		case 'P': return new Palete(point, "Palete");
		case 'M': return new Martelo(point, "Martelo");
		case '%': return new ParedeRachada(point, "ParedeRachada");
		case 'T': Teleporte t = new Teleporte(point, "Teleporte"); game.getStatus().addTeleport(t); return t;

		default: throw new IllegalArgumentException();
		}
	}
}
