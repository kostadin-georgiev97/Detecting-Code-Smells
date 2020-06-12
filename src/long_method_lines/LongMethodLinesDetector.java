package long_method_lines;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import app.Analyser;

public class LongMethodLinesDetector implements Analyser {
	
	private Map<String, CompilationUnit> compilationUnits;
	private List<LongMethodLines> longMethodsLines;
	private int methodLinesLimit;

	public LongMethodLinesDetector(Map<String, CompilationUnit> compilationUnits, int methodLinesLimit) {
		this.compilationUnits = compilationUnits;
		this.longMethodsLines = new ArrayList<LongMethodLines>();
		this.methodLinesLimit = methodLinesLimit;
	}
	
	public void findAllSmells() {
		// find all Long Method (Lines) smells and store them in longMethodsLines
		compilationUnits.forEach((path, cu) -> {
			VoidVisitor<List<LongMethodLines>> longMethodLinesVisitor = new LongMethodLinesVisitor(path, methodLinesLimit);
			longMethodLinesVisitor.visit(cu, longMethodsLines);
		});
	}

	public void printAllSmells() {
		System.out.println("Long Methods (based on number of lines):");
		try {
			longMethodsLines.forEach(smell -> {
				System.out.println("	" + smell.toString());
			});
		} catch (NullPointerException e) {
			System.out.println("	no code smells here");
		}
	}

}
