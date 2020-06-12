package temporary_fields;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TemporaryFieldsVisitor extends VoidVisitorAdapter<List<TemporaryField>> {
	
	private String pathName;
	
	public TemporaryFieldsVisitor(String path) {
		this.pathName = path;
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration c, List<TemporaryField> temporaryFields) {
		super.visit(c, temporaryFields);
		
		List<FieldDeclaration> fields = c.getFields();
		List<ConstructorDeclaration> constructors = c.getConstructors();
		List<MethodDeclaration> methods = c.getMethods();
		
		List<String> fieldNames = new ArrayList<String>();
		
		fields.forEach(field -> {
			if (!field.isFinal()) {
				field.getVariables().forEach(v -> {
					fieldNames.add(v.getNameAsString());
				});
			}
		});
		
		fieldNames.forEach(fieldName -> {
			
			int numReferencingMethods = 0;
			
			for (MethodDeclaration method : methods) {
				List<SimpleName> variables = method.getBody().get().findAll(SimpleName.class);
				List<String> variableNames = new ArrayList<String>();
				
				variables.forEach(var -> {
					variableNames.add(var.asString());
				});
				
				if (variableNames.contains(fieldName)) {
					// method refers to field
					numReferencingMethods++;
				}
			}
			
			for (ConstructorDeclaration constructor : constructors) {
				List<SimpleName> variables = constructor.getBody().findAll(SimpleName.class);
				List<String> variableNames = new ArrayList<String>();
				
				variables.forEach(var -> {
					variableNames.add(var.asString());
				});
				
				if (variableNames.contains(fieldName)) {
					// constructor refers to field
					numReferencingMethods++;
				}
			}
			
			if ((numReferencingMethods <= 1) && !methods.isEmpty()) {
				// field is referenced only in one method => temporary field code smell
				temporaryFields.add(new TemporaryField(pathName, c, fieldName));
			}
			
		});
		
	}

}
