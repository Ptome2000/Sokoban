package pt.iscte.poo.engine;

import java.util.Map;
import java.util.TreeMap;

public class Scores {
	
	Map<String, Integer> level;

	public Scores() {
		this.level = new TreeMap<>();
	}
	
	public void add(String name, int value) {
		level.put(name, value);
	}
	

	//Funções da aula (útil para futuro)
	public String topWord(int minWordLength) {
		
		//Criar mapa
		int max = 0;
		String strMax = "";
	
		for (String str : level.keySet()) {
			if (level.get(str) > max) {
				max = level.get(str);
				strMax = str;
			}
	}
		return strMax + ": " + max;
	}
		
	
	
	
	
}
