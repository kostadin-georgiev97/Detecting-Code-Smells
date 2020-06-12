package long_class_lines;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import app.Analyser;

public class LongClassLinesDetector implements Analyser {
	
	private Map<String, CompilationUnit> compilationUnits;
	private List<LongClassLines> longClassesLines;
	private int classLinesLimit;

	public LongClassLinesDetector(Map<String, CompilationUnit> compilationUnits, int classLinesLimit) {
		this.compilationUnits = compilationUnits;
		this.longClassesLines = new ArrayList<LongClassLines>();
		this.classLinesLimit = classLinesLimit;
	}
	
	public void findAllSmells() {
		// find all Long Class (Lines) smells and store them in longClassesLines
		compilationUnits.forEach((path, cu) -> {
			VoidVisitor<List<LongClassLines>> longClassLinesVisitor = new LongClassLinesVisitor(path, classLinesLimit);
			longClassLinesVisitor.visit(cu, longClassesLines);
		});
	}

	public void printAllSmells() {
		System.out.println("Long Class (based on number of lines):");
		try {
			longClassesLines.forEach(smell -> {
				System.out.println("	" + smell.toString());
			});
		} catch (NullPointerException e) {
			System.out.println("	no code smells here");
		}
	}

}
