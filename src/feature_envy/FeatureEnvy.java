package feature_envy;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

public class FeatureEnvy {
	
	private String path;
	private MethodDeclaration method;
	private ClassOrInterfaceDeclaration clas;
	
	public FeatureEnvy(String path, MethodDeclaration method, ClassOrInterfaceDeclaration clas) {
		this.path = path;
		this.method = method;
		this.clas = clas;
	}
	
	public String toString() {
		return path + " - " + method.getNameAsString() + "() may belong in class " + clas.getNameAsString();
	}

}
