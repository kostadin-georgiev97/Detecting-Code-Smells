package long_parameter_list;

import com.github.javaparser.ast.body.MethodDeclaration;

public class LongParameterList {
	
	private String path;
	private MethodDeclaration method;
	private int totalParameters;
	
	public LongParameterList(String path, MethodDeclaration method, int totalParameters) {
		this.path = path;
		this.method = method;
		this.totalParameters = totalParameters;
	}
	
	public String toString() {
		return path + " - " + method.getDeclarationAsString() + " has " + totalParameters + " parameters";
	}

}
