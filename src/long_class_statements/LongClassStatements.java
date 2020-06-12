package long_class_statements;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.Statement;

public class LongClassStatements {
	
	private String path;
	private ClassOrInterfaceDeclaration clas;
	private List<Statement> totalStatements;
	
	public LongClassStatements(String path, ClassOrInterfaceDeclaration clas, List<Statement> totalStatements) {
		this.path = path;
		this.clas = clas;
		this.totalStatements = totalStatements;
	}
	
	public String toString() {
		return path + " - " + clas.getNameAsString() + " has " + totalStatements.size() + " statements";
	}

}
