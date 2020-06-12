package data_class;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import app.Analyser;

public class DataClassDetector implements Analyser {
	
	private Map<String, CompilationUnit> compilationUnits;
	private List<DataClass> dataClasses;
	
	public DataClassDetector(Map<String, CompilationUnit> compilationUnits) {
		this.compilationUnits = compilationUnits;
		dataClasses = new ArrayList<DataClass>();
	}
	
	public void findAllSmells() {
		// find all Data Class smells and store them in dataClasses
		compilationUnits.forEach((path, cu) -> {
			VoidVisitor<List<DataClass>> dataClassVisitor = new DataClassVisitor(path);
			dataClassVisitor.visit(cu, dataClasses);
		});
	}
	
	public void printAllSmells() {
		System.out.println("Data Class:");
		try {
			dataClasses.forEach(smell -> {
				System.out.println("	" + smell.toString());
			});
		} catch (NullPointerException e) {
			System.out.println("	no code smells here");
		}
	}

}
