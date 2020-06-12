package feature_envy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;

import app.Analyser;

public class FeatureEnvyDetector implements Analyser {
	
	private Map<String, CompilationUnit> compilationUnits;
	private List<ClassOrInterfaceDeclaration> allClasses;
	private List<FeatureEnvy> featureEnvies;
	
	public FeatureEnvyDetector(Map<String, CompilationUnit> compilationUnits) {
		this.compilationUnits = compilationUnits;
		this.allClasses = new NodeList<ClassOrInterfaceDeclaration>();
		featureEnvies = new ArrayList<FeatureEnvy>();
	}
	
	public void findAllSmells() {
		// collect all java classes and store them in allClasses
		compilationUnits.forEach((path, cu) -> {
			VoidVisitor<List<ClassOrInterfaceDeclaration>> classVisitor = new ClassVisitor();
			classVisitor.visit(cu, allClasses);
		});
		// find all Feature Envy smells and store them in featureEnvies
		compilationUnits.forEach((path, cu) -> {
			VoidVisitor<List<FeatureEnvy>> featureEnvyVisitor = new FeatureEnvyVisitor(allClasses, path);
			featureEnvyVisitor.visit(cu, featureEnvies);
		});
	}
	
	public void printAllSmells() {
		System.out.println("Feature Envy:");
		try {
			featureEnvies.forEach(smell -> {
				System.out.println("	" + smell.toString());
			});
		} catch (NullPointerException e) {
			System.out.println("	no code smells here");
		}
		
	}

}
