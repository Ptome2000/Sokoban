package pt.iscte.poo.engine;

import javax.imageio.metadata.IIOInvalidTreeException;
import java.io.File;
import java.util.*;

public class Scores {

	private Map<String, Integer> topScores = new HashMap<>();
	private List<String> namesByscore;
	Iterator<Integer> iterator = topScores.values().iterator();
	private Integer score = 0;
	private HighScores highScores;


	public void generateHighScores(String filename, File[] levels){
		highScores = new HighScores(filename, levels);
	}

	public HighScores getHighScores(){
		return highScores;
	}

	public Scores(String filename, File[] levels){
		generateHighScores(filename, levels);
		topScores = highScores.getTopScores(filename);
		namesByscore = new ArrayList<>(topScores.keySet());
	}

	public void setScore(String name, Integer score){
		this.score = score;
		//validar
		addScoreToTop(name);
	}

	public void setScoreZero(){
		this.score = 0;
	}
	public Integer getScore(){
		return score;
	}

	public Map<String, Integer> getTopScores(){
		removeOneValueInferior();
		return topScores;
	}


	public void addScoreToTop(String name){
		topScores.put(name,score);
	}
	private void removeOneValueInferior() {
		while (iterator.hasNext()) {
			if (iterator.next() < score) {
				iterator.remove();
				return;
			}
		}
	}

	public List<String> getSortedNamesSortedByScores() {

		Collections.sort(namesByscore);
		return namesByscore;
	}
}
