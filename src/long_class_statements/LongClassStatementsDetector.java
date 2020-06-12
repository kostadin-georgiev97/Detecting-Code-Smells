package long_class_statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import app.Analyser;

public class LongClassStatementsDetector implements Analyser {
	
	private Map<String, CompilationUnit> compilationUnits;
	private List<LongClassStatements> longClassesStatements;
	private int statementsLimit;

	public LongClassStatementsDetector(Map<String, CompilationUnit> compilationUnits, int statementsLimit) {
		this.compilationUnits = compilationUnits;
		this.longClassesStatements = new ArrayList<LongClassStatements>();
		this.statementsLimit = statementsLimit;
	}
	
	public void findAllSmells() {
		// find all Long Class (Statements) smells and store them in longClassesStatements
		compilationUnits.forEach((path, cu) -> {
			VoidVisitor<List<LongClassStatements>> longClassesStatementsVisitor = new LongClassStatementsVisitor(path, statementsLimit);
			longClassesStatementsVisitor.visit(cu, longClassesStatements);
		});
	}

	public void printAllSmells() {
		System.out.println("Long Class (based on number of statements):");
		try {
			longClassesStatements.forEach(smell -> {
				System.out.println("	" + smell.toString());
			});
		} catch (NullPointerException e) {
			System.out.println("	no code smells here");
		}
	}

}
