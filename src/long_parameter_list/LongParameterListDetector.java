package long_parameter_list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import app.Analyser;

public class LongParameterListDetector implements Analyser {
	
	private Map<String, CompilationUnit> compilationUnits;
	private List<LongParameterList> longParameterLists;
	private int methodParamsLimit;

	public LongParameterListDetector(Map<String, CompilationUnit> compilationUnits, int methodParamsLimit) {
		this.compilationUnits = compilationUnits;
		this.longParameterLists = new ArrayList<LongParameterList>();
		this.methodParamsLimit = methodParamsLimit;
	}
	
	public void findAllSmells() {
		// find all methods with Long Parameter List smell and store them in longParameterLists
		compilationUnits.forEach((path, cu) -> {
			VoidVisitor<List<LongParameterList>> longParameterListsVisitor = new LongParameterListVisitor(path, methodParamsLimit);
			longParameterListsVisitor.visit(cu, longParameterLists);
		});
	}

	public void printAllSmells() {
		System.out.println("Long Parameter List:");
		try {
			longParameterLists.forEach(smell -> {
				System.out.println("	" + smell.toString());
			});
		} catch (NullPointerException e) {
			System.out.println("	no code smells here");
		}
	}

}
