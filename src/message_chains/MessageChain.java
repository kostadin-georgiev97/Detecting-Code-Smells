package message_chains;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

public class MessageChain {
	
	private String path;
	private MethodDeclaration method;
	private MethodCallExpr messageChain;
	
	public MessageChain(String path, MethodDeclaration method, MethodCallExpr messageChain) {
		this.path = path;
		this.method = method;
		this.messageChain = messageChain;
	}
	
	public String toString() {
		return path + " - " + method.getNameAsString() + "(): " + messageChain.toString();
	}

}
