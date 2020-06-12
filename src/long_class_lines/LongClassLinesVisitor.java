package long_class_lines;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class LongClassLinesVisitor extends VoidVisitorAdapter<List<LongClassLines>> {
	
	private int linesLimit;
	private String pathName;
	
	public LongClassLinesVisitor(String pathName, int linesLimit) {
		this.pathName = pathName;
		this.linesLimit = linesLimit;
	}
	
	@Override
	public void visit(ClassOrInterfaceDeclaration c, List<LongClassLines> classNumLines) {
		super.visit(c, classNumLines);
		
		String[] lines = c.toString().split("\r\n|\r|\n");
		if (lines.length > linesLimit) {
			classNumLines.add(new LongClassLines(pathName, c, lines.length));
		}
	}

}

