package long_methods_statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import app.Analyser;

public class LongMethodStatementsDetector implements Analyser {
	
	private Map<String, CompilationUnit> compilationUnits;
	private List<LongMethodStatements> longMethodsStatements;
	private int statementsLimit;

	public LongMethodStatementsDetector(Map<String, CompilationUnit> compilationUnits, int statementsLimit) {
		this.compilationUnits = compilationUnits;
		this.longMethodsStatements = new ArrayList<LongMethodStatements>();
		this.statementsLimit = statementsLimit;
	}
	
	public void findAllSmells() {
		// find all Long Method (Statements) smells and store them in longMethodsStatements
		compilationUnits.forEach((path, cu) -> {
			VoidVisitor<List<LongMethodStatements>> longMethodsStatementsVisitor = new LongMethodStatementsVisitor(path, statementsLimit);
			longMethodsStatementsVisitor.visit(cu, longMethodsStatements);
		});
	}

	public void printAllSmells() {
		System.out.println("Long Method (based on number of statements):");
		try {
			longMethodsStatements.forEach(smell -> {
				System.out.println("	" + smell.toString());
			});
		} catch (NullPointerException e) {
			System.out.println("	no code smells here");
		}
	}

}
