package pt.iscte.poo.engine;

import java.io.File;
import java.io.IOException;

public abstract class FileManager {

	private final String path = System.getProperty("user.dir");

	public boolean checkFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean checkFolder(String folder) {
		File dir = new File(folder);
		return dir.exists();
	}

	public void createFolder(String folder) {
		File dir = new File(folder);
		dir.mkdir();
	}

	public String getPath() {
		return path;
	}
}
