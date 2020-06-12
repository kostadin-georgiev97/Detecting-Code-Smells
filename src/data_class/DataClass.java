package data_class;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class DataClass {
	
	private String path;
	private ClassOrInterfaceDeclaration clas;
	
	public DataClass(String path, ClassOrInterfaceDeclaration clas) {
		this.path = path;
		this.clas = clas;
	}
	
	public String toString() {
		return path + " - " + clas.getNameAsString() + " is likely to be a Data Class ";
	}

}
