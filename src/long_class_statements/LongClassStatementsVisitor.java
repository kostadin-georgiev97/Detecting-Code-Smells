package long_class_statements;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LongClassStatementsVisitor extends VoidVisitorAdapter<List<LongClassStatements>> {

	private int statementsLimit;
	private String pathName;
	
	public LongClassStatementsVisitor(String pathName, int statementsLimit) {
		this.pathName = pathName;
		this.statementsLimit = statementsLimit;
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration clas, List<LongClassStatements> classNumStatements) {
		super.visit(clas, classNumStatements);
		
		List<Statement> statements = new ArrayList<Statement>();
		
		clas.getConstructors().forEach(constructor -> {
			List<Statement> constructorStatements = constructor.findAll(Statement.class);
			constructorStatements.removeIf(s -> (s instanceof BlockStmt));
			statements.addAll(constructorStatements);
		});
		
		clas.getMethods().forEach(method -> {
			List<Statement> methodStatements = method.findAll(Statement.class);
			methodStatements.removeIf(s -> (s instanceof BlockStmt));
			statements.addAll(methodStatements);
		});
		
		
		
		if (statements.size() > statementsLimit) {
			classNumStatements.add(new LongClassStatements(pathName, clas, statements));
		}
	}

}
