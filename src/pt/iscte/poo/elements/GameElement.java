package pt.iscte.poo.elements;

import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.tileObjects.Alvo;
import pt.iscte.poo.tileObjects.Bateria;
import pt.iscte.poo.tileObjects.Buraco;
import pt.iscte.poo.tileObjects.Caixote;
import pt.iscte.poo.tileObjects.Chao;
import pt.iscte.poo.tileObjects.Empilhadora;
import pt.iscte.poo.tileObjects.Palete;
import pt.iscte.poo.tileObjects.Parede;
import pt.iscte.poo.tileObjects.Vazio;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{

	protected Point2D position;

	public GameElement(Point2D position) {
		this.position = position;
	}

	public static GameElement generatePixel (char sym, Point2D point) {
		switch(sym) {
		case ' ': return new Chao(point);
		case '=': return new Vazio(point);
		case '#': return new Parede(point);
		case 'X': return new Alvo(point);
		case 'E': Empilhadora bobcat = new Empilhadora(point, "Empilhadora_U"); GameEngine.getInstance().setBobcat(bobcat); return bobcat;
		case 'C': return new Caixote(point);
		case 'B': return new Bateria(point);
		case 'O': return new Buraco(point);
		case 'P': return new Palete(point);

		default: throw new IllegalArgumentException();
		}
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
		return "";
	}

	public void setPosition(Point2D position) {}
}
