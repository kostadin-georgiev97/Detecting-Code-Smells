package app;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaFileAccessor {
	
	private List<File> javaFiles;
	
	public JavaFileAccessor(File folder) {
		javaFiles = new ArrayList<File>();
		addJavaFiles(folder);
	}
	
	public List<File> getJavaFiles() {
		return javaFiles;
	}

	private void addJavaFiles(File folder) {
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				addJavaFiles(fileEntry);
			} else {
				if (getFileExtension(fileEntry).equals(".java")) {
					javaFiles.add(fileEntry);
				}
			}
		}
	}
	
	private String getFileExtension(File file) {
		String path = file.getPath();
		
		if (path.lastIndexOf(".") == -1) {
			return "";
		}
		
		return path.substring(path.lastIndexOf("."));
	}
	
}
