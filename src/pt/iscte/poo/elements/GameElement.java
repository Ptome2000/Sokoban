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
	private String name;

	public GameElement(Point2D position, String name) {
		this.position = position;
		this.name = name;
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	public void setPosition(Point2D position) {}
	
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

	public static GameElement generatePixel (char sym, Point2D point) {
		switch(sym) {
		
		//Adicionar nome na creação
		case ' ': return new Chao(point, "Chao");
		case '=': return new Vazio(point, "Vazio");
		case '#': return new Parede(point, "Parede");
		case 'X': Alvo a = new Alvo(point, "Alvo"); GameEngine.getInstance().getStatus().addTarget(a); return a;
		case 'E': Empilhadora bobcat = new Empilhadora(point, "Empilhadora_U"); GameEngine.getInstance().setBobcat(bobcat); return bobcat;
		case 'C': return new Caixote(point, "Caixote");
		case 'B': return new Bateria(point, "Bateria");
		case 'O': return new Buraco(point, "Buraco");
		case 'P': return new Palete(point, "Palete");

		default: throw new IllegalArgumentException();
		}
	}
}
