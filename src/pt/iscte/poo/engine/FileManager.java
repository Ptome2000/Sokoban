package pt.iscte.poo.engine;

import java.io.File;

public abstract class FileManager {

	private final String path = System.getProperty("user.dir");

	public boolean checkFile(File file) {
		if (!file.exists()) return false;
		return true;
	}

	//Checks if folder exists with the given name
	public boolean checkFolder(String folder) {
		File dir = new File(folder);
		return dir.exists();
	}

	//Creates a folder with the given name
	public void createFolder(String folder) {
		File dir = new File(folder);
		dir.mkdirs();
	}

	public String getPath() {
		return path;
	}
	
	
}
