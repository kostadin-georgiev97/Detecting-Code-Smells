package temporary_fields;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class TemporaryField {
	
	private String path;
	private ClassOrInterfaceDeclaration clas;
	private String temporaryField;
	
	public TemporaryField(String path, ClassOrInterfaceDeclaration clas, String temporaryField) {
		this.path = path;
		this.clas = clas;
		this.temporaryField = temporaryField;
	}
	
	public String toString() {
		return path + " - " + clas.getNameAsString() + "(): " + temporaryField;
	}

}
