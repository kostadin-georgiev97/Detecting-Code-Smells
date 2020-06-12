package long_methods_statements;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LongMethodStatementsVisitor extends VoidVisitorAdapter<List<LongMethodStatements>> {
	
	private int statementsLimit;
	private String pathName;
	
	public LongMethodStatementsVisitor(String pathName, int statementsLimit) {
		this.pathName = pathName;
		this.statementsLimit = statementsLimit;
	}
	
	@Override
	public void visit(MethodDeclaration method,List<LongMethodStatements> longMethodsStatements) {
		super.visit(method, longMethodsStatements);
		
		List<Statement> statements = method.findAll(Statement.class);
		statements.removeIf(s -> (s instanceof BlockStmt));
		
		if (statements.size() > statementsLimit) {
			longMethodsStatements.add(new LongMethodStatements(pathName, method, statements));
		}
	}
	
}
