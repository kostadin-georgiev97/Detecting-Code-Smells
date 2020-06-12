package message_chains;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

import app.Analyser;

public class MessageChainsDetector implements Analyser {
	
	private Map<String, CompilationUnit> compilationUnits;
	private List<MessageChain> messageChains;

	public MessageChainsDetector(Map<String, CompilationUnit> compilationUnits) {
		this.compilationUnits = compilationUnits;
		messageChains = new ArrayList<MessageChain>();
	}

	public void findAllSmells() {
		// find all Message Chains smells and store them in messageChains
		compilationUnits.forEach((path, cu) -> {
			VoidVisitor<List<MessageChain>> messageChainsVisitor = new MessageChainsVisitor(path);
			messageChainsVisitor.visit(cu, messageChains);
		});
	}

	public void printAllSmells() {
		System.out.println("Message Chains:");
		try {
			messageChains.forEach(smell -> {
				System.out.println("	" + smell.toString());
			});
		} catch (NullPointerException e) {
			System.out.println("	no code smells here");
		}
	}

}
