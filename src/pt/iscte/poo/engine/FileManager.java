package pt.iscte.poo.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class FileManager {

	private final String path = System.getProperty("user.dir");

	public boolean checkFile(File file) {
		if (!file.exists()) return false;
		return true;
	}

	public boolean checkFolder(String folder) {
		File dir = new File(folder);
		return dir.exists();
	}

	public void createFolder(String folder) {
		File dir = new File(folder);
		dir.mkdirs();
	}

	public String getPath() {
		return path;
	}
	
	public void generateFile(File file) {
		try {
			PrintWriter writer = new PrintWriter(file);
			for (int i = 1; i <= 3; i++) {
				writer.println("Player" + i + ";" + "000");
			}
			writer.close();
		} 
		catch (FileNotFoundException e) {
			System.err.println("Problem writing in file " + file);
		}
	}
}
