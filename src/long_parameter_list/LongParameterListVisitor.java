package long_parameter_list;
import java.util.List;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LongParameterListVisitor extends VoidVisitorAdapter<List<LongParameterList>> {
	
	private int paramLimit;
	private String pathName;
	
	public LongParameterListVisitor(String pathName, int paramLimit) {
		this.pathName = pathName;
		this.paramLimit = paramLimit;
	}

	@Override
	public void visit(MethodDeclaration method, List<LongParameterList> longParameterLists) {
		super.visit(method, longParameterLists);
		
		int paramSize = method.getParameters().size();
		if (paramSize > paramLimit) {
			longParameterLists.add(new LongParameterList(pathName, method, paramSize));
		}
	}
	
}
