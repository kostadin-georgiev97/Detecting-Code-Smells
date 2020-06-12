package long_class_lines;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class LongClassLines {
	
	private String path;
	private ClassOrInterfaceDeclaration clas;
	private int numLines;
	
	public LongClassLines(String path, ClassOrInterfaceDeclaration clas, int numLines) {
		this.path = path;
		this.clas = clas;
		this.numLines = numLines;
	}
	
	public String toString() {
		return path + " - " + clas.getNameAsString() + " has " + numLines + " lines";
	}

}
