package pt.iscte.poo.tileObjects;

import pt.iscte.poo.elements.MovableElement;
import pt.iscte.poo.elements.WalkableElement;
import pt.iscte.poo.engine.GameEngine;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends WalkableElement {

	private GameEngine game = GameEngine.getInstance();

	public Buraco(Point2D position, String name) {
		super(position, name);
	}

	//When called, the element provided will be removed from the Game
	public void holeAction(MovableElement element) {
		if (verifyTarget("Palete")) {
			Palete palete = (Palete) element;
			game.addElement(palete.transformPalete());
		}
		else if (verifyTarget(GameEngine.getInstance().getBobcat().getName())) game.setBobcat(null);
		else game.getStatus().removeBox((Caixote) element);

		game.removeElement(element);
	}

}
