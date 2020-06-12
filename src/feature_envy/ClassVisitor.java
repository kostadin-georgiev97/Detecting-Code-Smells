package feature_envy;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<List<ClassOrInterfaceDeclaration>> {
	
	@Override
	public void visit(ClassOrInterfaceDeclaration clas, List<ClassOrInterfaceDeclaration> allClasses) {
		
		super.visit(clas, allClasses);
		
		allClasses.add(clas);
		
	}

}
