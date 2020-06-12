package feature_envy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FeatureEnvyVisitor extends VoidVisitorAdapter<List<FeatureEnvy>> {
	
	private List<ClassOrInterfaceDeclaration> allClasses;
	private String path;
	private Map<ClassOrInterfaceDeclaration, Integer> externalDependencies;
	private int internalDependenciesCount;
	
	public FeatureEnvyVisitor(List<ClassOrInterfaceDeclaration> allClasses, String path) {
		this.allClasses = allClasses;
		this.path = path;
		externalDependencies = new HashMap<ClassOrInterfaceDeclaration, Integer>();
		internalDependenciesCount = 0;
	}
	
	@Override
	public void visit(MethodDeclaration method, List<FeatureEnvy> featureEnvies) {
		
		super.visit(method, featureEnvies);
		
		List<MethodCallExpr> methodCalls = method.findAll(MethodCallExpr.class);
		
		methodCalls.forEach(methodCallExpr -> {
			List<Node> children = methodCallExpr.getChildNodes();
			if (children.get(0) instanceof NameExpr) {
				NameExpr callerObj = (NameExpr) children.get(0);
				
				// get obj's type - object can be a class field, method's param, or created in method's body
				List<FieldDeclaration> fields = getParentClassFields(method);
				List<VariableDeclarator> fieldVariables = new NodeList<VariableDeclarator>();
				fields.forEach(field -> {
					if (!field.isFinal()) {
						fieldVariables.addAll(field.getVariables());
					}
				});
				List<Parameter> parameters = method.getParameters();
				List<VariableDeclarator> localDeclarations = method.findAll(VariableDeclarator.class);
				
				boolean found = false;
				if (!fieldVariables.isEmpty()) {
					fieldVariables.forEach(fieldVar -> {
						if (callerObj.getName().equals(fieldVar.getName())) {
							Type fieldVarType = fieldVar.getType();
							// if fieldVarType is class in the existing project but not in the current class -> externalDependency
							ClassOrInterfaceDeclaration dependency = findExternalDependency(fieldVarType);
							if (dependency != null) {
								addExternalDependency(dependency);
							} else {
								internalDependenciesCount++;
							}
						}
					});
				}
				if (!parameters.isEmpty()) {
					parameters.forEach(param -> {
						if (callerObj.getName().equals(param.getName())) {
							Type paramType = param.getType();
							// if paramType is class in the existing project but not in the current class -> externalDependency
							ClassOrInterfaceDeclaration dependency = findExternalDependency(paramType);
							if (dependency != null) {
								addExternalDependency(dependency);
							} else {
								internalDependenciesCount++;
							}
						}
					});
				}
				if (!localDeclarations.isEmpty()) {
					localDeclarations.forEach(lDeclar -> {
						if (callerObj.getName().equals(lDeclar.getName())) {
							Type lDeclarType = lDeclar.getType();
							// if paramType is class in the existing project but not in the current class -> externalDependency
							ClassOrInterfaceDeclaration dependency = findExternalDependency(lDeclarType);
							if (dependency != null) {
								addExternalDependency(dependency);
							} else {
								internalDependenciesCount++;
							}
						}
					});
				}
			}
		});
		
		// if type is an existing class from the project -> add to extDependencies
		// else -> add to intDependencies
		
		// if ext > int -> Feature Envy
		ClassOrInterfaceDeclaration enviousClass = null;
		int externalDependenciesCount = 0;
		for (Entry<ClassOrInterfaceDeclaration, Integer> d : externalDependencies.entrySet()) {
			if (d.getValue() > externalDependenciesCount) {
				enviousClass = d.getKey();
				externalDependenciesCount = d.getValue();
			}
		}
		
		if (internalDependenciesCount == 0 && externalDependenciesCount > 0) {
			featureEnvies.add(new FeatureEnvy(path, method, enviousClass));
		} else {
			if (externalDependenciesCount > internalDependenciesCount && externalDependenciesCount > 3) {
				featureEnvies.add(new FeatureEnvy(path, method, enviousClass));
			}
		}
		
	}
	
	private List<FieldDeclaration> getParentClassFields(MethodDeclaration method) {
		Node parentClass = method.getParentNode().get();
		if (parentClass instanceof ClassOrInterfaceDeclaration) {
			return ((ClassOrInterfaceDeclaration) parentClass).getFields();
		}
		return null;
	}
	
	private ClassOrInterfaceDeclaration findExternalDependency(Type t) {
		for(ClassOrInterfaceDeclaration clas : allClasses) {
			if (clas.getNameAsString().equals(t.asString())) {
				return clas;
			}
		}
		return null;
	}
	
	private void addExternalDependency(ClassOrInterfaceDeclaration dependency) {
		if (externalDependencies.containsKey(dependency)) {
			externalDependencies.put(dependency, externalDependencies.get(dependency) + 1);
		} else {
			externalDependencies.put(dependency, 1);
		}
	}

}
