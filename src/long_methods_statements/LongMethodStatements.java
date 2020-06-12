package long_methods_statements;

import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;

public class LongMethodStatements {
	
	private String path;
	private MethodDeclaration method;
	private List<Statement> totalStatements;
	
	public LongMethodStatements(String path, MethodDeclaration method, List<Statement> totalStatements) {
		this.path = path;
		this.method = method;
		this.totalStatements = totalStatements;
	}
	
	public String toString() {
		return path + " - " + method.getNameAsString() + "() has " + totalStatements.size() + " statements";
	}

}
