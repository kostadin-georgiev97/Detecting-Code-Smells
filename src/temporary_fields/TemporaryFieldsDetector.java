package temporary_fields;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import app.Analyser;

public class TemporaryFieldsDetector implements Analyser {
	
	private Map<String, CompilationUnit> compilationUnits;
	private List<TemporaryField> temporaryFields;

	public TemporaryFieldsDetector(Map<String, CompilationUnit> compilationUnits) {
		this.compilationUnits = compilationUnits;
		this.temporaryFields = new ArrayList<TemporaryField>();
	}
	
	public void findAllSmells() {
		// find all Temporary Fields smells and store them in temporaryFields
		compilationUnits.forEach((path, cu) -> {
			VoidVisitor<List<TemporaryField>> temporaryFieldsVisitor = new TemporaryFieldsVisitor(path);
			temporaryFieldsVisitor.visit(cu, temporaryFields);
		});
	}

	public void printAllSmells() {
		System.out.println("Temporary Fields:");
		try {
			temporaryFields.forEach(smell -> {
				System.out.println("	" + smell.toString());
			});
		} catch (NullPointerException e) {
			System.out.println("	no code smells here");
		}
	}

}
