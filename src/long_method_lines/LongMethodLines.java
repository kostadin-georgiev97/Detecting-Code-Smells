package long_method_lines;

import com.github.javaparser.ast.body.MethodDeclaration;

public class LongMethodLines {
	
	private String path;
	private MethodDeclaration method;
	private int numLines;
	
	public LongMethodLines(String path, MethodDeclaration method, int numLines) {
		this.path = path;
		this.method = method;
		this.numLines = numLines;
	}
	
	public String toString() {
		return path + " - " + method.getNameAsString() + "() has " + numLines + " lines";
	}

}
