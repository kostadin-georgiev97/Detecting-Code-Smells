package data_class;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class DataClassVisitor extends VoidVisitorAdapter<List<DataClass>> {

private String pathName;
	
	public DataClassVisitor(String pathName) {
		this.pathName = pathName;
	}
	
	/**
	 * Data Class has no methods apart from getters and setters:
	 * 1. Extract all class fields and store each field's name in a list
	 * 2. Extract all methods that belong to the class
	 * 3. If there are no methods -> data class
	 * 4. Else If there are only getters and setters -> data class
	 * 5. Else If a method is other than getter or setter is found:
	 *     - If it contains only print statements -> data class
	 *     - Else -> not a data class
	 */
	@Override
	public void visit(ClassOrInterfaceDeclaration clas, List<DataClass> dataClasses) {
		
		super.visit(clas, dataClasses);
		
		boolean isDataClass = true;
		
		List<FieldDeclaration> fields = clas.getFields();
		List<VariableDeclarator> fieldVariables = new NodeList<VariableDeclarator>();
		fields.forEach(field -> {
			if (!field.isFinal()) {
				fieldVariables.addAll(field.getVariables());
			}
		});
	
		List<MethodDeclaration> methods = clas.getMethods();
		
		// If there are no methods -> this is a Data Class (isDataClass remains true)
		if (!methods.isEmpty()) {
			for(MethodDeclaration method : methods) {
				// If method is other than getter or setter -> this is not a Data Class (isDataClass = false)
				if (!isAccessor(method, fieldVariables) && !isMutator(method, fieldVariables)) {
					isDataClass = false;
				}
			}
		}
		
		if (isDataClass) {
			dataClasses.add(new DataClass(pathName, clas));
		}
		
	}
	
	/**
	 * Getter method conditions:
	 * 1. Method should have no parameters
	 * 2. Method's body should have only one statement and it should be return
	 * 3. Check that the return statement returns one of the class fields without modifying it
	 * 
	 * @param method
	 * @return
	 */
	private boolean isAccessor(MethodDeclaration method, List<VariableDeclarator> fieldVariables) {
		
		if (method.getParameters().isEmpty()) {
			List<Statement> statements = method.getBody().get().getStatements();
			if ((statements.size() == 1)) {
				for (Statement e: statements) {
					if (e instanceof ReturnStmt && e.getChildNodes().size() == 1) {
						
						for(Node child : e.getChildNodes()) {
							if (child instanceof NameExpr) {
								
								for(VariableDeclarator v : fieldVariables) {
									if (v.getNameAsString().equals(((NameExpr) child).getNameAsString())) {
										return true;
									}
								}	
								
							}
						}
						
					}
				}
			}
		}
		
		return false;
	}

	/**
	 * Setter method conditions:
	 * 1. Method should have exactly one arg
	 * 2. Method's body should have one statement and it should be an Assign Expression
	 * 3. The Assign Expression should set the value of one of the fields to the value of the arg
	 * 4. Check method return type is void and type of arg corresponds to the field's type
	 * 
	 * @param method
	 * @return
	 */
	private boolean isMutator(MethodDeclaration method, List<VariableDeclarator> fieldVariables) {
		
		if (method.getParameters().size() == 1) {
			List<Statement> statements = method.getBody().get().getStatements();
			if ((statements.size() == 1)) {
				for (Statement exprStmt: statements) {
					if (exprStmt instanceof ExpressionStmt) {
						
						if (exprStmt.getChildNodes().size() == 1) {
							for (Node assignExpr : exprStmt.getChildNodes()) {
								if (assignExpr instanceof AssignExpr) {
								
									for (Node nameExpr : assignExpr.getChildNodes()) {
										if (!(nameExpr instanceof NameExpr)) {
											return false;
										} 
									}
									Node leftNameExpr = assignExpr.getChildNodes().get(0);
									for (VariableDeclarator fv : fieldVariables) {
										if (fv.getNameAsString().equals(((NameExpr) leftNameExpr).getNameAsString())) {
											return true;
										}
									}
									
								}
							}
						}
							
					}
				}
			}
		}
		
		return false;
	}
	
}
