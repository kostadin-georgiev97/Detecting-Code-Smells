package message_chains;
import java.util.List;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MessageChainsVisitor extends VoidVisitorAdapter<List<MessageChain>> {

	private String pathName;
	
	public MessageChainsVisitor(String pathName) {
		this.pathName = pathName;
	}
	
	@Override
	public void visit(MethodDeclaration method, List<MessageChain> messageChains) {
		super.visit(method, messageChains);
		
		// if MethodCallExpr has MethodCallExpr in childNodes => message chain
		method.findAll(MethodCallExpr.class).forEach(methodCall -> {
			
			if (!(methodCall.getParentNode().get() instanceof MethodCallExpr)) {
				if (methodCall.getChildNodes().size() == 2) {
					
					for(Node child : methodCall.getChildNodes()) {
						if (child instanceof MethodCallExpr) {
							messageChains.add(new MessageChain(pathName, method, methodCall));
							break;
						}
					}
					
				}
			}
			
		});
		
	}
	
}
